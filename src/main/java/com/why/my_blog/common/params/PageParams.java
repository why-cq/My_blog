package com.why.my_blog.common.params;

import lombok.Data;

@Data
public class PageParams {
    private int page = 1;

    private int pageSize = 10;
}
