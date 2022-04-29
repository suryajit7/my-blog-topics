package com.framework.data.supplier;

import com.framework.data.entity.User;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class UserData {


    @DataProvider(name = "users-info")
    public Object[][] getUserInfoToCreateUsers(){
        return createUserPayload().stream()
                .map(user -> new Object[] {user})
                .toArray(Object[][]::new);
    }



    private List<User> createUserPayload() {

        List<User> usersList = new ArrayList<>();

        usersList.add(User.builder().name("admin").job("admin").build());
        usersList.add(User.builder().name("ronaldo").job("player").build());

        return usersList;
    }

}

