package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.beans.response.BuildingResponseDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	BuildingRepository buildingRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private RentAreaRepository rentAreaRepository;

	@Override
	public List<BuildingResponseDTO> findAll(Map<String, String> params, List<String> typeCode) {
		List<BuildingEntity> buildingEntity = buildingRepository.findAll(params, typeCode);
		List<BuildingResponseDTO> buildingResponseDTO = new ArrayList<BuildingResponseDTO>();

		for (BuildingEntity it : buildingEntity) {
			BuildingResponseDTO buildingResponse = new BuildingResponseDTO();
			buildingResponse.setName(it.getName());
			buildingResponse.setNumberOfBasement(it.getNumberOfBasement());
			buildingResponse.setRentPrice(it.getRentPrice());
			DistrictEntity districtEntity = districtRepository.findNameById(it.getDistrictId());
			buildingResponse.setAddress(it.getStreet() + "," + it.getWard()+ "," + districtEntity.getName());
			List<RentAreaEntity> rentAreaEntity = rentAreaRepository.findByBuildingId(it.getId());
			buildingResponse.setRentArea(rentAreaEntity.stream().map(item -> String.valueOf(item.getValue()))
					.collect(Collectors.joining(", ")));
			buildingResponseDTO.add(buildingResponse);
		}
		return buildingResponseDTO;
	}

}
