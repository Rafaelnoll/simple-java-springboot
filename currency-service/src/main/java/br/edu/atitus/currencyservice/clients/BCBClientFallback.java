package br.edu.atitus.currencyservice.clients;

import br.edu.atitus.currencyservice.dtos.BCBCotacaoDTO;
import br.edu.atitus.currencyservice.dtos.BCBCotacaoResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BCBClientFallback implements BCBClient {

    // Valor de fallback fixo — indica indisponibilidade da API BCB
    private static final double FALLBACK_RATE = -1.0;

    @Override
    public BCBCotacaoResponseDTO getCotacaoMoedaDia(String moeda, String dataCotacao, String format) {
        BCBCotacaoDTO fallbackDTO = new BCBCotacaoDTO(
                FALLBACK_RATE,
                FALLBACK_RATE,
                "unavailable");
        return new BCBCotacaoResponseDTO(List.of(fallbackDTO));
    }
}