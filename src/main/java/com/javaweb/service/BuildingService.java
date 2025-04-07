package com.javaweb.service;

import java.util.List;
import java.util.Map;

import com.javaweb.beans.BuildingBeans;
import com.javaweb.beans.response.BuildingResponseDTO;
import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingService {
   List<BuildingResponseDTO> findAll(Map<String, String> params, List<String> typeCode);
   
   BuildingEntity createBuilding(BuildingBeans buildingBean);
}
