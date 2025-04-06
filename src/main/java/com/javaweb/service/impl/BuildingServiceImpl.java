package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.beans.response.BuildingResponseDTO;
import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingConverter;
import com.javaweb.converter.BuildingSearchBuilderConverter;
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
	// Tim kiem interface BuildingRepository va inject(tiem) cac phuong thuc .Vi
	// interface khong dung de khoi tao doi tuong
	BuildingRepository buildingRepository;
	
	@Autowired
	BuildingConverter buildingConverter;
	
	 @Autowired
	    private BuildingSearchBuilderConverter buildingSearchBuilderConverter;

	@Override
	public List<BuildingResponseDTO> findAll(Map<String, String> params, List<String> typeCode) {
		BuildingSearchBuilder builder = buildingSearchBuilderConverter.toBuildingSearchBuilder(params, typeCode);
		List<BuildingEntity> buildingEntity = buildingRepository.findAll(builder);
		List<BuildingResponseDTO> result = new ArrayList<BuildingResponseDTO>();
		for (BuildingEntity it : buildingEntity) {
			BuildingResponseDTO buildingResponse = buildingConverter.buildingResponseDTO(it);
			result.add(buildingResponse);
		}
		return result;
	}

}
