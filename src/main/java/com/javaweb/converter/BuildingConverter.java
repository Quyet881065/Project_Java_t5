package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.javaweb.beans.response.BuildingResponseDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;

@Configuration // Đây là nơi định nghĩa các bean 
// Trong Spring, một Bean là một đối tượng (object) được Spring quản lý vòng đời.
// Bean = một class thông thường mà Spring tạo ra, lưu lại, inject vào nơi cần dùng.

// Bean để làm gì?
//Tái sử dụng: tạo một lần dùng nhiều nơi
//Tự động inject: dùng @Autowired hoặc @Inject để lấy ra mà không cần new

public class BuildingConverter {
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingResponseDTO buildingResponseDTO(BuildingEntity it) {
		BuildingResponseDTO buildingResponse = modelMapper.map(it, BuildingResponseDTO.class);
		buildingResponse.setAddress(it.getStreet() + "," + it.getWard()+ "," + it.getDistrict().getName());
		buildingResponse.setRentArea(it.getRentAreas().stream().map(item -> item.getValue().toString())
				.collect(Collectors.joining(", ")));
		return buildingResponse;
	}
}
