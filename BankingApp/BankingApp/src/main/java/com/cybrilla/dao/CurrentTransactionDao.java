package com.cybrilla.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cybrilla.domain.CurrentTransaction;

public interface CurrentTransactionDao extends CrudRepository<CurrentTransaction, Long> {

    List<CurrentTransaction> findAll();
}
