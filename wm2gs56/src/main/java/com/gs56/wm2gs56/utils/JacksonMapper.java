package com.gs56.wm2gs56.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author gaishi_z
 * @create 2019-11-13 10:17
 */
public class JacksonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JacksonMapper() {

    }

    public static ObjectMapper getInstance() {
        return mapper;
    }
}
