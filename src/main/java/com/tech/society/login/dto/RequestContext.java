package com.tech.society.login.dto;

import com.tech.society.login.models.UserType;
import io.micrometer.common.util.StringUtils;
import lombok.Data;

@Data
public class RequestContext {
    private String societyIdentifier;
    private String ipAddress;
    private String requestTime;
    private String userName;
    private String userId;
    private UserType userType;

    public RequestContext() {
    }

    public RequestContext(String societyIdentifier, String ipAddress, String requestTime, String userName, String userId, String userTypeStr) {
        this.societyIdentifier = societyIdentifier;
        this.ipAddress = ipAddress;
        this.requestTime = requestTime;
        this.userName = userName;
        this.userId = userId;
        this.userType = StringUtils.isBlank(userTypeStr) ? UserType.valueOf("RESIDENT") : UserType.valueOf(userTypeStr.trim().toUpperCase());
    }


    public String getSocietyIdentifier() {
        return societyIdentifier;
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

    public String getUserId() {
        return userId;
    }

    public UserType getUserType() {
        return userType;
    }
}