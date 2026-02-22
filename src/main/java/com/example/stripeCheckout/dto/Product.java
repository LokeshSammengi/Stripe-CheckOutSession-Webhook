package com.example.stripeCheckout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	// represents a product with price and currency
	private String name;
	private long price;	//In cents
	private String currency;
	
}
