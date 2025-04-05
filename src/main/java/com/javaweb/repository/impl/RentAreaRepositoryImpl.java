package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "123456";

	@Override
	public List<RentAreaEntity> findByBuildingId(Long buildingid) {
		String sql = " SELECT * FROM rentarea WHERE rentarea.buildingid = " + buildingid;
		List<RentAreaEntity> rentArea = new ArrayList<RentAreaEntity>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql)) {
              while(rs.next()) {
            	  RentAreaEntity rentAreaEntity = new RentAreaEntity();
            	  rentAreaEntity.setId(rs.getLong("id"));
            	  rentAreaEntity.setValue(rs.getLong("value"));
            	  rentAreaEntity.setBuildingId(rs.getLong("buildingid"));
            	  rentArea.add(rentAreaEntity);
              }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rentArea;
	}

}
