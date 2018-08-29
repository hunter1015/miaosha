package com.miaosha.util;

import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.AbstractContext;
import org.thymeleaf.context.IWebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WilsonSong
 * @date 2018/8/5
 */

/**
 * https://blog.csdn.net/WilsonSong1024/article/details/81535860
 * 由于Sping5中SpringWebContext方法过时，无法将页面数据保存起来手工进行页面渲染
 * 本类是原作者通过查看spring4版本SpringWebContext的代码，进行了源码修改 成为可用的 页面内容处理 组件
 */
public class SpringWebContextUtil  extends AbstractContext implements IWebContext {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ServletContext servletContext;

    public static final String BEANS_VARIABLE_NAME = "beans";
    private static final ConcurrentHashMap<ApplicationContext, HashMap<String, Object>> variableMapPrototypes = new ConcurrentHashMap();
    private final ApplicationContext applicationContext;

    public SpringWebContextUtil(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final ServletContext servletContext,
                                final Locale locale,
                                final Map<String, Object> variables,
                                final ApplicationContext appctx){
        super(locale,addSpringSpecificVariables(variables, appctx));
        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
        this.applicationContext = appctx;

    }

    private static Map<String, Object> addSpringSpecificVariables(Map<String, ?> variables, ApplicationContext appctx) {
        HashMap<String, Object> variableMapPrototype = (HashMap)variableMapPrototypes.get(appctx);
        if (variableMapPrototype == null) {
            variableMapPrototype = new HashMap(20, 1.0F);
            ContexBeans beans = new ContexBeans(appctx);
            variableMapPrototype.put("beans", beans);
            variableMapPrototypes.put(appctx, variableMapPrototype);
        }

        Map newVariables;
        synchronized(variableMapPrototype) {
            newVariables = (Map)variableMapPrototype.clone();
        }

        if (variables != null) {
            newVariables.putAll(variables);
        }

        return newVariables;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public HttpSession getSession() {
        return this.request.getSession(false);
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public ServletContext getServletContext() {
        return this.servletContext;
    }
}
