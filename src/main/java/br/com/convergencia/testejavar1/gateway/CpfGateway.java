package br.com.convergencia.testejavar1.gateway;

import br.com.convergencia.testejavar1.config.validation.ExceptionTypeEnum;
import br.com.convergencia.testejavar1.gateway.exception.GatewayException;
import br.com.convergencia.testejavar1.gateway.presentation.CpfPresentation;
import br.com.convergencia.testejavar1.gateway.presentation.NewCpfPresentation;
import br.com.convergencia.testejavar1.model.dto.CpfDto;
import br.com.convergencia.testejavar1.model.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


@Service
public class CpfGateway {

    private final Logger logger = LoggerFactory.getLogger(CpfGateway.class);

    @Value("${client.cpf.url}")
    private String url;

    public CpfDto getByCpf(String cpf) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + cpf))
                    .timeout(Duration.ofSeconds(6))
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(6))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (HttpStatus.OK.value() != response.statusCode()) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            CpfPresentation foundCpf = mapper.readValue(response.body(), CpfPresentation.class);

            var foundCpfValue = foundCpf.number();
            logger.info("method=getByCpf, message=cpf {} was returned from the api", foundCpfValue);

            return new CpfDto(foundCpf.number(), foundCpf.status());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new GatewayException(ExceptionTypeEnum.CPF_FIND_ERROR, e.getMessage());
        }
    }

    public CpfDto create(UserDto loginDto) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String requestBody = mapper.writeValueAsString(new NewCpfPresentation(loginDto.login()));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(6))
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(6))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            CpfPresentation savedCpf = mapper.readValue(response.body(), CpfPresentation.class);
            var savedCpfValue = savedCpf.number();
            logger.info("method=create, message=cpf {} was successfully registered from the api", savedCpfValue);

            return new CpfDto(savedCpf.number(), savedCpf.status());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new GatewayException(ExceptionTypeEnum.CPF_SAVE_ERROR, e.getMessage());
        }
    }
}
