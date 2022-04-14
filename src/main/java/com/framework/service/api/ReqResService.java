package com.framework.service.api;

import com.framework.service.BaseService;
import io.restassured.response.Response;

public class ReqResService extends BaseService {

    public static final String LIST_USERS_ROUTE = "/users?page=2";
    public static final String GET_SINGLE_USER_ROUTE = "users/{path_param}";
    public static final String CREATE_USER = "/users";

    public Response getAllUsersList(){
        return get(LIST_USERS_ROUTE);
    }

    public Response createUser(Object payload){
        return post(CREATE_USER, payload);
    }



}
