package com.evcsms.ocppmockserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Validated
public class CreateChargePointDTO {

    @NotBlank
    private String ocppId;

    @NotNull
    private Boolean disabled;

    @NotNull
    private CreateChargeHardwareSpecDTO hardwareSpec;

    @NotEmpty
    private List<@NotNull CreateConnectorDTO> connectors;

}
