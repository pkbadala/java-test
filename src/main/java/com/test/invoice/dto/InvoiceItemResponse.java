package com.test.invoice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Return response of Invoice line item details
@Data
public class InvoiceItemResponse {
	
	private String description;
	private Long quantity;
	private BigDecimal unitPrice;
	private BigDecimal lineItemTotal;

}
