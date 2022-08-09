package com.example.commonInfraProxy.filters;

import com.example.commonInfraProxy.repository.UserRepository;
import com.example.commonInfraProxy.utils.Utils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PreFilter extends ZuulFilter {

    @Autowired
    UserRepository userRepository;

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

        System.out.println("filter ====== "+request.getRequestURI());

        if(request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/signup"))
            return false;

        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();

        try{
            String token = request.getHeader("token");


                if (token.length() == 0)
                {
                    ctx.setSendZuulResponse(false);
                }

                Claims claims = Utils.validateToken(token);

                String id = claims.getId();

                Boolean userExist = userRepository.existsByUserId(id);

                System.out.println(id);

                if (userExist){
                    response.setHeader("userId", id);
                    ctx.addZuulRequestHeader("userId", id);
                }
                else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
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
