package com.createEscrow;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value= PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateEscrowRequest {

    private String txnType;
    private String releaseMechanism;
    private String buyerId;
    private String sellerId;
    private String txnDescription;
    private String invoiceCurrency;
    private double invoiceAmount;
    private String feeTier;
    private String feePaidBy;
    private float feePercentage;
    private String initiatedBy;
}
