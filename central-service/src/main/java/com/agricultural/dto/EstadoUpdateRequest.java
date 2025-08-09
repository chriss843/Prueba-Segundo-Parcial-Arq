package edu.ec.com.central.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record EstadoUpdateRequest(
        @NotBlank String estado,
        UUID factura_id
) {}
