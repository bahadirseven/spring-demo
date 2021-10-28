package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.request.AddressRequest;
import com.demo.dto.request.UserRequest;
import com.demo.dto.response.UserResponse;
import com.demo.entity.Address;
import com.demo.entity.User;
import com.demo.exception.UserNotFoundException;
import com.demo.repository.UserRepository;

@Service
public class UserService {

	private static final String USER_NOT_FOUND = "User not found.";
	@Autowired
	private UserRepository userRepository;

	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
	}

	public UserResponse getUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
		return UserResponse.fromUser(user);
	}

	public UserResponse createOrUpdateUser(UserRequest userRequest) {
		User user = null;
		if (userRequest.getId() == null) {

			List<Address> addressList = new ArrayList<>();
			for (AddressRequest addressRequest : userRequest.getAddressRequestList()) {
				addressList.add(new Address(null, addressRequest.getDetail(), null));
			}

			user = User.builder()
					.username(userRequest.getUsername())
					.password(userRequest.getPassword())
					.fullname(userRequest.getFullname())
					.email(userRequest.getEmail())
					.phone(userRequest.getPhone())
					.userAddressList(addressList)
					.build();
		} else {
			user = userRepository.findById(userRequest.getId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
			user.setUsername(userRequest.getUsername() == null ? user.getUsername() : userRequest.getUsername());
			user.setPassword(userRequest.getPassword() == null ? user.getPassword() : userRequest.getPassword());
			user.setFullname(userRequest.getFullname() == null ? user.getFullname() : userRequest.getFullname());
			user.setEmail(userRequest.getEmail() == null ? user.getEmail() : userRequest.getEmail());
			user.setPhone(userRequest.getPhone() == null ? user.getPhone() : userRequest.getPhone());
		}
		User savedUser = userRepository.save(user);
		return UserResponse.fromUser(savedUser);
	}

	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}
}