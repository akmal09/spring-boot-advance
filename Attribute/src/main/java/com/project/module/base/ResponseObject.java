package com.project.module.base;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    private Boolean success;
    private Integer code;
    private String message;
    private Object data;
}
