package com.config_server.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.config_server.entities.EsewaPaymentRequest;

public interface EsewaPaymentRequestRepository extends JpaRepository<EsewaPaymentRequest, Long> {
}