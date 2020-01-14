package com.geo.core.util;

import com.geo.core.constant.JWTTokenConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.Map;

public class TokenUtil {

    private final static Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    private static long timeLimit = 1000 * 60 * 60 * 2L;// 2小时

    /**
     * 生成jwtToken
     * @param tokenKey
     */
    public static String generateToken(String key, Long time) {
        HashMap<String, Object> map = new HashMap<>();
        // you can put any data in the map
        map.put("tokenKey", key);
        String jwt = Jwts.builder().setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(SignatureAlgorithm.HS512, JWTTokenConstant.SECRET).compact();
        return jwt;
    }

    /**
     * 生成jwtToken
     */
    public static String generateToken(Map<String, Object> body) {
        // you can put any data in the map
        String jwt = Jwts.builder().setClaims(body)
                .signWith(SignatureAlgorithm.HS512, JWTTokenConstant.SECRET).compact();
        return jwt;
    }

    /**
     * 校验jwtToken
     *
     * @param token
     * @return
     */
    public static Map<String, Object> validateToken(String token) {
        if (!StringUtils.isEmpty(token)) {
            Map<String, Object> body = Jwts.parser().setSigningKey(JWTTokenConstant.SECRET).parseClaimsJws(token).getBody();

            if (CollectionUtils.isEmpty(body)) {
                throw new TokenValidationException("Wrong token without tokenKey");
            }
            return body;
        } else {
            throw new TokenValidationException("Missing token");
        }
    }

    public static long getEXPIRATION_TIME(){
        return timeLimit;
    }


    static class TokenValidationException extends RuntimeException {
        /**
         *
         */
        private static final long serialVersionUID = -7946690694369283250L;

        public TokenValidationException(String msg) {
            super(msg);
        }
    }
}
