package com.fwbittencourt.accountspayable.domain.mapper;

import com.fwbittencourt.accountspayable.domain.dto.PayableEntryDto;
import com.fwbittencourt.accountspayable.domain.model.PayableEntry;
import org.mapstruct.Mapper;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Mapper(componentModel = "spring")
public interface PayableEntryMapper {
    PayableEntry toPayableEntry(PayableEntryDto payableEntryDto);
    PayableEntryDto toPayableEntryDto(PayableEntry payableEntry);
}
