package com.example.commonInfraProxy.filters;

import com.example.commonInfraProxy.repository.UserRepository;
import com.example.commonInfraProxy.utils.Utils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
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

        if(request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/signup") ||
                request.getRequestURI().equals("/ads/getads/quizMaster"))
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
                    response.setHeader("Access-Control-Allow-Origin","*");
                    response.setHeader("Access-Control-Allow-Methods", "DELETE, GET, OPTIONS, POST, PUT");
                    response.setHeader("Access-Control-Allow-Credentials", "true");

                    ctx.addZuulRequestHeader("userId", id);
                    ctx.addZuulResponseHeader("Access-Control-Allow-Origin","*");
                }
                else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
                    // todo : after all the testing, if the token is not valid .. send the user to login page
                }


        }catch (Exception e){
            ctx.setResponseBody("token is absent "+e.getMessage());
            ctx.setSendZuulResponse(false);
        }

        System.out.println("uri ==== "+request.getRequestURI());


        return null;
    }
}
