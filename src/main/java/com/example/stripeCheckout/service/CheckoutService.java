package com.example.stripeCheckout.service;

import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class CheckoutService {

	//Method to create checkout session
	//It takes the success & cancel URLs as parameter
	//Throws stripeException if API fails
	public String createCheckoutSession(String successURL, String cancelURL) throws StripeException{
		
		SessionCreateParams params = SessionCreateParams.builder()
				//sets the session mode to payment (Stripe supports payment, subscription, setup).
				.setMode(SessionCreateParams.Mode.PAYMENT)
				//defines redirection URL to success if payment is successful
				.setSuccessUrl(successURL)
//				defines redirection URL to cancel if payment is not successful
				.setCancelUrl(cancelURL)
				//Add one Item to the session
				.addLineItem(
						SessionCreateParams.LineItem.builder()
						//The quantity of the product
						.setQuantity(1L)
						//Sets the product price to $0.00(500 cents
						//defines the currency (USD)
						.setPriceData(
								SessionCreateParams.LineItem.PriceData.builder()
								.setCurrency("usd")
								//Sets the unit price amount
								.setUnitAmount(5000L)//$50
								//sets the name for the Product
								.setProductData(
										SessionCreateParams.LineItem.PriceData.ProductData.builder()
										.setName("Windows Surface Laptop 2025 Intel core")
										.build()
										)
								.build()
								)
						.build()
						)
				.build();
				
		//calls strip API to create a session
//		/Return payment Page uRL
		Session session = Session.create(params);
		return session.getUrl();	//Redirects Payment URL
	}
	
	//To retrive a checkout session by session Id
	public Session retriveCheckoutSession(String sessionId) throws StripeException{
		return Session.retrieve(sessionId);
	}
	
	//To retrive a session by ID and will exprie IT
	public Session expireCheckoutSession(String sessionId) throws StripeException{
		Session session = Session.retrieve(sessionId);
		return session.expire();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
