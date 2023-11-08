package com.example.fastcampusmysql.domain;

import org.springframework.data.domain.Sort;

import java.util.List;

public class PageHelper {
    public static String orderBy(Sort sort) {
        if(sort.isEmpty()) {
            return "id DESC";
        }

        List<Sort.Order> orders = sort.toList();
        var orderBys = sort.stream()
                .map(order -> order.getProperty() + " "+order.getDirection())
                .toList();

        return String.join(", ", orderBys);
    }
}
