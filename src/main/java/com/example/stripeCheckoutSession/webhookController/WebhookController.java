package com.example.stripeCheckoutSession.webhookController;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.model.Event;
import com.stripe.net.Webhook;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);
    
    // Replace with your endpoint secret from the Stripe Dashboard
    private static final String ENDPOINT_SECRET = "whsec_b4f875285ec1cf788033eda71b9b81dddcaaa8995cd77f16eca4fcb02c0d23d6";

    public Map<String,Object> handleStripeWebhook(
            @RequestBody String payload, 
            @RequestHeader("Stripe-Signature") String sigHeader) 
    {
    	Map<String,Object> response = new HashMap<String, Object>();
    	//print payload to check if the request is reaching your app
    	logger.info("Recive webhook payload :{}",payload);
    	try {
    		Event event = Webhook.constructEvent(payload, sigHeader, ENDPOINT_SECRET);
    		
    		if("checkout.session.completed".equals(event.getType())) {
    			Session session = (Session) event.getDataObjectDeserializer().getObject().get();
    			logger.info("Payment successful for session ID: {}",session.getId());
    			response.put("status","Payment successful for session ID: "+session.getId());
    		}
    	}
    	catch (Exception e) {
    		logger.error("webhook Error : {} ",e.getMessage());
    		response.put("error","webhook Error: "+e.getMessage());
		}
    	return response;
    }
    
    
    
    
    
    
   /* @PostMapping
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload, 
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;

        try {
            // Verify the signature and construct the event
            event = Webhook.constructEvent(payload, sigHeader, ENDPOINT_SECRET);
        } catch (SignatureVerificationException e) {
            logger.error("Invalid signature: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        // Handle the event
        switch (event.getType()) {
            case "payment_intent.succeeded":
                handlePaymentSucceeded(event);
                break;
            case "payment_intent.payment_failed":
                logger.info("Payment failed for: {}", event.getId());
                break;
            // Add more cases as needed
            default:
                logger.info("Unhandled event type: {}", event.getType());
        }

        return ResponseEntity.ok("Success");
    }

    private void handlePaymentSucceeded(Event event) {
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        if (dataObjectDeserializer.getObject().isPresent()) {
            StripeObject stripeObject = dataObjectDeserializer.getObject().get();
            logger.info("Payment succeeded event processed for ID: {}", event.getId());
            // Cast stripeObject to PaymentIntent to access specific fields
        }
    }*/
}