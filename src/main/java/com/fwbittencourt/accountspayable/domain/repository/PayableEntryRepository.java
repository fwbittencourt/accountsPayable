package com.fwbittencourt.accountspayable.domain.repository;

import com.fwbittencourt.accountspayable.domain.model.PayableEntry;
import com.fwbittencourt.accountspayable.interfaces.entity.TbPayableEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */

public interface PayableEntryRepository {
    PayableEntry save(PayableEntry payableEntry);
    List<PayableEntry> findAllPayableEntries();
    Optional<PayableEntry> findById(UUID id);
    MyPage<PayableEntry> findAllByFilters(Specification<TbPayableEntry> payableEntrySpecification, Pageable pageable);
    long saveAll(List<PayableEntry> payableEntryDtoToSave);
}