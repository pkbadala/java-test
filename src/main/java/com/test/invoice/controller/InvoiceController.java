package com.test.invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.invoice.dto.BaseResponse;
import com.test.invoice.dto.InvoiceRequest;
import com.test.invoice.dto.InvoiceResponse;
import com.test.invoice.dto.InvoicesResponse;
import com.test.invoice.service.InvoiceService;

@RestController
public class InvoiceController {
	
	// Some variable values never be change. So, we define here with final keyword.
	private static final String INVOICES = "invoices";
	
	@Autowired
	private InvoiceService invoiceService;

	// Add new invoice 	
	@PostMapping(INVOICES)
	public ResponseEntity<BaseResponse> addInvoice(@RequestBody InvoiceRequest invoiceRequest) {
	    return new ResponseEntity<BaseResponse>(invoiceService.addInvoice(invoiceRequest), HttpStatus.OK);
	}
	
	// Return list of all invoices
	@GetMapping(INVOICES)
	public ResponseEntity<InvoicesResponse> getInvoices() {
	    return new ResponseEntity<InvoicesResponse>(invoiceService.getInvoices(), HttpStatus.OK);
	}

	// Return particular invoices by invoice ID
	@GetMapping(INVOICES + "/{invoiceId}")
	public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable Long invoiceId) {
	    return new ResponseEntity<InvoiceResponse>(invoiceService.getInvoice(invoiceId), HttpStatus.OK);
	}
}
