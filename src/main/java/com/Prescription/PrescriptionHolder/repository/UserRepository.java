package com.Prescription.PrescriptionHolder.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Prescription.PrescriptionHolder.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);

}
