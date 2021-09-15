package com.test.invoice.service;

import com.test.invoice.dto.BaseResponse;
import com.test.invoice.dto.InvoiceRequest;
import com.test.invoice.dto.InvoiceResponse;
import com.test.invoice.dto.InvoicesResponse;

public interface InvoiceService {
	
	BaseResponse addInvoice(InvoiceRequest invoiceRequest);
	
	InvoicesResponse getInvoices();
	
	InvoiceResponse getInvoice(Long invoiceId);
}
