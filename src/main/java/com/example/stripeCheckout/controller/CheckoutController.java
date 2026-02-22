package com.example.stripeCheckout.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.stripeCheckout.dto.CheckoutSessionResponse;
import com.example.stripeCheckout.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

//@Restcontroller - Exposes an API to initate this stripe checkout

@Controller
@RequestMapping("/api/checkout")
public class CheckoutController {

	//injects checkout service
	@Autowired
	private CheckoutService checkoutService;
	
	
	@PostMapping("/create-session")
	@ResponseBody		//It ensures JSON response is sent back
	//calls checkout service to get the stripe URL
	public Map<String,Object> createCheckoutSession () throws StripeException{
		String sessionurl = checkoutService.createCheckoutSession("http://localhost:8080/Success.html", "http://localhost:8080/Failure.html");
		Map<String,Object> response = new HashMap<String, Object>();
		response.put("url", sessionurl);
		return response;
	}
	
	// Retreive checkout session API (Fixed)
	@GetMapping("/retrive-session/{sessionId}")
	@ResponseBody //Ensures JSON response is retrived
	public CheckoutSessionResponse retriveCheckoutSession(@PathVariable String sessionId) throws StripeException{
		Session session = checkoutService.retriveCheckoutSession(sessionId);
		
		//Extract required field and return DTO
		return new CheckoutSessionResponse(
				session.getId(),
				session.getPaymentStatus(),
				session.getAmountTotal(), 
				session.getCurrency(),
				session.getStatus(),
				session.getCustomerDetails() != null ? session.getCustomerDetails().getEmail(): "N/A");
	}
	
	//To expire the session
	@PostMapping("/expire-session/{sessionId}")
	@ResponseBody
	public Map<String,Object> expireCheckoutSession (@PathVariable String sessionId) throws StripeException{
		Session expiredSession = checkoutService.expireCheckoutSession(sessionId);
		
		//returns the response in JSON Format
		Map<String,Object> response = new HashMap<String, Object>();
		response.put("sessionId", expiredSession.getId());
		response.put("status",expiredSession.getStatus());
		return response;
	}
	
}
