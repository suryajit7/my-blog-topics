package com.framework.data.supplier;

import com.framework.data.entity.User;
import io.github.sskorol.core.DataSupplier;

import java.util.stream.Stream;

import static com.framework.data.entity.User.builder;


public class UserData {

    @DataSupplier
    public Stream<User> getUserInfoToCreateUsers() {
        return Stream.of(builder().name("admin").job("admin").build(),
                        builder().name("ronaldo").job("player").build());
    }

}

