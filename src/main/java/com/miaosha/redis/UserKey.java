package com.miaosha.redis;

public class UserKey extends BasePrefix{
    private static final int TOKEN_EXIPRE= 3600*24*2;

    private UserKey(String prefix) {
        super(prefix);
    }
    private UserKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }






    public static UserKey token = new UserKey(TOKEN_EXIPRE,"tk");

    //对象缓存要永久有效，过期时间设置为0
    public static UserKey getById = new UserKey(0,"id");
    public static UserKey getByName = new UserKey("name");
}
