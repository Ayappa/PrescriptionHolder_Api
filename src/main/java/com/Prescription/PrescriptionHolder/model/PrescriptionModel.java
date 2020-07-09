package com.Prescription.PrescriptionHolder.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.Prescription.PrescriptionHolder.bean.Patient;

@Document(collection ="Prescription")

public class PrescriptionModel {
  @Id
  private String id;  
  private String user_id;
  private List<Patient>  patient_list;
  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public List<Patient> getPatient_list() {
		return patient_list;
	}
	public void setPatient_list(List<Patient> patient_list) {
		this.patient_list = patient_list;
	}
	public PrescriptionModel(String id, String user_id, List<Patient>  patient_list) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.patient_list = patient_list;
	}
	public PrescriptionModel() {}
	public PrescriptionModel(String user_id, List<Patient> patient_list) {
		// TODO Auto-generated constructor stub
		super();
		this.user_id = user_id;
		this.patient_list =  patient_list;
	}
	@Override
	public String toString() {
		return "PrescriptionModel [id=" + id + ", user_id=" + user_id + ", patient_list=" + patient_list + "]";
	}
  
  
 
}
