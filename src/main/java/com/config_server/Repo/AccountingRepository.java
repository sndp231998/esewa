package com.config_server.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.config_server.entities.Accounting;

public interface AccountingRepository extends JpaRepository<Accounting, Long> {
}
