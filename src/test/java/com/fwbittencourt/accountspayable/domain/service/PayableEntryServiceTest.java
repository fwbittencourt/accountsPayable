package com.fwbittencourt.accountspayable.domain.service;

import com.fwbittencourt.accountspayable.domain.model.PayableEntry;
import com.fwbittencourt.accountspayable.domain.repository.PayableEntryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayableEntryServiceTest {
    @Mock
    PayableEntryRepository payableEntryRepository;

    @InjectMocks
    PayableEntryService payableEntryService;

    @Captor
    ArgumentCaptor<List<PayableEntry>> payableEntriesCaptor;

    @Test
    void testLoadCsvFile() {
        String fileContent = """
            Transport bill,2026-07-15,,OPEN,11100.5
            Eletrict bill,2026-08-15,,OPEN,1100.5
            Eletrict bill,2024-06-09,,OVERDUE,1100.5
            Credit card bill,2024-06-06,,OVERDUE,10000.5
            """;

        payableEntryService.loadCsvFile(fileContent);

        verify(payableEntryRepository).saveAll(payableEntriesCaptor.capture());

        List<PayableEntry> result = payableEntriesCaptor.getValue();

        Assertions.assertEquals(4L, result.size());
        Assertions.assertEquals("Transport bill", result.get(0).getDescription());
        Assertions.assertEquals("2026-07-15", result.get(0).getDueDate().toString());
        Assertions.assertNull(result.get(0).getPaymentDate());
        Assertions.assertEquals("OPEN", result.get(0).getStatus().name());
        Assertions.assertEquals("11100.5", result.get(0).getValue().toString());
    }

}
