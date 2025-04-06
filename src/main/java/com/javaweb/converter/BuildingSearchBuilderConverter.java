package com.javaweb.converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.utils.MapUtils;

@Component
public class BuildingSearchBuilderConverter {
     public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, String> params, List<String> typeCode) {
    	 BuildingSearchBuilder builder = new BuildingSearchBuilder.Builder()
    			 .setDistrictid(MapUtils.getObject(params, "districtid", String.class))
	   			  .setFloorArea(MapUtils.getObject(params, "floorArea", Long.class))
	   			  .setLevel(MapUtils.getObject(params, "level", Long.class))
	   			  .setManagerName(MapUtils.getObject(params, "managerName", String.class))
	   			  .setManagerPhoneNumber(MapUtils.getObject(params, "managerPhoneNumber", String.class))
	   			  .setName(MapUtils.getObject(params, "name", String.class))
	   			  .setNumberOfBasement(MapUtils.getObject(params, "numberOfBasement", Long.class))
	   			  .setRentAreaFrom(MapUtils.getObject(params, "rentAreaFrom", Long.class))
	   			  .setRentAreaTo(MapUtils.getObject(params, "rentAreaTo", Long.class))
	   			  .setRentPriceFrom(MapUtils.getObject(params, "rentPriceFrom", Long.class))
	   			  .setRentPriceTo(MapUtils.getObject(params, "rentPriceTo", Long.class))
	   			  .setStaffId(MapUtils.getObject(params, "staffId", Long.class))
	   			  .setStreet(MapUtils.getObject(params, "street", String.class))
	   			  .setTypeCode(typeCode)
	   			  .setWard(null)
	   			  .build();
    	 return builder;
     }
}
