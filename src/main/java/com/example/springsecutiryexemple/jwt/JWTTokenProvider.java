package com.example.springsecutiryexemple.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.springsecutiryexemple.utils.Utils.*;
import static java.util.Arrays.stream;


@Component
public class JWTTokenProvider {

    @Value("application.jwt.secretKey")
    private String secret;



    //mycode = numele celui care trimite
    //administration = info ~ numele proiectului
    //authorities: in token punem autorizatiile pe care le are userul
    //criptam cu secretul prin alg HMAC informatiile
    public String generateJwtToken(UserDetails userDetails){
        String[] claims = getClaimsFromUser(userDetails);
        return JWT.create().withIssuer(MY_CODE).withAudience(ADMINISTRATION)
                .withIssuedAt(new Date()).withSubject(userDetails.getUsername())
                .withArrayClaim(AUTHORITIES, claims).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    //luam autoritatile/permisiunile pe care le are userul
    public List<GrantedAuthority> getAuthorities(String token){
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    //returneazaz o lista de autorizari pe care le are un user
    //Granted authority => autoritatea unui user
    private String[] getClaimsFromUser(UserDetails userDetails){
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority grantedAuthority: userDetails.getAuthorities()){
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

    //se returneza permisiunile din token dupa ce am verificat daca este autentic
    private String[] getClaimsFromToken(String token){
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }


    //se face decodificarea token-ului sa vedem daca este autentic, generat de mine
    private JWTVerifier getJWTVerifier(){
        JWTVerifier verifier;
        try{
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier =  JWT.require(algorithm).withIssuer(MY_CODE).build();
        }catch (JWTVerificationException exception){
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }
}
