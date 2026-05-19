package br.edu.atitus.currencyservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record BCBCotacaoResponseDTO(
        @JsonProperty("value") List<BCBCotacaoDTO> value) {
}