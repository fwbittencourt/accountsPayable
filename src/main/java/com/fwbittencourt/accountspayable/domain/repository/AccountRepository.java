package com.fwbittencourt.accountspayable.domain.repository;

import com.fwbittencourt.accountspayable.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <Filipe Bittencourt> on 09/06/24
 */


public interface AccountRepository extends JpaRepository<Account, Long> {
}
