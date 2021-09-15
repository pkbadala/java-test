package com.test.invoice.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

//Invoice request
@Data
public class InvoiceRequest {

	private String client;
	private Long vatRate;
	private Date invoiceDate;
	private List<InvoiceItemRequest> lineItems;
}
