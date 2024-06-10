package com.fwbittencourt.accountspayable.infra.security.repository;

import com.fwbittencourt.accountspayable.infra.security.domain.entity.TbUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author <Filipe Bittencourt> on 09/06/24
 */
public interface UserRepository extends CrudRepository<TbUser, Long> {
    Optional<TbUser> findByUsername(String username);
}