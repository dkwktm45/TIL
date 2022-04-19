package com.sp.fc.web.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession {

    private String username;
    private List<SessionInfo> sessionInfoList;

    public int getCount(){
        return sessionInfoList.size();
    }
}
