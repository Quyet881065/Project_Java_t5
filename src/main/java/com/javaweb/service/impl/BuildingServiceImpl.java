package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.beans.BuildingBeans;
import com.javaweb.beans.response.BuildingResponseDTO;
import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingConverter;
import com.javaweb.converter.BuildingConverter1;
import com.javaweb.converter.BuildingSearchBuilderConverter;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {
	// Tim kiem interface BuildingRepository va inject(tiem) cac phuong thuc .Vi
	// interface khong dung de khoi tao doi tuong
	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	BuildingConverter buildingConverter;

	@Autowired
	private BuildingSearchBuilderConverter buildingSearchBuilderConverter;
	
	@Autowired
	BuildingConverter1 buildingConverter1;
	
	@PersistenceContext 
	private EntityManager entityManager;

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

	@Override
	public BuildingEntity createBuilding(BuildingBeans buildingBean) {
		//List<BuildingEntity> buildingEntitys = new ArrayList<BuildingEntity>();
		BuildingEntity buildingEntity = buildingConverter1.building(buildingBean);
		DistrictEntity districtEntity = entityManager.find(DistrictEntity.class, buildingBean.getDistrictId());
		buildingEntity.setDistrict(districtEntity);
		entityManager.persist(buildingEntity);
		return buildingEntity;
	}

}
