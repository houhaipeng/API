package com.hhp.jwt;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class JwtTest {

    private static long tokenExpiration = 1000 * 60 * 60 * 24;//一天
    private static String tokenSignKey = "fhid";

    //生成jwt
    @Test
    public void testCreatedToken() {
        JwtBuilder builder = Jwts.builder();
        //头，载荷，签名哈希
        String jwtToken = builder
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                //载荷
                .claim("nickname", "hhp")
                .claim("avatar", "1.jpg")
                .claim("role", "admin")
                //载荷：默认信息
                .setSubject("主题")//等价于claim("sub", "主题")
                .setIssuer("签发者")
                .setAudience("接收者")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .setNotBefore(new Date(System.currentTimeMillis() + 1000 * 20))
                //
                .setId(UUID.randomUUID().toString())
                //
                .signWith(SignatureAlgorithm.HS256, tokenSignKey)
                //组装jwt
                .compact();
        System.out.println(jwtToken);

    }

    //解析jwt
    @Test
    public void testGetUserInfo() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuaWNrbmFtZSI6ImhocCIsImF2YXRhciI6IjEuanBnIiwicm9sZSI6ImFkbWluIiwic3ViIjoi5Li76aKYIiwiaXNzIjoi562-5Y-R6ICFIiwiYXVkIjoi5o6l5pS26ICFIiwiaWF0IjoxNjIzMDcwNzY0LCJleHAiOjE2MjMxNTcxNjQsIm5iZiI6MTYyMzA3MDc4NCwianRpIjoiOGYzODVkNWEtODY1MC00ZjM2LTgwZjYtYTY1ZDI5MmEwNGNmIn0.Qe1_y6mcSm-qZCn0z5kRfXkesKV0e8ghvidURKymd08";
        JwtParser parser = Jwts.parser();
        Jws<Claims> claimsJws = parser.setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");
        String role = (String) claims.get("role");
        System.out.println(role);
        String id = claims.getId();
        System.out.println(id);
    }
}
