package com.demo.movie.moviecatalogueservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.movie.moviecatalogueservice.entity.UserDetails;

@Repository
@Transactional
public interface UserDetailsRepository extends JpaRepository<UserDetails, String>{
	public UserDetails findByUserId(String userId);
}
