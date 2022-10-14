package br.com.convergencia.testejavar1.service;


import br.com.convergencia.testejavar1.model.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class TokenService {

    @Value("${client.jwt.expiration}")
    private String expiration;

    @Value("${client.jwt.secret}")
    private String secret;

    public String generate(UserModel user) {

        Date today = new Date();

        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("CONVERGÊNCIA")
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
