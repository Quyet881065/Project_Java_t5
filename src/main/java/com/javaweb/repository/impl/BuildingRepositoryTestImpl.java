package com.javaweb.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;


@Repository
public class BuildingRepositoryTestImpl {

//	@PersistenceContext
//	private EntityManager entityManager;
//	
//	@Override
//	public List<BuildingEntity> findAll(BuildingSearchBuilder builder) {
//		// JPQL
////		String sql = " FROM BuildingEntity b WHERE 1=1 AND b.name like '%tower%' AND b.district.id = 2 ";
////		Query query = entityManager.createQuery(sql , BuildingEntity.class);
//		
//		// SQL Native
//		String sql = "SELECT b.* FROM building b WHERE 1=1 AND b.name like '%tower%'";
//		Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
//		
//		return query.getResultList();
//	}

}
