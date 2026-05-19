package br.edu.atitus.currencyservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BCBCotacaoDTO(
        @JsonProperty("cotacaoCompra") Double cotacaoCompra,
        @JsonProperty("cotacaoVenda") Double cotacaoVenda,
        @JsonProperty("dataHoraCotacao") String dataHoraCotacao) {
}