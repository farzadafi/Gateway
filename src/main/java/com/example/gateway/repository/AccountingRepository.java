package com.example.gateway.repository;

import com.example.gateway.model.Accounting;
import org.springframework.data.repository.CrudRepository;

public interface AccountingRepository extends CrudRepository<Accounting,String> {
}
