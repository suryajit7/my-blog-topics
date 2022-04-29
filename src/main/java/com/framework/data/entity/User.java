package com.framework.data.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class User {

    private Integer id;

    private String name;
    private String first_name;
    private String last_name;

    private String email;
    private String job;

    private String avatar;

    //TODO: This is typically a date, create a custom Date Parser
    private String createdAt;

}
