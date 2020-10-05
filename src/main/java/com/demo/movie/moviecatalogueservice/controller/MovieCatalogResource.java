package com.demo.movie.moviecatalogueservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.demo.movie.moviecatalogueservice.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation (value = "/catalog", tags = "MovieCatalogueController")
@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	private static final Logger LOGGER = LogManager.getLogger(MovieCatalogResource.class);
	
	@Autowired
	private UserRating userRating;

	@Autowired
	private MovieInfo movieInfo;

	@Autowired
	private UserRatingInfo userRatingInfo;
	
	@Autowired
	UserService userService;

	@Value("${url.movie.info.service}")
	private String movieInfoServiceUrl;

	@Value("${url.rating.data.service}")
	private String ratingDataServiceUrl;

//	@Autowired
//	WebClient.Builder webClientBuilder;

	@ApiOperation (value = "Fetch all movie details for a particular user", response = Iterable.class)
	@ApiResponses ( value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = CatalogItem.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZED", response = CatalogItem.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = CatalogItem.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = CatalogItem.class)
	})
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		final String METHOD_NAME = "getCatalog";
		LOGGER.info(METHOD_NAME+" STARTS");
		if (userService.validateUser(userId)) {
			userRating = userRatingInfo.getUserRatings(ratingDataServiceUrl, userId);
	
			return userRating.getUserRating().stream().map(rating -> {
				return movieInfo.getCatalogItem(movieInfoServiceUrl, rating);
			}).collect(Collectors.toList());
		}
		else {
			LOGGER.error("Could not find user id {}",userId);
			return new ArrayList<CatalogItem>();
		}
		// Put them together
//		return Collections.singletonList(
//					new CatalogItem("Transformers", "Test", 4)
//				);		
	}

}
