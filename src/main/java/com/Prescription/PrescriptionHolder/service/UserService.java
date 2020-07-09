package com.Prescription.PrescriptionHolder.service;

import java.util.Arrays;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Prescription.PrescriptionHolder.model.PrescriptionModel;
import com.Prescription.PrescriptionHolder.model.User;
import com.Prescription.PrescriptionHolder.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public Optional<User> findById(String id) {
		return userRepository.findById(id);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}

	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepository.findByEmail(username);
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
	}

	public void update(User userCheck) {
		// TODO Auto-generated method stub
		 Document document = new Document();
		    mongoTemplate.getConverter().write(userCheck, document);
		    Update update = Update.fromDocument(document);
		    Query query = new Query(Criteria.where("email").is(userCheck.getEmail()));

		    mongoTemplate.upsert(query, update, User.class);
	}

}
