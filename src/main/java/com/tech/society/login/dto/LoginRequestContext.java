package com.tech.society.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginRequestContext {
    private String societyId;
    private String ipAddress;
    private String requestTime;

    private String userName;

    public LoginRequestContext() {
    }

    public LoginRequestContext(String societyId, String ipAddress, String requestTime) {
        this.societyId = societyId;
        this.ipAddress = ipAddress;
        this.requestTime = requestTime;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }
}