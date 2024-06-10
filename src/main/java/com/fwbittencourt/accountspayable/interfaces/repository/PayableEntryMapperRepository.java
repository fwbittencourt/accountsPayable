package com.fwbittencourt.accountspayable.interfaces.repository;

import com.fwbittencourt.accountspayable.domain.model.PayableEntry;
import com.fwbittencourt.accountspayable.interfaces.entity.TbPayableEntry;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Mapper(componentModel = "spring")
public interface PayableEntryMapperRepository {
    TbPayableEntry toTbAccount(PayableEntry payableEntry);
    PayableEntry toAccount(TbPayableEntry account);
    List<PayableEntry> toAccountList(List<TbPayableEntry> account);
}
