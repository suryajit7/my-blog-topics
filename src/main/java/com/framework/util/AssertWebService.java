package com.framework.util;


import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.AbstractAssert;
import org.skyscreamer.jsonassert.JSONAssert;

import static com.framework.util.PathFinder.getFilePathForFile;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.settings;
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT_ORDER;

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


    @SneakyThrows
    public <T> AssertWebService hasValidJsonData(String jsonFilename, Class<T> type) {
        isNotNull();
        String jsonString = FileUtils.readFileToString(FileUtils.getFile(getFilePathForFile(jsonFilename).toFile()), "UTF-8");
        JSONAssert.assertEquals(actual.then().extract().asPrettyString(), jsonString, STRICT_ORDER);
        return this;
    }


}
