package com.cybrilla.dao;

import com.cybrilla.domain.CurrentAccount;
import org.springframework.data.repository.CrudRepository;

public interface CurrentAccountDao extends CrudRepository<CurrentAccount,Long> {

    CurrentAccount findByAccountNum(int accountNum);
}
