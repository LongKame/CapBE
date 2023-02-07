package com.example.JWTSecure.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViolateDTO {

    private Long id;
    private String post_user_name;
    private String name_post;
    private String violated_code;
    private String violated_name;
    private String email;
    private String phone;
    private String name;
    private String image;
    private String key_search;
    private int page;
    private int pageSize;
}
