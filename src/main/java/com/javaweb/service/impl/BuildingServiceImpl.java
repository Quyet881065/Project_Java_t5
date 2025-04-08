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
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired // Tim kiem interface BuildingRepository va inject(tiem) cac phuong thuc .Vi
				// interface khong dung de khoi tao doi tuong
	private BuildingRepository buildingRepository;
	@Autowired
	private BuildingConverter buildingConverter;
	@Autowired
	private BuildingConverter1 buildingConverter1;
	@Autowired
	private BuildingSearchBuilderConverter buildingSearchBuilderConverter;

	@Override
	public List<BuildingResponseDTO> findAll(Map<String, Object> params, List<String> typeCode) {
		BuildingSearchBuilder builder = buildingSearchBuilderConverter.toBuildingSearchBuilder(params, typeCode);
		List<BuildingEntity> buildingEntitys = buildingRepository.findAll(builder);
//		List<BuildingEntity> buildingEntitys = buildingRepository.findByNameContainingAndWardContaining("building","phuong");
		//BuildingEntity buildings = buildingRepository.findById(2L).get();
		List<BuildingResponseDTO> results = new ArrayList<BuildingResponseDTO>();
		for (BuildingEntity it : buildingEntitys) { // filter
			BuildingResponseDTO buildingResponseDTO = buildingConverter.buildingResponseDTO(it);
			
			results.add(buildingResponseDTO);
		}
		return results;
	}

	@Override
	public BuildingEntity createOrUpdateBuilding(BuildingBeans buildingBean) {
//		List<BuildingEntity> buildingEntity = new ArrayList<BuildingEntity>();
		BuildingEntity buildingEntity = buildingConverter1.buildingEntity(buildingBean);
		DistrictEntity districtEntity = entityManager.find(DistrictEntity.class, buildingBean.getDistrictId());
		buildingEntity.setDistrict(districtEntity);
		entityManager.persist(buildingEntity); 
		return buildingEntity;
	}

}
