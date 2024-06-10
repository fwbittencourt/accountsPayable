package com.fwbittencourt.accountspayable.interfaces.repository;

import com.fwbittencourt.accountspayable.domain.model.PayableEntry;
import com.fwbittencourt.accountspayable.domain.repository.PayableEntryRepository;
import com.fwbittencourt.accountspayable.interfaces.entity.TbPayableEntry;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Component
public class PayableEntryRepositoryImpl implements PayableEntryRepository {

    private final PayableEntryJpaRepository payableEntryJpaRepository;
    private final PayableEntryMapperRepository payableEntryMapperRepository;

    PayableEntryRepositoryImpl(PayableEntryJpaRepository payableEntryJpaRepository,
        PayableEntryMapperRepository payableEntryMapperRepository){

        this.payableEntryJpaRepository = payableEntryJpaRepository;
        this.payableEntryMapperRepository = payableEntryMapperRepository;
    }

    @Override
    public PayableEntry save(PayableEntry payableEntry) {
        TbPayableEntry tbPayableEntry = payableEntryMapperRepository.toTbAccount(payableEntry);
        TbPayableEntry savedAccount = payableEntryJpaRepository.save(tbPayableEntry);
        return payableEntryMapperRepository.toAccount(savedAccount);
    }

    @Override
    public List<PayableEntry> findAllPayableEntries() {
        List<TbPayableEntry> tbPayableEntries = payableEntryJpaRepository.findAll();
        return payableEntryMapperRepository.toAccountList(tbPayableEntries);
    }
}