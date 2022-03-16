package com.axonactive.jpa.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.axonactive.jpa.entities.Token;
import com.axonactive.jpa.entities.User;
import com.axonactive.jpa.helper.AppConfigService;
import com.axonactive.jpa.service.impl.UserServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;

public class JWTAuthenticationServices {

    @Inject
    UserServiceImpl userService;

    public Token createToken(User user) {
        validateUser(user);

        String token = null;
        String secretKey = AppConfigService.getSecretKey();
        String issuer = AppConfigService.getIssuer();
        int timeToLive = AppConfigService.getTimeToLive();

        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            token = JWT.create()
                    .withIssuer(issuer)
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("username", user.getName())
                    .withExpiresAt(this.setTokenTimeToLive(timeToLive))
                    .sign(algorithm);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        if(Objects.isNull(token)){
            throw  new WebApplicationException(Response.status(BAD_REQUEST).entity("Could not create Token").build());
        }
        return new Token(token,timeToLive);


    }

    private void validateUser(User user) {
        User userInDataBase = userService.findUserByNameAndPassword(user.getName(), user.getPassword());
        if(Objects.isNull(userInDataBase)){
            throw new WebApplicationException(Response.status(FORBIDDEN).entity("User not in system").build());
        }
    }

    private Date setTokenTimeToLive(int timeToLive) {
        LocalDate currentDate = LocalDate.now();
        LocalDate expiredDate = currentDate.plusDays(changeMinusToDay(timeToLive));
        return Date.from(expiredDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private long changeMinusToDay(int timeToLive) {
        int minutesInDays = 24 * 60;
        return timeToLive/ minutesInDays;
    }
}
