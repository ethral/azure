package com.tefloncode.azure.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
	
	private final String jwt;
	private boolean active;

}
