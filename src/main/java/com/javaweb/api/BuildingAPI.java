package com.javaweb.api;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.beans.BuildingBeans;
import com.javaweb.beans.response.BuildingResponseDTO;
import com.javaweb.customexceptions.InvalidDataException;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;

@RestController // một annotation trong Spring Framework, được sử dụng để đánh dấu một class là
// một RESTful web service controller
@PropertySource("classpath:application.properties")
@Transactional
public class BuildingAPI {
	
	@Value("${dev.nguyen}")
	private String tmp;

	@Autowired // Giup tim interface , hay cung cap ham khoi tao cho interface
	BuildingService buildingService;

	@GetMapping(value = "/api/buildings")
	@ResponseBody // Chuyen doi data(beans, list, map) tu server tra ra cho client thanh JSON
	private Object getBuilding(@RequestParam Map<String, String> params,
			@RequestParam(name = "typeCode", required = false) List<String> typeCode) {

		System.out.print(tmp);
		
       //List<BuildingEntity> results = buildingRepository.findAll(params, typeCode);
		List<BuildingResponseDTO> results = buildingService.findAll(params, typeCode);
		return results;
	}
	
	private void validate(BuildingBeans buildingBeans) throws InvalidDataException {
		if(buildingBeans.getName() == null || buildingBeans.getName().equals("")) {
			throw new InvalidDataException("Building name is required.");
		}
	}
	
	@PersistenceContext
	private EntityManager entityManager;
//	@PostMapping(value = "/api/buildings")
//	public void createBuilding(@RequestBody BuildingBeans building) throws InvalidDataException {
//		validate(building);
//		BuildingEntity buildingEntity = new BuildingEntity();
//		buildingEntity.setName(building.getName());
//		buildingEntity.setStreet(building.getStreet());
//		buildingEntity.setWard(building.getWard());
//		buildingEntity.setRentPrice(building.getRentPrice());
//		DistrictEntity districtEntity = entityManager.find(DistrictEntity.class, building.getDistrictId());
//		buildingEntity.setDistrict(districtEntity);
//		entityManager.persist(buildingEntity);
//	}
	
	@PostMapping(value = "/api/buildings")
	public void createUpdateBuilding(@RequestBody BuildingBeans building) throws InvalidDataException {
		validate(building);
		BuildingEntity buildingEntity = new BuildingEntity();
		if(building != null) {
			buildingEntity = entityManager.find(BuildingEntity.class, building.getId());
		}
		buildingEntity.setName(building.getName());
		buildingEntity.setStreet(building.getStreet());
		buildingEntity.setWard(building.getWard());
		buildingEntity.setRentPrice(building.getRentPrice());
		DistrictEntity districtEntity = entityManager.find(DistrictEntity.class, building.getDistrictId());
		buildingEntity.setDistrict(districtEntity);
		entityManager.merge(buildingEntity);
	}

}
