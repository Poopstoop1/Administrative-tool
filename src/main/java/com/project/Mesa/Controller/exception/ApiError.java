package com.project.Mesa.Controller.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
	public String message;
    public String error;
    public int status;
    public String path;
    public LocalDateTime timestamp;
}
