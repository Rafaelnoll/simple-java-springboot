package br.edu.atitus.currencyservice.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.currencyservice.clients.BCBClient;
import br.edu.atitus.currencyservice.dtos.BCBCotacaoDTO;
import br.edu.atitus.currencyservice.dtos.BCBCotacaoResponseDTO;
import br.edu.atitus.currencyservice.dtos.CurrencyDTO;
import br.edu.atitus.currencyservice.entities.CurrencyEntity;
import br.edu.atitus.currencyservice.repositories.CurrencyRepository;

@RestController
@RequestMapping("currency")
public class CurrencyController {

    private static final String DATA_COTACAO = "'05-16-2025'";

    private final CurrencyRepository repository;
    private final BCBClient bcbClient;

    @Value("${server.port}")
    private String port;

    public CurrencyController(CurrencyRepository repository, BCBClient bcbClient) {
        this.repository = repository;
        this.bcbClient = bcbClient;
    }

    @GetMapping("/convert")
    @Cacheable(value = "cotacoes", key = "#source + '-' + #target")
    public ResponseEntity<CurrencyDTO> getConvert(@RequestParam String source,
            @RequestParam String target) throws Exception {

        String sourceFinal = source.toUpperCase();
        String targetFinal = target.toUpperCase();

        String environment = "Currency Service running on port " + port;

        try {
            BCBCotacaoResponseDTO response = bcbClient.getCotacaoMoedaDia(
                    "'" + sourceFinal + "'",
                    DATA_COTACAO,
                    "json");

            if (response != null && response.value() != null && !response.value().isEmpty()) {
                BCBCotacaoDTO cotacao = response.value().get(0);

                if (cotacao.cotacaoVenda() > 0) {
                    return ResponseEntity.ok(new CurrencyDTO(
                            null,
                            sourceFinal,
                            targetFinal,
                            cotacao.cotacaoVenda(),
                            environment));
                }
            }
        } catch (Exception ignored) {
        }

        CurrencyEntity currency = repository
                .findBySourceCurrencyAndTargetCurrency(sourceFinal, targetFinal)
                .orElseThrow(() -> new Exception("Currency not found: " + sourceFinal + " -> " + targetFinal));

        return ResponseEntity.ok(new CurrencyDTO(
                currency.getId(),
                currency.getSourceCurrency(),
                currency.getTargetCurrency(),
                currency.getConversionRate(),
                environment));
    }
}