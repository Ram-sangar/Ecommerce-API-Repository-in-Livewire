package com.SpringBoot.EcommerceSiteProject.Payment.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChargeRequest {
    private String token;
    private Long amount;

    // getters and setters
}