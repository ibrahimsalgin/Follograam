package com.developers.yeditepe.follograam.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ibrahimsalgin on 21.12.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataResponseObject {
    private String outgoing_status;
    private String incoming_status;
    private boolean target_user_is_private;

    public String getOutgoing_status() {
        return outgoing_status;
    }

    public void setOutgoing_status(String outgoing_status) {
        this.outgoing_status = outgoing_status;
    }

    public String getIncoming_status() {
        return incoming_status;
    }

    public void setIncoming_status(String incoming_status) {
        this.incoming_status = incoming_status;
    }

    public boolean getTarget_user_is_private() {
        return target_user_is_private;
    }

    public void setTarget_user_is_private(boolean target_user_is_private) {
        this.target_user_is_private = target_user_is_private;
    }
}
