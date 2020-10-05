package com.demo.movie.moviecatalogueservice.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.movie.moviecatalogueservice.entity.UserDetails;
import com.demo.movie.moviecatalogueservice.repository.UserDetailsRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserDetailsRepository userDetailsRepository;

	
	@Override
	public Boolean validateUser(String userId) {
		final String METHOD_Name = "validateUser";
		LOGGER.info(METHOD_Name+" Starts");
		LOGGER.info("Validating user id {}",userId);
		UserDetails userDetails = userDetailsRepository.findByUserId(userId);
		if (userDetails != null)
			return true;
		return false;
		
	}

}
