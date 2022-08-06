package com.example.commonInfraProxy.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.*;

import javax.servlet.http.HttpServletRequest;

public class PreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        if(request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/signup"))
            return false;

        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        try{
            String token = request.getHeader("token");
            if(request.getRequestURI().equals("/user/userauthorization"))
            {
                if (token.length() == 0)
                {
                    ctx.setSendZuulResponse(false);
                }
            }
        }catch (Exception e){
            ctx.setResponseBody("token is absent "+e.getMessage());
            ctx.setSendZuulResponse(false);
        }


//        System.out.println("========here==========="+token);
        System.out.println("uri ==== "+request.getRequestURI());


        return null;
    }
}
