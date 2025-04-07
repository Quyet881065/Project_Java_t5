package com.javaweb.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.javaweb.beans.BuildingBeans;
import com.javaweb.repository.entity.BuildingEntity;

@Configuration
public class BuildingConverter1 {
   @Autowired
   ModelMapper modelMapper;
   public BuildingEntity building(BuildingBeans buildingBean) {
	   BuildingEntity buildingEntity = modelMapper.map(buildingBean, BuildingEntity.class);
	   return buildingEntity;
   }
}
