package com.example.commonInfraProxy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class Utils {

    private static String SecretKey = "M@@KIC|-|00";

    public static Claims validateToken(String token) {
        return Jwts.parser().setSigningKey(SecretKey.getBytes()).parseClaimsJws(token).getBody();
    }

}
