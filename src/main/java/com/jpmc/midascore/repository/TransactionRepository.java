package com.jpmc.midascore.repository;

import com.jpmc.midascore.foundation.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableJpaRepositories
public interface TransactionRepository extends CrudRepository<TransactionRecord, Long> {

}
