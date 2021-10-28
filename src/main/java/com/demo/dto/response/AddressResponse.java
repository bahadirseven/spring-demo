package com.demo.dto.response;

import com.demo.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressResponse {
	private Long id;
	private String detail;

	public static AddressResponse fromAddress(Address address) {
		return new AddressResponse(
				address.getId(),
				address.getDetail());
	}
}
