package com.evcsms.chargestationserver.dto;

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

    @NotEmpty
    private List<@NotNull CreateConnectorDTO> connectors;

}
