package com.miaosha.controller;

import com.miaosha.entity.Goods;
import com.miaosha.entity.User;
import com.miaosha.redis.GoodsKey;
import com.miaosha.redis.RedisService;
import com.miaosha.result.Result;
import com.miaosha.service.GoodsService;
import com.miaosha.service.UserService;
import com.miaosha.util.SpringWebContextUtil;
import com.miaosha.vo.GoodsDetailVo;
import com.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;


    @Autowired
    ApplicationContext applicationContext;


    /*@RequestMapping(value="/to_list", produces="text/html")
    @ResponseBody*/

    //全部商品的列表 不返回页面，直接返回HTML的代码
    @GetMapping(value = "/list",produces = "text/html")
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
//    	 return "goods_list";

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }

        //手动渲染 优化访问速度，应对高并发，想把页面信息全部获取出来存到redis缓存中，这样每次访问就不用客户端进行渲染了，速度能快不少。

        //SpringWebContext本身是spring5之前可用的页面内容处理类，但spring5之后过时了，所以这里根据源代码进行了修改使其可用
        SpringWebContextUtil ctx = new SpringWebContextUtil(request,response,
                request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );

        //使用thymeleaf引擎渲染页面内容 在goods_listhtml中渲染传入的页面内容ctx
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if(!StringUtils.isEmpty(html)) {
            //如果页面内容不为空，并且走到这一步，说明缓存中没有，于是将其加入缓存中（增快用户访问列表页面的刷新速度）
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

//    public String toList(Model model, HttpServletRequest request, HttpServletResponse response,
////                         @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN,required = false)String cookieToken,
////                            //为了兼容手机端，从RequestParam中取token
////                         @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN,required = false)String paramToken
//                         MiaoshaUser user){
//         model.addAttribute("user", user);
////        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
////            return "login";
////        }
////        //设置优先级。优先取paramToken
////        String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
////        //从redis中取出token
////        MiaoshaUser user=miaoshaUserService.getByToken(response,token);
//
//        //页面缓存
//        /*
//        取缓存
//         */
//        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
//        //如果缓存中非空
//        if(!StringUtils.isEmpty(html)){
//            return html;
//        }
//
//        //查询商品列表
//        List<GoodsVo> goodsList=goodsService.listGoodsVo();
//        model.addAttribute("goodsList",goodsList);
////        return "goods_list";
//
//        /*如果从redis中娶不到，就手动渲染*/
//        SpringWebContext ctx = new SpringWebContext(request,response,
//                request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );
//        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
//        if(!StringUtils.isEmpty(html)) {
//            redisService.set(GoodsKey.getGoodsList, "", html);
//        }
//        //写到输出
//        return html;
//    }


    //静态的商品的详情页
    @RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(User user,
                                        @PathVariable("goodsId")long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        return Result.success(vo);
    }



   //木有静态化的商品的详情页
    @RequestMapping(value = "/to_detail/{goodsId}", produces="text/html")
    @ResponseBody
    public String detail(Model model, HttpServletRequest request, HttpServletResponse response,
                         User user,
                         @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);
//        取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        //如果缓存中非空
        if(!StringUtils.isEmpty(html)){
            return html;
        }
//        *如果从redis中取不到，就*手动渲染*
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
//        return "goods_detail";

        SpringWebContextUtil ctx = new SpringWebContextUtil(request,response,
                request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
        }
        //写到输出
        return html;
    }

}
