package edu.ec.com.central.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "agricultores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agricultor {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID agricultorId;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String finca;

    @Column(nullable = false, length = 100)
    private String ubicacion;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    private OffsetDateTime fechaRegistro = OffsetDateTime.now();
}
