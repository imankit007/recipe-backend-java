package com.recipe.core.utils;

import org.springframework.data.domain.Page;

public class PageUtils {

    public static <T> com.recipe.grpc.api.common.v1.Page toGrpcPage(Page<T> page, Integer currentPage) {
        return com.recipe.grpc.api.common.v1.Page.newBuilder()
                .setPage(currentPage)
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements())
                .build();
    }



}
