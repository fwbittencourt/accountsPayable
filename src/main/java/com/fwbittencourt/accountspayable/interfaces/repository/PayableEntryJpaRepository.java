package com.fwbittencourt.accountspayable.interfaces.repository;

import com.fwbittencourt.accountspayable.interfaces.entity.TbPayableEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */

public interface PayableEntryJpaRepository extends JpaRepository<TbPayableEntry, UUID>, JpaSpecificationExecutor<TbPayableEntry> {

    @Query("select sum(t.value) from TbPayableEntry t where t.paymentDate between :initialDate and :finalDate and t.status = 'PAID'")
    BigDecimal sumPaidAmountBetweenDates(LocalDate initialDate, LocalDate finalDate);
}
