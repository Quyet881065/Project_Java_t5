package com.javaweb.api;

import java.util.List;
import java.util.Map;

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
import com.javaweb.service.BuildingService;

@RestController // một annotation trong Spring Framework, được sử dụng để đánh dấu một class là
// một RESTful web service controller
@PropertySource("classpath:application.properties")
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
	
	@PostMapping(value = "/api/buildings")
	private Object createBuilding(@RequestBody BuildingBeans building) throws InvalidDataException {
		validate(building);
	    return building;
	}

}
