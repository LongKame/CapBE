package com.example.JWTSecure.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailEventDTO {

    private Long id;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private String caption;
    private String image;
    private String register_date;
    private String event_name;
    private String key_search;
    private int page;
    private int pageSize;
}
