package com.test.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.invoice.dto.BaseResponse;
import com.test.invoice.dto.InvoiceItemRequest;
import com.test.invoice.dto.InvoiceRequest;
import com.test.invoice.dto.InvoiceResponse;
import com.test.invoice.dto.ResponseStatus;
import com.test.invoice.entity.Invoice;
import com.test.invoice.service.InvoiceService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InvoiceApplication.class)
class InvoiceApplicationTests {

	@Autowired
	private InvoiceService invoiceService;
	
	// Add invoice test case
	@Test
	void addInvoiceTest() {
		BaseResponse response = addInvoice();
		System.out.println("Add Invoice response: " + response);
		assert(response.getStatus().equals(ResponseStatus.SUCCESS));
	}
	
	// List of all invoices test case
	@Test
	void getInvoicesTest() {
		addInvoice();
		BaseResponse response = invoiceService.getInvoices();
		System.out.println("Get all Invoice response: " + response);
		assert(response.getStatus().equals(ResponseStatus.SUCCESS));
	}
	
	// Invoices detail from particular invoice ID test case
	@Test
	void getInvoiceTest() {
		addInvoice();
		BaseResponse response = invoiceService.getInvoice(1L);
		System.out.println("Get Invoice details by invoice ID response: " + response);
		assert(response.getStatus().equals(ResponseStatus.SUCCESS));
	}
	
	// common function for add invoice record
	private BaseResponse addInvoice() {
		InvoiceRequest invoiceRequest = new InvoiceRequest();
		
		List<InvoiceItemRequest> lineItems = new ArrayList<InvoiceItemRequest>();
		InvoiceItemRequest invoiceItemRequest1 = new InvoiceItemRequest("test description 1", 10L, new BigDecimal(1000));
		InvoiceItemRequest invoiceItemRequest2 = new InvoiceItemRequest("test description 2", 15L, new BigDecimal(1500));
		InvoiceItemRequest invoiceItemRequest3 = new InvoiceItemRequest("test description 3", 12L, new BigDecimal(2000));
		
		lineItems.add(invoiceItemRequest1);
		lineItems.add(invoiceItemRequest2);
		lineItems.add(invoiceItemRequest3);

		invoiceRequest.setClient("Testing Client");
		invoiceRequest.setInvoiceDate(new Date());
		invoiceRequest.setVatRate(10L);
		invoiceRequest.setLineItems(lineItems);

		return invoiceService.addInvoice(invoiceRequest);
	}
}
