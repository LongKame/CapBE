package com.example.JWTSecure.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO {

    private Long id;
    private String name;
    private String image;
    private String from_date;
    private String to_date;
    private String key_search;
    private int page;
    private int pageSize;
}
