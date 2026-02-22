package com.example.stripeCheckout.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

//marks this class as configuration class, allowing spring to load it during application stratup
@Configuration
public class StripeConfig {

	@Value("${stripe.secret-key}")
	private String secretKey;
	
	//@postconstruct - ensures that this method runs after the class is instantiated
	//It sets the stripe.apikey globall for the stripe SDK
	//PURPOSE - It configures stripe before making any API Calls
//	@PostConstruct
//	public void init() {
//		Stripe.apiKey = secretKey;
//	}
//	
	@PostConstruct
	public void setup() {
	    if (secretKey == null || secretKey.contains("{")) {
	        System.err.println("ERROR: Stripe Key failed to load!");
	    } else {
	        Stripe.apiKey = secretKey;
	    }
	}
}
