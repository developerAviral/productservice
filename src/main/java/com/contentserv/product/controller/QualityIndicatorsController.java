package com.contentserv.product.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.contentserv.product.exception.MethodNotImplementedException;

@RestController
@RequestMapping("/products/quality")
public class QualityIndicatorsController {

	@GetMapping("/audit")
	public String getAuditDetails() {
		throw new MethodNotImplementedException("Audit details method is not implemented yet.");
	}
	
	@GetMapping("/httpTrace")
	public String getHttpTraceDetails() {
		throw new MethodNotImplementedException("Http trace details method is not implemented yet.");
	}
	
	@GetMapping("/metrics")
	public String getMetricsDetails() {
		throw new MethodNotImplementedException("Metrics details method is not implemented yet.");
	}
	
	@GetMapping("/threadDumps")
	public String getThreadDumpsDetails() {
		throw new MethodNotImplementedException("Thread Dump details method is not implemented yet.");
	}
	
	@RequestMapping(value="/" , method = RequestMethod.OPTIONS)
	public ResponseEntity<Object> getQuaityIndicatorsOptions() {
		return ResponseEntity
				.ok()
				.allow(HttpMethod.GET, HttpMethod.OPTIONS)
				.build();
	}
}
