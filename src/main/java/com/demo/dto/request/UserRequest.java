package com.demo.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {
	private Long id;
	private String username;
	private String password;
	private String fullname;
	private String email;
	private String phone;
	List<AddressRequest> addressRequestList;
}
