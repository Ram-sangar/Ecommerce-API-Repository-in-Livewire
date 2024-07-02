package com.SpringBoot.EcommerceSiteProject.Payment.Controller;

import com.SpringBoot.EcommerceSiteProject.Payment.model.ChargeRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StripePaymentController {


    private String currency = "INR";

    @PostMapping("/charge")
    public ResponseEntity<String> chargeCard(@RequestBody ChargeRequest chargeRequest) {
        try {
            ChargeCreateParams createParams = new ChargeCreateParams.Builder()
                    .setAmount(chargeRequest.getAmount())
                    .setCurrency(currency)
                    .setSource(chargeRequest.getToken())
                    .build();

            Charge charge = Charge.create(createParams);
            return ResponseEntity.ok("Payment successful: " + charge.getId());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: " + e.getMessage());
        }
    }
}