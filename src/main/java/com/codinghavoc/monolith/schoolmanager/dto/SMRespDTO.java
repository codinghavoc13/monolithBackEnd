package com.codinghavoc.monolith.schoolmanager.dto;

public class SMRespDTO {
    public String message;
    public Object body;

    public SMRespDTO(String m, Object b){
        message = m;
        body = b;
    }
}
