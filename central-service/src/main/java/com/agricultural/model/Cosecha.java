package edu.ec.com.central.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cosechas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cosecha {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID cosechaId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "agricultor_id")
    private Agricultor agricultor;

    @Column(nullable = false, length = 50)
    private String producto;

    @Column(nullable = false)
    private double toneladas;

    @Column(nullable = false, length = 20)
    private String estado = "REGISTRADA";

    private OffsetDateTime creadoEn = OffsetDateTime.now();

    @Column(columnDefinition = "uuid")
    private UUID facturaId;
}
