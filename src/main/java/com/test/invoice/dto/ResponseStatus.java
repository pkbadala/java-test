package com.test.invoice.dto;

/* 
 * Making some enum type, it will useful for return status of every API
 * If response status is SUCCESS : it mean no error or no exception and successful return response.
 * And if response status is FAILURE : it mean there is some error or exception that's why API not execute properly.
*/
public enum ResponseStatus {
	SUCCESS, FAILURE;
}
