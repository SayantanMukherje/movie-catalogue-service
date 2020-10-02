package com.demo.movie.moviecatalogueservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.movie.moviecatalogueservice.model.CatalogItem;
import com.demo.movie.moviecatalogueservice.model.Movie;
import com.demo.movie.moviecatalogueservice.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieInfo {
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
			}
		)
	public CatalogItem getCatalogItem(String movieInfoServiceUrl, Rating rating) {
		String movieInfoUrl = movieInfoServiceUrl + rating.getMovieId();
		Movie movie = restTemplate.getForObject(movieInfoUrl, Movie.class);

//			Movie movie = webClientBuilder.build()
//				.get()
//				.uri("http://localhost:8082/movies/"+rating.getMovieId())
//				.retrieve()
//				.bodyToMono(Movie.class)
//				.block();
		return new CatalogItem(movie.getMovieId(), movie.getMovieName(), movie.getMovieOverview(), rating.getRating());
	}

	public CatalogItem getFallbackCatalogItem(String movieInfoServiceUrl, Rating rating) {
		return (new CatalogItem("Movie name not found", " ", " ", rating.getRating()));
	}

}
