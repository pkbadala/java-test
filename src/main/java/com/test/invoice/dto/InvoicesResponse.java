package com.test.invoice.dto;

import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

// Return response of List of all Invoices
@Data
@EqualsAndHashCode(callSuper=false)
public class InvoicesResponse extends BaseResponse {

	// We are returning list of invoices
	private Collection<InvoiceResponse> invoices;
}
