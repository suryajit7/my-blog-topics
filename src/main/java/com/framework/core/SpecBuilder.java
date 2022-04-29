package com.framework.core;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;

import static com.framework.data.Constants.API_URL;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;


public class SpecBuilder {

    public RequestSpecification getRequestSpec(){
        RequestSpecification requestSpec = null;
        return requestSpec = new RequestSpecBuilder()
                .setBaseUri(API_URL)
                .setBasePath("/api")
                .setContentType(JSON)
                .log(ALL).build();
    }

    public ResponseSpecification getResponseSpec(){
        ResponseSpecification responseSpec = null;
        return responseSpec = new ResponseSpecBuilder()
                .log(ALL).build();
    }


    public HashMap<String, String> getFormParams() {

        HashMap<String, String> formParams = new HashMap<>();
        formParams.clear();
        formParams.put("CLIENT_ID", "");
        formParams.put("CLIENT_SECRET", "");
        formParams.put("REFRESH_TOKEN", "");
        formParams.put("GRANT_TYPE", "REFRESH_TOKEN");

        return formParams;
    }

}
