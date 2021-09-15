package com.test.invoice.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

// Return response of Invoice info
@Data
@EqualsAndHashCode(callSuper=false)
public class InvoiceResponse extends BaseResponse {
	private Long invoiceId;
	private String client;
	private Long vatRate;
	private Date invoiceDate;
	private BigDecimal subTotal;
	private BigDecimal total;
	private List<InvoiceItemResponse> lineItems;
}
