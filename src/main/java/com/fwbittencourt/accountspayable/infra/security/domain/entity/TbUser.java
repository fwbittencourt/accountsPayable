package com.fwbittencourt.accountspayable.infra.security.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * @author <Filipe Bittencourt> on 09/06/24
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuarios")
public class TbUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "nome_usuario")
    private String username;
    @Column(name = "senha")
    private String password;
}