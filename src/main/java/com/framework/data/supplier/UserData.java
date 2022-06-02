package com.framework.data.supplier;

import com.framework.data.entity.User;
import io.github.sskorol.core.DataSupplier;

import java.util.stream.Stream;

public class UserData {

    @DataSupplier
    public Stream<User> getUserInfoToCreateUsers() {
        return Stream.of(User.builder().name("admin").job("admin").build(),
                User.builder().name("ronaldo").job("player").build());
    }



}

