package com.framework.service;

import com.framework.core.SpecBuilder;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static io.restassured.RestAssured.given;

@Slf4j
public class BaseService {

    public static final String PATH_PARAM = "path_param";

    public SpecBuilder specBuilder = new SpecBuilder();

    public Response get(String path){
        return given(specBuilder.getRequestSpec())
                .when()
                .get(path)
                .then()
                .spec(specBuilder.getResponseSpec())
                .extract().response();
    }

    public Response post(String path, Object payload){
        return given(specBuilder.getRequestSpec())
                .body(payload)
                .post(path)
                .then()
                .spec(specBuilder.getResponseSpec())
                .extract().response();
    }


}
