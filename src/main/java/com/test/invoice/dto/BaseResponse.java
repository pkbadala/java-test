package com.test.invoice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseResponse {

	// Set some value that never be change.
	public static final String INVOICE_ADDED_SUCCESSFULLY = "Invoice added successfully."; 
	public static final String LIST_INVOICES = "List of all invoices.";
	public static final String INVOICE_DETAILS = "Invoice details.";
	public static final String NO_INVOICE = "No invoice available.";
	public static final String NO_INVOICE_WITH_ID = "No invoice with this ID.";
	
	private ResponseStatus status;

	private String message;

}