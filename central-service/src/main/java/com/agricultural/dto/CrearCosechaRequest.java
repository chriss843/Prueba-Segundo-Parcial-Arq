package edu.ec.com.central.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

public record CrearCosechaRequest(
        @NotNull UUID agricultor_id,
        @NotBlank String producto,
        @Positive double toneladas,
        String ubicacion
) {}
