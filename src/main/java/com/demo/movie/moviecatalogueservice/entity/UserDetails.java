package com.demo.movie.moviecatalogueservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel (description = "UserDetails Model")
public class UserDetails {
	
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty (notes = "User ID of the user", name = "userId", required = true, value = "1")
	private String userId;
	
	@Id
	@ApiModelProperty (notes = "User Name of the user", name = "userName", required = true, value = "username")
	private String userName;
	
	@Column
	@ApiModelProperty (notes = "Password of the user", name = "password", required = true, value = "password")
	private String password;
}
