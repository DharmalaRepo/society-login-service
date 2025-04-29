package com.tech.society.login.dto;

import lombok.Data;

@Data
public class RequestContext {
    private String societyId;
    private String ipAddress;
    private String requestTime;

    public RequestContext() {
    }

    public RequestContext(String societyId, String ipAddress, String requestTime) {
        this.societyId = societyId;
        this.ipAddress = ipAddress;
        this.requestTime = requestTime;
    }

    public String getSocietyId() {
        return societyId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getRequestTime() {
        return requestTime;
    }
}