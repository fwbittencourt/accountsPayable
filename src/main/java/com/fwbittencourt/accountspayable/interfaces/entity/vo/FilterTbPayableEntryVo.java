package com.fwbittencourt.accountspayable.interfaces.entity.vo;

import com.fwbittencourt.accountspayable.domain.enums.EnStatus;
import com.fwbittencourt.accountspayable.interfaces.entity.TbPayableEntry;
import com.fwbittencourt.accountspayable.interfaces.specification.TbPayableEntrySpecs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(name = "Filtro contas a pagar", description = "Filtro utilizado para requisição de contas a pagar")
public class FilterTbPayableEntryVo {

    private UUID id;
    private LocalDate initialDueDate;
    private LocalDate finalDueDate;
    private LocalDate initialPaymentDate;
    private LocalDate finalPaymentDate;
    private BigDecimal value;
    private String description;
    private List<EnStatus> status;
    private boolean interno;

    public static FilterTbPayableEntryVo mounterFilter(final LocalDate paymentDate, final LocalDate dueDate,
        final List<EnStatus> status, final String description, final boolean interno) {

        return FilterTbPayableEntryVo.builder()
            .initialDueDate(dueDate)
            .finalDueDate(paymentDate)
            .status(status)
            .description(description)
            .interno(interno)
            .build();
    }

    public Specification<TbPayableEntry> toSpecs() {
        final var specPeriodDueDate = getPeriodByDueDate();
        final var specPeriodPaymentDate = getPeriodByPaymentDate();
        final var specStatus = getSpecStatus();
        final var specDescription = getDescriptionSpec();

        return specPeriodDueDate
            .and(specPeriodPaymentDate)
            .and(specStatus)
            .and(specDescription);
    }

    private Specification<TbPayableEntry> getPeriodByDueDate() {
        if (initialDueDate != null && finalDueDate != null) {
            return TbPayableEntrySpecs.dateCreateBetween(initialDueDate, finalDueDate);
        }
        if (initialDueDate != null) {
            return TbPayableEntrySpecs.dateCreateGreaterOrEqual(initialDueDate);
        }
        if (finalDueDate != null) {
            return TbPayableEntrySpecs.dataCreateMinorOrEqual(finalDueDate);
        }
        return Specification.where(null);
    }

    private Specification<TbPayableEntry> getPeriodByPaymentDate() {
        if (initialPaymentDate != null && finalPaymentDate != null) {
            return TbPayableEntrySpecs.dateCreateBetween(initialPaymentDate, finalPaymentDate);
        }
        if (initialPaymentDate != null) {
            return TbPayableEntrySpecs.dateCreateGreaterOrEqual(initialPaymentDate);
        }
        if (finalPaymentDate != null) {
            return TbPayableEntrySpecs.dataCreateMinorOrEqual(finalPaymentDate);
        }
        return Specification.where(null);
    }

    private Specification<TbPayableEntry> getSpecStatus() {
        if (CollectionUtils.isNotEmpty(status)) {
            return TbPayableEntrySpecs.statusIn(status);
        }
        return Specification.where(null);
    }

    private Specification<TbPayableEntry> getDescriptionSpec() {
        if (StringUtils.isBlank(description)) {
            return Specification.where(null);
        }
        return TbPayableEntrySpecs.description(description.trim());
    }
}