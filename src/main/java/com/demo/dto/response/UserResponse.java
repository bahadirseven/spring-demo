package com.demo.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
	private Long id;
	@JsonIgnore
	private String password;
	private String username;
	private String fullname;
	private String email;
	private String phone;
	private List<AddressResponse> addressResponse;

	public static UserResponse fromUser(User user) {
		return new UserResponse(
				user.getId(),
				user.getPassword(),
				user.getUsername(),
				user.getFullname(),
				user.getEmail(),
				user.getPhone(),
				user.getUserAddressList().stream().map(AddressResponse::fromAddress).collect(Collectors.toList()));
	}
}
