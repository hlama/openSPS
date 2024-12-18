package com.purchasing.health.repositories;

import com.purchasing.health.models.Bank;
import com.purchasing.health.models.AccountBank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountBankRepository extends CrudRepository<AccountBank,Long> {

    @Query("select account.idorgunit from AccountBank account")
    List<Long> IdOrgunits();
    AccountBank findByIdorgunit(Long idorgunit);
    List<AccountBank> findByBank(Bank bank);
    AccountBank findByAccountnumber(String accountnumber);
}
