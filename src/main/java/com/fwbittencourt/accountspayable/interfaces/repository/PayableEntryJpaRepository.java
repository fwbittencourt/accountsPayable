package com.fwbittencourt.accountspayable.interfaces.repository;

import com.fwbittencourt.accountspayable.interfaces.entity.TbPayableEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */

public interface PayableEntryJpaRepository extends JpaRepository<TbPayableEntry, Long> {
}
