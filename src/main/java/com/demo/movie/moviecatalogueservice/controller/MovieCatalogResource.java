package com.demo.movie.moviecatalogueservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive.function.client.WebClient;

import com.demo.movie.moviecatalogueservice.model.CatalogItem;
import com.demo.movie.moviecatalogueservice.model.UserRating;
import com.demo.movie.moviecatalogueservice.service.MovieInfo;
import com.demo.movie.moviecatalogueservice.service.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private UserRating userRating;

	@Autowired
	private MovieInfo movieInfo;

	@Autowired
	private UserRatingInfo userRatingInfo;

	@Value("${url.movie.info.service}")
	private String movieInfoServiceUrl;

	@Value("${url.rating.data.service}")
	private String ratingDataServiceUrl;

//	@Autowired
//	WebClient.Builder webClientBuilder;

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		userRating = userRatingInfo.getUserRatings(ratingDataServiceUrl, userId);

		return userRating.getUserRating().stream().map(rating -> {
			return movieInfo.getCatalogItem(movieInfoServiceUrl, rating);
		}).collect(Collectors.toList());

		// Put them together
//		return Collections.singletonList(
//					new CatalogItem("Transformers", "Test", 4)
//				);		
	}

}
