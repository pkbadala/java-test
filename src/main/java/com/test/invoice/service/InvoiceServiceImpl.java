package com.test.invoice.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.invoice.dto.BaseResponse;
import com.test.invoice.dto.InvoiceItemResponse;
import com.test.invoice.dto.InvoiceRequest;
import com.test.invoice.dto.InvoiceResponse;
import com.test.invoice.dto.InvoicesResponse;
import com.test.invoice.dto.ResponseStatus;
import com.test.invoice.entity.Invoice;
import com.test.invoice.entity.InvoiceItem;
import com.test.invoice.repository.InvoiceRespository;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private InvoiceRespository invoiceRepository;
	
	// Save new invoice
	public BaseResponse addInvoice(InvoiceRequest invoiceRequest) {
		BaseResponse response = new BaseResponse();
		Invoice invoice = new Invoice();
		invoice.setItems(new HashSet<>());
		invoice.setClient(invoiceRequest.getClient());
		invoice.setVatRate(invoiceRequest.getVatRate());
		invoice.setInvoiceDate(invoiceRequest.getInvoiceDate());
		
		if (invoiceRequest.getLineItems().size() > 0) {
			invoice.setItems(new HashSet<>());
			invoiceRequest.getLineItems().forEach(item -> {
				InvoiceItem invoiceItem = new InvoiceItem();
				invoiceItem.setInvoice(invoice);
				invoiceItem.setDescription(item.getDescription());
				invoiceItem.setQuantity(item.getQuantity());
				invoiceItem.setUnitPrice(item.getUnitPrice());
				invoice.getItems().add(invoiceItem);
			});
		}
		invoiceRepository.save(invoice);
		response.setStatus(ResponseStatus.SUCCESS);
		response.setMessage(BaseResponse.INVOICE_ADDED_SUCCESSFULLY);
		return response;
	}

	//	Get list of all invoices
	public InvoicesResponse getInvoices() {
		InvoicesResponse invoicesResponse = new InvoicesResponse();
		invoicesResponse.setInvoices(new ArrayList<>());

		invoiceRepository.findAll().forEach(invoice -> {
			InvoiceResponse invoiceResponse = new InvoiceResponse();
			invoiceDetail(invoice, invoiceResponse);
			invoicesResponse.getInvoices().add(invoiceResponse);
		});

		if (invoicesResponse.getInvoices().size() == 0) {
			invoicesResponse.setStatus(ResponseStatus.SUCCESS);
			invoicesResponse.setMessage(BaseResponse.NO_INVOICE);
		} else {
			invoicesResponse.setStatus(ResponseStatus.SUCCESS);
			invoicesResponse.setMessage(BaseResponse.LIST_INVOICES);
		}
		return invoicesResponse;
	}

	//	Get all detail of particular invoice
	public InvoiceResponse getInvoice(Long invoiceId) {

		InvoiceResponse invoiceResponse = new InvoiceResponse();
		Optional<Invoice> invoice = null;

		try {
			// fetch invoice detail of particular invoice ID
			invoice = invoiceRepository.findById(invoiceId);
			invoiceDetail(invoice.get(), invoiceResponse);
			
			invoiceResponse.setStatus(ResponseStatus.SUCCESS);
			invoiceResponse.setMessage(BaseResponse.INVOICE_DETAILS);
		} catch (EntityNotFoundException ex) {
			invoiceResponse.setStatus(ResponseStatus.FAILURE);
			invoiceResponse.setMessage(BaseResponse.NO_INVOICE_WITH_ID);
		}

		return invoiceResponse;
	}
	
    // Merge information of invoice and invoice item details	
	private void invoiceDetail(Invoice invoiceInfo, InvoiceResponse invoiceResponse) {
		invoiceResponse.setInvoiceId(invoiceInfo.getId());
		invoiceResponse.setClient(invoiceInfo.getClient());
		invoiceResponse.setVatRate(invoiceInfo.getVatRate());
		invoiceResponse.setInvoiceDate(invoiceInfo.getInvoiceDate());
		invoiceResponse.setLineItems(new ArrayList<>());
		BigDecimal subTotal = new BigDecimal(0);

		for (InvoiceItem invoiceItem : invoiceInfo.getItems()) {
			InvoiceItemResponse invoiceItemResponse = new InvoiceItemResponse();
			invoiceItemResponse.setDescription(invoiceItem.getDescription());
			invoiceItemResponse.setQuantity(invoiceItem.getQuantity());
			invoiceItemResponse.setUnitPrice(invoiceItem.getUnitPrice());
			
			BigDecimal lineItemTotal = invoiceItem.getUnitPrice().multiply(BigDecimal.valueOf(invoiceItem.getQuantity())).setScale(2, RoundingMode.HALF_EVEN);
			invoiceItemResponse.setLineItemTotal(lineItemTotal);
			invoiceResponse.getLineItems().add(invoiceItemResponse);
			subTotal = subTotal.add(lineItemTotal);
		}
		// Set sub total value of invoice
		invoiceResponse.setSubTotal(subTotal.setScale(2, RoundingMode.HALF_EVEN));
		
		// Calculate vat rate on sub total
		BigDecimal vat = invoiceResponse.getSubTotal().multiply(BigDecimal.valueOf(invoiceResponse.getVatRate())).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_EVEN);
		
		// Calculate total of invoice
		invoiceResponse.setTotal(invoiceResponse.getSubTotal().add(vat).setScale(2, RoundingMode.HALF_EVEN));
	}

}
