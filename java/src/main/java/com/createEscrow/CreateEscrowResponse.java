package com.createEscrow;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class CreateEscrowResponse {


    private Data data;
    private String status;
    private String message;

    @JsonNaming(value= PropertyNamingStrategy.SnakeCaseStrategy.class)
    @Getter
    @Setter
    @NoArgsConstructor
    public class Data {

        private String txnNo;
        private String state;
        private String subState;
        private String txnType;
        private String invoiceCurrency;
        private double invoiceAmount;
        private String feeTier;
        private String feePaidBy;
        private float feeTierPercentage;
        private float feeAmount;
        private float collectAmount;
        private float disburseAmount;
    }
}
