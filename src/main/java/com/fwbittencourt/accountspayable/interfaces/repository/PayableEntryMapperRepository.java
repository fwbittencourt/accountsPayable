package com.fwbittencourt.accountspayable.interfaces.repository;

import com.fwbittencourt.accountspayable.domain.model.PayableEntry;
import com.fwbittencourt.accountspayable.domain.repository.MyPage;
import com.fwbittencourt.accountspayable.interfaces.entity.TbPayableEntry;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Mapper(componentModel = "spring")
public interface PayableEntryMapperRepository {
    TbPayableEntry toTbPayableEntry(PayableEntry payableEntry);
    PayableEntry toPayableEntry(TbPayableEntry account);
    MyPage<PayableEntry> mapPageToPayableEntryPage(Page<TbPayableEntry> page);
}
