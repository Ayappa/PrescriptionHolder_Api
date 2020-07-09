package com.Prescription.PrescriptionHolder.bean;

public class Tablets {
   private String tablet_name;
   private int tablet_count;
	public String getTablet_name() {
		return tablet_name;
	}
	public void setTablet_name(String tablet_name) {
		this.tablet_name = tablet_name;
	}
	public int getTablet_count() {
		return tablet_count;
	}
	public void setTablet_count(int tablet_count) {
		this.tablet_count = tablet_count;
	}
	public Tablets(String tablet_name, int tablet_count) {
		super();
		this.tablet_name = tablet_name;
		this.tablet_count = tablet_count;
	}
	@Override
	public String toString() {
		return "Tablets [tablet_name=" + tablet_name + ", tablet_count=" + tablet_count + "]";
	}
		
	
}
