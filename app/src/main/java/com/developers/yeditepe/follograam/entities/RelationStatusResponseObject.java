package com.developers.yeditepe.follograam.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ibrahimsalgin on 21.12.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationStatusResponseObject {
    private DataResponseObject data;
    private MetaResponseObject meta;

    public DataResponseObject getData() {
        return data;
    }

    public void setData(DataResponseObject data) {
        this.data = data;
    }

    public MetaResponseObject getMeta() {
        return meta;
    }

    public void setMeta(MetaResponseObject meta) {
        this.meta = meta;
    }
}
