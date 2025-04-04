package com.javaweb.beans;

import java.util.List;

public class BuildingBeans {
	 private String name ;
	    private String ward;
	    private Long numberOfBasement;
	    private Long rentPrice;
	    private List<String> typeCode;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getWard() {
			return ward;
		}
		public void setWard(String ward) {
			this.ward = ward;
		}
		public Long getNumberOfBasement() {
			return numberOfBasement;
		}
		public void setNumberOfBasement(Long numberOfBasement) {
			this.numberOfBasement = numberOfBasement;
		}
		public Long getRentPrice() {
			return rentPrice;
		}
		public void setRentPrice(Long rentPrice) {
			this.rentPrice = rentPrice;
		}
		public List<String> getTypeCode() {
			return typeCode;
		}
		public void setTypeCode(List<String> typeCode) {
			this.typeCode = typeCode;
		}
	    
	    
}
