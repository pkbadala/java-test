package com.test.invoice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Invoice item request
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItemRequest {
	
	private String description;
	private Long quantity;
	private BigDecimal unitPrice;

}
