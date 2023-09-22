package med.voll.api.domain.direccion;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosDireccion(
        @NotBlank
        String calle,
        @NotBlank
        String distrito,
        @NotBlank
        String ciudad,
        @NotBlank
        String numero,
        @NotBlank
        String complemento,
        @NotBlank
        @Pattern(regexp = "\\d{5}")

        String codigoPostal,
        @NotBlank
        String provincia,
        @NotBlank
        String urbanizacion
) {}
