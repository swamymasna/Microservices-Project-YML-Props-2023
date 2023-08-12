package com.swamy.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetails {

	private String message;
	private HttpStatus status;
	private Integer statusCode;
	private LocalDateTime timeStamp;
	private String path;
}
