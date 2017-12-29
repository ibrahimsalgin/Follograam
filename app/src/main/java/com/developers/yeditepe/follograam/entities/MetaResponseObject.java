package com.developers.yeditepe.follograam.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ibrahimsalgin on 21.12.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaResponseObject {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
