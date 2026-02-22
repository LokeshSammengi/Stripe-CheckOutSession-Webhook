package com.example.stripeCheckout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class CheckoutSessionResponse {

	private String sessionId;
	private String paymentStatus;
	private long amountTotal;
	private String currency;
	private String status;
	private String customerEmail;
	
}
