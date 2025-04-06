package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     
     @Column(name= "username" , nullable = false, unique = true)
     private String userName;
     
     @Column(name= "passwork" ,nullable=false)
     private String passWork;
     
     @Column(name="fullname" )
     private String fullName;
     
     @Column(name= "status", nullable=false)
     private Integer status;
     
     @Column(name="email")
     private String email;

     @OneToMany(mappedBy = "userEntity" , fetch = FetchType.LAZY) // fetch mac dinh la LAZY
     private List<UserRoleEntity> userRoleEntities = new ArrayList<UserRoleEntity>();
     
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWork() {
		return passWork;
	}

	public void setPassWork(String passWork) {
		this.passWork = passWork;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
     
     
}
