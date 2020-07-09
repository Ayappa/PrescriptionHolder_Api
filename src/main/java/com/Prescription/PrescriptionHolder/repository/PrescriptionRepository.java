package com.Prescription.PrescriptionHolder.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.Repository;

import com.Prescription.PrescriptionHolder.bean.Patient;
import com.Prescription.PrescriptionHolder.controller.PrescriptionController;
import com.Prescription.PrescriptionHolder.model.PrescriptionModel;

public interface PrescriptionRepository extends MongoRepository<PrescriptionModel,String>  {


	PrescriptionModel save(PrescriptionModel prescriptionModel);
    
	@Query(value="{'user_id':'5','patient_list.patient_name':?0}",fields="{'patient_list.patient_name':?0}")
	PrescriptionModel findByPatient_listPatient_name(String patient_name);


	

	
	

}
