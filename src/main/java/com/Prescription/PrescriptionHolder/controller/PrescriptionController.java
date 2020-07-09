package com.Prescription.PrescriptionHolder.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.Prescription.PrescriptionHolder.bean.Patient;
import com.Prescription.PrescriptionHolder.jwt.JwtUtil;
import com.Prescription.PrescriptionHolder.model.PrescriptionModel;
import com.Prescription.PrescriptionHolder.service.PrescriptionService;
import com.mongodb.client.result.UpdateResult;

import io.jsonwebtoken.Claims;

@RestController
@CrossOrigin(origins="*")

public class PrescriptionController {
	@Autowired
	PrescriptionService prescriptionService;
	@Autowired 
	private JwtUtil jwt;
	
	
	@PostMapping("/update")
	public UpdateResult  check(@RequestHeader("authToken") String authToken,@RequestBody Patient patient) {
		Claims claim=jwt.extractAllClaims(authToken);
		return prescriptionService.updatePrescription(claim.getSubject(),patient);
	}
	
	@PostMapping("/insertNew")
	public String insert(@RequestBody Patient patient,@RequestHeader("authToken") String authToken) {
		Claims claim=jwt.extractAllClaims(authToken);
		if(prescriptionService.findByUser_id(claim.getSubject()).size()==0) {
			PrescriptionModel prescriptionModel=new PrescriptionModel(claim.getSubject(), Arrays.asList(patient));
			System.out.println(prescriptionModel.toString());
			 prescriptionService.savePrescription(prescriptionModel);
			 return "added New user with prescription";
		}else if(prescriptionService.findByPatientName(claim.getSubject(),patient.getPatient_name()).size()>0){
			return "name already taken";
		}else{
			System.out.println("***inside addToList controller***");

			 prescriptionService.addToList(claim.getSubject(),patient);
			 return "added new prescription";
		}
		
	}
	
	@PostMapping("/remove")
	public UpdateResult remove(@RequestHeader("authToken") String authToken,@RequestHeader("pName") String pName) {
		Claims claim=jwt.extractAllClaims(authToken);
		return prescriptionService.removePrescription(claim.getSubject(),pName);
	}
	
	@GetMapping("/getAll")
	public List<PrescriptionModel> finalAll(@RequestHeader("authToken") String authToken) {
		Claims claim=jwt.extractAllClaims(authToken);
		return prescriptionService.findByUser_id(claim.getSubject());
	}
}
