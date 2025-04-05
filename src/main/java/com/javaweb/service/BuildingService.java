package com.javaweb.service;

import java.util.List;
import java.util.Map;

import com.javaweb.beans.response.BuildingResponseDTO;

public interface BuildingService {
   List<BuildingResponseDTO> findAll(Map<String, String> params, List<String> typeCode);
}
