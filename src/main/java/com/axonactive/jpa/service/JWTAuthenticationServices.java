package com.axonactive.jpa.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.axonactive.jpa.controller.request.UserRequest;
import com.axonactive.jpa.entities.Token;
import com.axonactive.jpa.entities.User;
import com.axonactive.jpa.exeption.UnAuthorizedException;
import com.axonactive.jpa.helper.AppConfigService;
import com.axonactive.jpa.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;

//@Log4j2
public class JWTAuthenticationServices {

    @Inject
    UserServiceImpl userService;

    private static final Logger logger = LogManager.getLogger(JWTAuthenticationServices.class);

    public Token createToken(UserRequest userRequest) {
        validateUser(userRequest);

        String token;
        String secretKey = AppConfigService.getSecretKey();
        String issuer = AppConfigService.getIssuer();
        int timeToLive = AppConfigService.getTimeToLive();

        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            token = JWT.create()
                    .withIssuer(issuer)
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("username", userRequest.getName())
                    .withExpiresAt(this.setTokenTimeToLive(timeToLive))
                    .sign(algorithm);
        } catch (IllegalArgumentException e) {
            logger.error("could not create token as the argument is illegal");
            throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Could not create Token").build());
        }

        if (Objects.isNull(token)) {
            throw new WebApplicationException(Response.status(BAD_REQUEST).entity("Could not create Token").build());
        }
        return new Token(token, timeToLive);
    }

    public void checkAuthorizedToken(String authorization) {
        if (Objects.isNull(authorization)) {
            logger.error("The authorization header is null");
            throw new WebApplicationException(Response.status(FORBIDDEN).entity("There is no authorization header in the request").build());
        }
        String jwtToken = getSWTToken(authorization);
        try {
            String secretKey = AppConfigService.getSecretKey();
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(AppConfigService.getIssuer()).build();
            verifier.verify(jwtToken);

        } catch (JWTVerificationException e) {
            logger.error("the token is invalided");
            throw new WebApplicationException(Response.status(FORBIDDEN).entity("The token for authentication is invalided").build());
        } catch (IllegalArgumentException e) {
            logger.error("the secret key {} is invalided", e.getMessage());
        }

    }

    private void validateUser(UserRequest userRequest) {
        User userInDataBase = userService.findUserByNameAndPassword(userRequest.getName(), userRequest.getPassword());
        if (Objects.isNull(userInDataBase)) {
            logger.error("try to get authorization with username: {}",userRequest.getName());
            throw new UnAuthorizedException();
        }
    }

    private Date setTokenTimeToLive(int timeToLive) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime expiredDate = currentDate.plusMinutes(timeToLive);
        return Date.from(expiredDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    private String getSWTToken(String authorization) {
        String[] authPaths = authorization.split("\\s+");
        if (authPaths.length < 2 || !authPaths[0].equals("Bearer")) {
            logger.error("There is no swtToken in header");
            throw new WebApplicationException(Response.status(FORBIDDEN).entity("Lack of SWT Token in authentication header").build());
        }
        return authPaths[1];
    }
}
