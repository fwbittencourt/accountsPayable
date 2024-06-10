package com.fwbittencourt.accountspayable.domain.service.imp;

import com.fwbittencourt.accountspayable.domain.dto.PayableEntryDto;
import com.fwbittencourt.accountspayable.domain.mapper.PayableEntryMapper;
import com.fwbittencourt.accountspayable.domain.model.PayableEntry;
import com.fwbittencourt.accountspayable.domain.repository.PayableEntryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Service
@AllArgsConstructor
public class PayableEntryService {

    private PayableEntryRepository payableEntryRepository;
    private PayableEntryMapper payableEntryMapper;

    public PayableEntryDto create(PayableEntryDto payableEntryDto) {
        PayableEntry payableEntry = payableEntryMapper.toPayableEntry(payableEntryDto);
        PayableEntry savedPayableEntry = payableEntryRepository.save(payableEntry);
        return payableEntryMapper.toPayableEntryDtoList(savedPayableEntry);
    }

    public List<PayableEntryDto> listAllAccounts() {
        List<PayableEntry> payableEntries = payableEntryRepository.findAllPayableEntries();
        return payableEntryMapper.toPayableEntryDtoList(payableEntries);
    }
}
