package com.fwbittencourt.accountspayable.domain.repository;

import com.fwbittencourt.accountspayable.domain.model.PayableEntry;

import java.util.List;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */

public interface PayableEntryRepository {
    PayableEntry save(PayableEntry payableEntry);
    List<PayableEntry> findAllPayableEntries();
}