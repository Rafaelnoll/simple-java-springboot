package br.edu.atitus.currencyservice.clients;

import br.edu.atitus.currencyservice.dtos.BCBCotacaoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "BCBClient", url = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata", fallback = BCBClientFallback.class)
public interface BCBClient {

    @GetMapping(value = "/CotacaoMoedaDia(moeda=@moeda,dataCotacao=@dataCotacao)", produces = "application/json")
    BCBCotacaoResponseDTO getCotacaoMoedaDia(
            @RequestParam("@moeda") String moeda,
            @RequestParam("@dataCotacao") String dataCotacao,
            @RequestParam("$format") String format);
}