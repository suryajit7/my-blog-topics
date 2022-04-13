package com.framework.data;

/**
 * All framework related constants which are not defined in application.properties.
 */
public class Constants {

    public static final String TEST_RESOURCE = "";

    //API Related Constants
    public static final String CONTENT_TYPE_JSON_CHARSET_UTF8 = "application/json;charset=utf-8";
    public static final String HEADER = "header";
    public static final String RESPONSE_HEADER = "responseHeader";
    public static final String MULTI_VALUE_HEADER = "multiValueHeader";
    public static final String X_API_KEY_HEADER = "X-Api-Key";
    public static final String X_MOCK_MATCH_REQUEST_HEADERS = "x-mock-match-request-headers";
    public static final String X_MOCK_MATCH_REQUEST_BODY = "x-mock-match-request-body";
    public static final String X_SRV_TRACE = "x-srv-trace";
    public static final String X_SRV_SPAN = "x-srv-span";
    public static final String X_RATE_LIMIT_LIMIT = "X-RateLimit-Limit";
    public static final String X_RATE_LIMIT_REMAINING = "X-RateLimit-Remaining";
    public static final String X_RATE_LIMIT_RESET = "X-RateLimit-Reset";

    //Common Regex Constants
    public static final String RFC5322_EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
}
