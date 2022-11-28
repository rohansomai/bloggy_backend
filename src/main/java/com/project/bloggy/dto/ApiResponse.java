package com.project.bloggy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private Object data;

}
