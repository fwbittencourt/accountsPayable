package com.fwbittencourt.accountspayable.domain.repository;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Getter
@Setter
public class SortDomain {
    String direction;
    String property;
    boolean ignoreCase;
    boolean ascending;
    String nullHandling;

    public SortDomain(){
    }
}
