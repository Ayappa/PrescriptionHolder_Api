package com.Prescription.PrescriptionHolder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.Prescription.PrescriptionHolder.bean.Patient;
import com.Prescription.PrescriptionHolder.controller.PrescriptionController;
import com.Prescription.PrescriptionHolder.model.PrescriptionModel;
import com.Prescription.PrescriptionHolder.repository.PrescriptionRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;

@Service
public class PrescriptionService {
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public PrescriptionModel savePrescription(PrescriptionModel prescriptionModel) {
		return prescriptionRepository.save(prescriptionModel);
	}

	

	public UpdateResult  updatePrescription(String user_id,Patient patient) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("user_id").is(user_id).and("patient_list.patient_name").is(patient.getPatient_name()));
		query.fields().include("patient_list.$");
		Update update=new Update();
		update.set("patient_list.$",patient);
		return mongoTemplate.updateMulti(query, update, PrescriptionModel.class);
		//return mongoTemplate.find(query, PrescriptionModel.class);
		//return prescriptionRepository.findByPatient_listPatient_name(patient_name);
	}



	public List<PrescriptionModel> findByUser_id(String user_id) {
		// TODO Auto-generated method stub
		Query query = new Query();
		 query.addCriteria(Criteria.where("user_id").is(user_id));
		 return mongoTemplate.find(query,  PrescriptionModel.class);
		//return prescriptionRepository.findAllByUserId(user_id);
	}



	public void addToList(String user_id, Patient patient) {
		// TODO Auto-generated method stub
		System.out.println("***inside addToList***");
		Update update=new Update();
		update.addToSet("patient_list",patient);
		Criteria criteria=Criteria.where("user_id").is(user_id);
		System.out.println("addtoList="+ mongoTemplate.updateFirst(Query.query(criteria), update, PrescriptionModel.class));
		 mongoTemplate.updateFirst(Query.query(criteria), update, PrescriptionModel.class);
	}



	public List<PrescriptionModel> findByPatientName(String user_id, String patient_name) {
		// TODO Auto-generated method stub
		Query query = new Query();
		query.addCriteria(Criteria.where("user_id").is(user_id).and("patient_list.patient_name").is(patient_name));
		query.fields().include("patient_list.$");
		return mongoTemplate.find(query, PrescriptionModel.class);
		 
	}


	public UpdateResult removePrescription(String user_id, String patient_name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("user_id").is(user_id).and("patient_list.patient_name").is(patient_name));
		Update update = new Update().pull("patient_list", 
				       new BasicDBObject("patient_name", patient_name));	
		return mongoTemplate.updateMulti(query,update,  PrescriptionModel.class);
	}
}
 