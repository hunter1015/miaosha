package com.miaosha.service;

import com.miaosha.dao.UserDao;
import com.miaosha.entity.User;
import com.miaosha.exception.GlobleException;
import com.miaosha.redis.RedisService;
import com.miaosha.redis.UserKey;
import com.miaosha.result.CodeMsg;
import com.miaosha.util.CookieUtil;
import com.miaosha.util.MD5Util;
import com.miaosha.util.UUIDUtil;
import com.miaosha.vo.LoginVoPhone;
import com.miaosha.vo.LoginVoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.miaosha.util.MD5Util.inputPassToDBPass;

@Service
public class UserService {
    public static final String COOKIE_NAME_TOKEN="token";
    private static Logger userServiceLog= LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;

    /**
     * 新增用户-其中密码要通过加salt处理
     * @param user
     * @return
     */
    public boolean newUser(User user){
        if (userDao.selectUserByName(user.getUsername()).size()>0) {
            userServiceLog.info("发现同名用户，无法新建用户"+user.getUsername());
            return false;
        }
        Random random=new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        //随机为本用户生成16位salt值
        String saltstr=new BASE64Encoder().encode(salt);
        user.setSalt(saltstr);
        user.setRegisterDate(new Date(System.currentTimeMillis()));

        //根据生成的salt值加密用户密码-确保数据库不存储用户密码明文
        user.setPassword(inputPassToDBPass(user.getPassword(),saltstr));
        userServiceLog.info("new user");
        return userDao.insert(user)>0;
    }

    public boolean deleteUser(int userId){
        return userDao.deleteByPrimaryKey(userId)>0;
    }

    public List<User> listAllUser(){
        return userDao.selectAll();
    }

    /**
     * 通过ID查人  --唯一
     * @param id
     * @return
     */
    public User getUserById(long id){
        //先查redis缓存
        User user= redisService.get(UserKey.getById, ""+id ,User.class);
        if (user !=null){
            return user;
        }
        //缓存中查不着，就查数据库的
        user= userDao.selectUserById(18);
        userServiceLog.info("redis没查到，去数据库查，结果为"+user.getUsername());
        if (user !=null){
            redisService.set(UserKey.getById, "" + id, user);
        }
        return user;
    }

    /**
     * 通过用户名查人
     * @param username
     * @return
     */
    public User getUserByName(String username){
        //先查redis缓存
        User user= redisService.get(UserKey.getByName, ""+username ,User.class);
        if (user !=null){
            return user;
        }
        //缓存中查不着，就查数据库的
        List<User> userList= userDao.selectUserByName(username);
        if (userList !=null&&userList.size()<=1){
            redisService.set(UserKey.getByName, "" + username, userList.get(0));
            return userList.get(0);
        }
        return null;
    }

    //csdn大神文章
    // http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
    public boolean updatePassword(String token,long id,String formPass){
        /*
        对象级缓存
         */
        //取user
        User user=getUserById(id);
        if (user==null){
            throw new GlobleException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库，修改哪个字段就更新哪个字段
        User toBeUpdate = new User();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass,user.getSalt()));
        userDao.updateByPrimaryKey(toBeUpdate);
        //UserDao.update(toBeUpdate);

        //更新完数据库，删除缓存
        redisService.delete(UserKey.getById,""+id);
        //token不能直接删除，要不更新不上。所以更新一下
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(UserKey.token,token,user);

        return true;
    }

    /**
     * 登录-手机号
     * @param response
     * @param loginVo
     * @return
     */
    public boolean loginByPhone(HttpServletRequest request,HttpServletResponse response, LoginVoPhone loginVo){
        if (loginVo==null){
            throw  new GlobleException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        User user = getUserById(Long.parseLong(mobile));
        if(user==null){
            throw  new GlobleException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if(!calcPass.equalsIgnoreCase(dbPass)){
            throw new GlobleException(CodeMsg.PASSWORD_ERROR);
        }
        //分布式session
        //生成cookie
        String token= UUIDUtil.uuid();
        addCookie(request,response,token,user);
        return true;
    }

    /**
     * 登录-用户名
     * @param response
     * @param loginVoUser
     * @return
     */
    public boolean loginByUser(HttpServletRequest request,HttpServletResponse response, LoginVoUser loginVoUser){
        if (loginVoUser==null){
            //接受的用户输入为空，报服务器错
            throw  new GlobleException(CodeMsg.SERVER_ERROR);
        }

        String username = loginVoUser.getUsername();
        String inputPass = loginVoUser.getPassword();


        //判断用户号是否存在
        User user = getUserByName(username);
        if(user==null){
            throw  new GlobleException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //验证是否有已存在Cookie
        if (getCookie(request,username))
            return true;



        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.inputPassToDBPass(inputPass, saltDB);
        if(!calcPass.equalsIgnoreCase(dbPass)){
            throw new GlobleException(CodeMsg.PASSWORD_ERROR);
        }
        //分布式session
        //生成cookie
        String token= UUIDUtil.uuid();
        addCookie(request,response,token,user);
        return true;
    }
    //生成cookie
    private void addCookie(HttpServletRequest request,HttpServletResponse response, String token,User user){
        HttpSession session=request.getSession();
        String username=user.getUsername();

        //设置session和值  记录的是单纯的 名称+token值  让无法判断是某个用户的
        session.setAttribute(COOKIE_NAME_TOKEN+"-"+username,token);

        //redis保存
        redisService.set(UserKey.token,token,user);

        //cookie添加
        Cookie cookie=new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(UserKey.token.expireSeconds());//cookie有效期=userkey的有效期
        cookie.setPath("/");//设置成网站根目录
        response.addCookie(cookie);
        userServiceLog.info("添加完cookie和session,session的key为cookietoken 值为"+token);

    }

    private boolean getCookie(HttpServletRequest request,String username){
        //获取名为“token”的cookie，只能有一个？？
        Cookie cookie=CookieUtil.getCookieByName(request,COOKIE_NAME_TOKEN);
        if (cookie!=null) {
            //获取具体的token值
            String token = cookie.getValue();

            //从redis里直接找对应本token对应的username，也就是说用户名和token的对应关系最后是在redis记录
            String cookieuser = redisService.get(UserKey.token, token, String.class);
            if (cookieuser != null) {
                return username == cookieuser;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

}
