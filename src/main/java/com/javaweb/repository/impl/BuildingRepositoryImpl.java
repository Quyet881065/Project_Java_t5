package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtils;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	
	public void queryJoin(Map<String, String> params, List<String> typeCode, StringBuilder join) {
		String staffid = params.get("staffid");
		if (StringUtils.checkData(staffid)) {
			join.append(" JOIN assignmentbuilding as ON b.id = as.buildingid");
		}

		String rentAreaFrom = params.get("rentAreaFrom");
		String rentAreaTo = params.get("rentAreaTo");
		if (StringUtils.checkData(rentAreaFrom) || StringUtils.checkData(rentAreaTo)) {
			join.append(" JOIN rentarea rt ON b.id = rt.buildingid");
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			join.append(" JOIN buildingrenttype ON buildingrenttype.buildingid = b.id");
			join.append(" JOIN renttype ON renttype.id = buildingrenttype.renttypeid");
		}
	}

	public void querySqlNomal(Map<String, String> params, StringBuilder where) {
		for (Map.Entry<String, String> item : params.entrySet()) {
			// Duyệt qua từng phần tử trong params bằng cách sử dụng entrySet(),
			// giúp lấy cả key và value trong mỗi lần lặp.
			String key = item.getKey(); // Lấy tên khóa (key) của phần tử hiện tại trong params.
			if (!key.equals("staffid") && !key.equals("typeCode") && !key.startsWith("rentArea")
					&& !key.startsWith("rentPrice")) {
				String value = item.getValue().toString();
				if (NumberUtil.isNumber(value) == true) {
					where.append(" AND b." + key.toLowerCase() + " = " + value);
				} else {
					where.append(" AND b." + key.toLowerCase() + " LIKE '%" + value + "%'");
				}
			}

		}
	}

	public void querySqlSpecial(Map<String, String> params, List<String> TypeCode, StringBuilder where) {
		String rentAreaFrom = params.get("rentAreaFrom");
		String rentAreaTo = params.get("rentAreaTo");
		if (StringUtils.checkData(rentAreaFrom) || StringUtils.checkData(rentAreaTo)) {
			if (StringUtils.checkData(rentAreaFrom)) {
				where.append(" AND rt.value >=" + rentAreaFrom);
			}
			if (StringUtils.checkData(rentAreaTo)) {
				where.append(" AND rt.value <=" + rentAreaTo);
			}
		}
		String staffid = params.get("staffid");
		if (StringUtils.checkData(staffid)) {
			where.append(" AND as.staffId " + staffid);
		}

		String rentPriceFrom = params.get("rentPriceFrom");
		String rentPriceTo = params.get("rentPriceTo");
		if (StringUtils.checkData(rentPriceFrom) || StringUtils.checkData(rentPriceTo)) {
			if (StringUtils.checkData(rentPriceFrom)) {
				where.append(" AND b.rentprice >=" + rentPriceFrom);
			}
			if (StringUtils.checkData(rentPriceTo)) {
				where.append(" AND b.rentprice <=" + rentPriceTo);
			}
		}

		// java 8
		if (TypeCode != null && !TypeCode.isEmpty()) {
			where.append(" AND (");
			where.append(TypeCode.stream().map(item -> "renttype.code LIKE '%" + item + "%'")
					.collect(Collectors.joining(" OR ")));
			where.append(" ) ");
		}
	}

	@Override
	public List<BuildingEntity> findAll(Map<String, String> params, List<String> typeCode) {
		List<BuildingEntity> buildings = new ArrayList<BuildingEntity>();

		StringBuilder sql = new StringBuilder(" SELECT b.* FROM building b ");
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryJoin(params, typeCode, sql);
		querySqlNomal(params, where);
		querySqlSpecial(params, typeCode, where);
		sql.append(where).append(" GROUP BY b.id");

		try ( Connection conn = ConnectionUtil.getConnection();
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql.toString())) {
            
			while(rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setId(rs.getLong("id"));
				buildingEntity.setName(rs.getString("name"));
				buildingEntity.setNumberOfBasement(rs.getLong("numberofbasement"));
				buildingEntity.setDistrictId(rs.getLong("districtid"));
				buildingEntity.setRentPrice(rs.getLong("rentprice"));
				buildingEntity.setStreet(rs.getString("street"));
				buildingEntity.setWard(rs.getString("ward"));
				buildings.add(buildingEntity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
		}
		return buildings;
	}

}
