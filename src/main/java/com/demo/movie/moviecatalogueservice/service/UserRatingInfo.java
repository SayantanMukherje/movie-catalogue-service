package com.demo.movie.moviecatalogueservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.movie.moviecatalogueservice.model.Rating;
import com.demo.movie.moviecatalogueservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserRatingInfo {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackUserRatings",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000000"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "50"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "500"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "50000")
			}
		)
	public UserRating getUserRatings(String ratingUrl, String userId) {
		return restTemplate.getForObject(ratingUrl+userId, UserRating.class);
	}

	public UserRating getFallbackUserRatings(String ratingUrl, String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRating(Arrays.asList(new Rating("0", 0)));
		return userRating;
	}
}
