package com.demo.movie.moviecatalogueservice.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CatalogItem {	
	private String movieId;
	private String movieName;
	private String movieOverview;
	private int rating;
}
