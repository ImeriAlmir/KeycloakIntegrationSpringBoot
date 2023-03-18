package com.example.demo.utilties;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.dto.LoginRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@AllArgsConstructor
public class Utility {

    private static OkHttpClient okHttpClient = new OkHttpClient();

    private final static String KEYCLOAK_URL = "http://localhost:8080/realms/SpringBootKeycloak/protocol/openid-connect/token";

    public static String getUserId() {
        String userId = "";

        String jwt = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getTokenValue();
        DecodedJWT decodedJWT = JWT.decode(jwt);
        userId = decodedJWT.getSubject();

        return userId;
    }

    public static String loginUser(LoginRequest loginRequest) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "password")
                .add("client_id", "springboot-keycloak-client")
                .add("username", "user1")
                .add("password", "1234")
                .build();
        Request request = new Request.Builder()
                .url(KEYCLOAK_URL) // replace with your URL
                .post(formBody)
                .build();
        String accessTokenValue = "1234";

        try (Response response = okHttpClient.newCall(request).execute()) {
            // read JSON response as string
            String jsonResponse = response.body().string();

            // parse JSON response with Jackson
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            System.out.println("*****************: " + root);
            // access specific field by key
            JsonNode accessToken = root.get("access_token");
            accessTokenValue = accessToken.asText();

            // do something with the field value
            System.out.println("Field value: " + accessTokenValue);

            return accessTokenValue;
        }
    }
}
