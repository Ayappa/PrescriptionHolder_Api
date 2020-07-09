package com.Prescription.PrescriptionHolder.bean;

import java.util.ArrayList;

public class Patient {
  private String patient_name;
  private ArrayList<Tablets> tablets;
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public ArrayList<Tablets> getTablets() {
		return tablets;
	}
	public void setTablets(ArrayList<Tablets> tablets) {
		this.tablets = tablets;
	}
	public Patient(String patient_name, ArrayList<Tablets> tablets) {
		super();
		this.patient_name = patient_name;
		this.tablets = tablets;
	}
	@Override
	public String toString() {
		return "Patient [patient_name=" + patient_name + ", tablets=" + tablets + "]";
	}
		
  
}
