package com.test.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.invoice.entity.Invoice;

@Repository
public interface InvoiceRespository extends JpaRepository<Invoice, Long> {

}
