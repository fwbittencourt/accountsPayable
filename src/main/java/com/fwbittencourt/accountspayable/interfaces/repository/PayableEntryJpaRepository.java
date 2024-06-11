package com.fwbittencourt.accountspayable.interfaces.repository;

import com.fwbittencourt.accountspayable.interfaces.entity.TbPayableEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */

public interface PayableEntryJpaRepository extends JpaRepository<TbPayableEntry, UUID>, JpaSpecificationExecutor<TbPayableEntry> {
}
