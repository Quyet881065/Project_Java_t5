package com.javaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@RestController // một annotation trong Spring Framework, được sử dụng để đánh dấu một class là
// một RESTful web service controller
public class BuildingAPI {

	@Autowired // Giup tim interface , hay cung cap ham khoi tao cho interface
	BuildingRepository buildingRepository;

	@GetMapping(value = "/api/buildings")
	@ResponseBody // Chuyen doi data(beans, list, map) tu server tra ra cho client thanh JSON
	private Object getBuilding(@RequestParam Map<String, String> params,
			@RequestParam(name = "typeCode", required = false) List<String> typeCode) {

       List<BuildingEntity> results = buildingRepository.findAll(params, typeCode);
		//List<BuildingResponseDTO> results = buildingService.findAll(params, typeCode);
		return results;
	}

}
