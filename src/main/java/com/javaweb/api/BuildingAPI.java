package com.javaweb.api;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.beans.BuildingBeans;
import com.javaweb.beans.response.BuildingResponseDTO;
import com.javaweb.customexceptions.InvalidDataException;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@RestController
@PropertySource("classpath:application.properties")
@Transactional  //Đảm bảo tính transactional
// một annotation trong Spring Framework, được sử dụng để đánh dấu một class là
// một RESTful web service controller
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;  
	@PersistenceContext
	private EntityManager entityManager;

	@Value("${dev.nguyen}")
	private String tmp;
	@GetMapping(value = "/api/buildings")
	public Object getBuildings( @RequestParam Map<String,Object> params,
			                     @RequestParam (name="typeCode", required= false) List<String> typeCode) {  
		System.out.println(tmp);
		List<BuildingResponseDTO> result = buildingService.findAll(params, typeCode);  
		return result;
	}

	private void validate(BuildingBeans buildingBeans) throws InvalidDataException {
		if (buildingBeans.getName() == null || buildingBeans.getName().equals("")) {
			throw new InvalidDataException("Building name is required.");
		}
	}
	
	@PostConstruct
	public void init() {
	    System.out.println(">>> BUILDING SERVICE: " + buildingService);
	}

//	@PersistenceContext
//	private EntityManager entityManager;
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

//	@PostMapping(value = "/api/buildings")
//	public void createUpdateBuilding(@RequestBody BuildingBeans building) throws InvalidDataException {
//		validate(building);
//		BuildingEntity buildingEntity = new BuildingEntity();
//		if (building != null) {
//			buildingEntity = entityManager.find(BuildingEntity.class, building.getId());
//		}
//		buildingEntity.setName(building.getName());
//		buildingEntity.setStreet(building.getStreet());
//		buildingEntity.setWard(building.getWard());
//		buildingEntity.setRentPrice(building.getRentPrice());
//		DistrictEntity districtEntity = entityManager.find(DistrictEntity.class, building.getDistrictId());
//		buildingEntity.setDistrict(districtEntity);
//		entityManager.merge(buildingEntity);
//	}
	
	@PostMapping(value = "/api/buildings")
	public void createBuilding(@RequestBody BuildingBeans buildingBeans) {
		//BuildingEntity buildingEntity = buildingService.createBuilding(buildingBeans);
		buildingService.createBuilding(buildingBeans);
	}
	
//
//	@DeleteMapping(value = "/api/buildings/{ids}")
//	public void deleteBuilding(@PathVariable List<Long> ids) {
//		for (Long id : ids) {
//			BuildingEntity buildingEntity = entityManager.find(BuildingEntity.class, id);
//			entityManager.remove(buildingEntity);
//		}
//	}
	
	@Autowired
	private BuildingRepository buildingRepository;
    @DeleteMapping(value = "/api/buildings/{ids}")
    public void findBuilding(@PathVariable Long[] ids) {
    	buildingService.deleteBuilding(ids);
    	System.out.print("ok");
    }
}
