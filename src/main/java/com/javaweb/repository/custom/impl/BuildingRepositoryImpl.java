package com.javaweb.repository.custom.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.custom.BuildingRepositoryCustom;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtils;

@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;

	public void queryJoin(BuildingSearchBuilder builder, StringBuilder join) {
		Long staffid = builder.getStaffId();
		if (staffid != null) {
			join.append(" JOIN assignmentbuilding as ON b.id = as.buildingid");
		}

		Long rentAreaFrom = builder.getRentAreaFrom();
		Long rentAreaTo = builder.getRentAreaTo();
		if (rentAreaFrom != null || rentAreaTo != null) {
			join.append(" JOIN rentarea ra ON b.id = ra.buildingid");
		}

		if (builder.getTypeCode() != null && !builder.getTypeCode().isEmpty()) {
			join.append(" JOIN buildingrenttype ON buildingrenttype.buildingid = b.id");
			join.append(" JOIN renttype ON renttype.id = buildingrenttype.renttypeid");
		}
	}

	public void querySqlNomal(BuildingSearchBuilder builder, StringBuilder where) {
//		for (Map.Entry<String, String> item : params.entrySet()) {
//			// Duyệt qua từng phần tử trong params bằng cách sử dụng entrySet(),
//			// giúp lấy cả key và value trong mỗi lần lặp.
//			String key = item.getKey(); // Lấy tên khóa (key) của phần tử hiện tại trong params.
//			if (!key.equals("staffid") && !key.equals("typeCode") && !key.startsWith("rentArea")
//					&& !key.startsWith("rentPrice")) {
//				String value = item.getValue().toString();
//				if (NumberUtil.isNumber(value) == true) {
//					where.append(" AND b." + key.toLowerCase() + " = " + value);
//				} else {
//					where.append(" AND b." + key.toLowerCase() + " LIKE '%" + value + "%'");
//				}
//			}
//
//		}

		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields(); // Lay ten cua cac field cua obj dua vao 1
																				// mang
			for (Field item : fields) {
				item.setAccessible(true);
				String fileName = item.getName();
				if (!fileName.equals("staffid") && !fileName.equals("typeCode") && !fileName.startsWith("rentArea")
						&& !fileName.startsWith("rentPrice")) {
                     Object value = item.get(builder);
                     if(value != null) {
                    	 if(item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Interger")) {
                    		 where.append(" AND b." + fileName.toLowerCase() + " = " + value);
                    	 }else if(item.getType().getName().equals("java.lang.String")) {
                    		 where.append(" AND b." + fileName.toLowerCase() + " LIKE '%" + value + "%'");
                    	 }
                     }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void querySqlSpecial(BuildingSearchBuilder builder, StringBuilder where) {
		Long rentAreaFrom = builder.getRentAreaFrom();
		Long rentAreaTo = builder.getRentAreaTo();
		if (rentAreaFrom != null || rentAreaTo != null) {
			where.append(" AND EXISTS ( SELECT * FROM rentarea rt WHERE b.id = rt.buildingid ");
			if (rentAreaFrom != null) {
				where.append(" AND ra.value >=" + rentAreaFrom);
			}
			if (rentAreaTo != null) {
				where.append(" AND ra.value <=" + rentAreaTo);
			}
			where.append(" )");
		}
		Long staffid = builder.getStaffId();
		if (staffid != null) {
			where.append(" AND as.staffId " + staffid);
		}

		Long rentPriceFrom = builder.getRentPriceFrom();
		Long rentPriceTo = builder.getRentPriceTo();
		if (rentPriceFrom != null || rentPriceTo != null) {
			if (rentPriceFrom != null) {
				where.append(" AND b.rentprice >=" + rentPriceFrom);
			}
			if (rentPriceTo != null) {
				where.append(" AND b.rentprice <=" + rentPriceTo);
			}
		}

		// java 7
//		if (typeCode != null && !typeCode.isEmpty()) {
//		List<String> code = new ArrayList<String>();
//		for (String it : typeCode) {
//			code.add("'" + it + "'"); // IN('tang-tret','nguyen-can')
//		}
//		where.append(" AND renttype.code IN (" + String.join(",", code) + ") ");
//	}

		// java 8
		List<String> typeCode = builder.getTypeCode();
		if (typeCode != null && !typeCode.isEmpty()) {
			where.append(" AND (");
			where.append(typeCode.stream().map(item -> "renttype.code LIKE '%" + item + "%'")
					.collect(Collectors.joining(" OR ")));
			where.append(" ) ");
		}
	}

	//@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder builder) {
		//List<BuildingEntity> buildings = new ArrayList<BuildingEntity>();
		StringBuilder sql = new StringBuilder(" SELECT b.* FROM building b ");
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryJoin(builder, sql);
		querySqlNomal(builder, where);
		querySqlSpecial(builder, where);
		sql.append(where).append(" GROUP BY b.id");
        Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();
	}

}
