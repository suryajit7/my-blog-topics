package com.framework.data;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.framework.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@JsonInclude(NON_NULL)
public class BaseEntity {

    List<User> users;


}
