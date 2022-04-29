package com.framework.util;


import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import org.assertj.core.api.AbstractAssert;
import org.skyscreamer.jsonassert.JSONAssert;

import static com.framework.util.PathFinder.getFilePathForFile;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.settings;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.io.FileUtils.getFile;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.hamcrest.Matchers.lessThan;
import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT;
import static org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE;

public class AssertWebService extends AbstractAssert<AssertWebService, Response> {

    public AssertWebService(Response response) {
        super(response, AssertWebService.class);
    }

    public static AssertWebService assertThat(Response response) {
        return new AssertWebService(response);
    }


    public AssertWebService hasValidStatusCode(int expectedHttpSatus) {
        isNotNull();
        actual.then().assertThat().statusCode(expectedHttpSatus);
        return this;
    }


    public AssertWebService hasValidJsonSchema(String schemaFilename) {
        isNotNull();
        actual.then()
                .body(matchesJsonSchema(getFilePathForFile(schemaFilename).toFile())
                        .using(settings));
        return this;
    }


    public AssertWebService hasResponseTimeWithin(Long timeout) {
        isNotNull();
        ValidatableResponse validatableResponse = actual.then();
        validatableResponse.time(lessThan(timeout), SECONDS);
        return this;
    }


    @SneakyThrows
    public <T> AssertWebService hasValidJsonData(String jsonFilename) {
        isNotNull();

        String expectedJsonString = readFileToString(getFile(getFilePathForFile(jsonFilename).toFile()), "UTF-8");

        JSONAssert.assertEquals(expectedJsonString, actual.then().extract().asPrettyString(), LENIENT);
        return this;
    }

    @SneakyThrows
    public <T> AssertWebService hasValidJsonData(String jsonFilename, Class<T> type) {
        isNotNull();

        String expectedJsonString = readFileToString(getFile(getFilePathForFile(jsonFilename).toFile()), "UTF-8");

        JSONAssert.assertEquals(expectedJsonString, actual.as(type).toString(), NON_EXTENSIBLE);
        return this;
    }


}
