package com.example.commonInfraProxy.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
import org.slf4j.*;

import javax.servlet.http.HttpServletRequest;

public class PreFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PreFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run()  {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        System.out.println("========here===========");

        log.info("PreFilter: " + String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        return null;
    }
}
