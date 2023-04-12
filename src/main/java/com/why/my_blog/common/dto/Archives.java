package com.why.my_blog.common.dto;

import lombok.Data;

@Data
public class Archives {

    private Integer year;

    private Integer month;

    private Long count;
}
