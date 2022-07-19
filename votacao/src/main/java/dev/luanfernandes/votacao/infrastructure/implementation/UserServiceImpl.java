package dev.luanfernandes.votacao.infrastructure.implementation;

import dev.luanfernandes.votacao.api.exceptions.ExternalApiException;
import dev.luanfernandes.votacao.domain.dto.ApiDto;
import dev.luanfernandes.votacao.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final RestTemplate restTemplate;

    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean consultaCpf(String cpf) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(headers);
        ApiDto response;
        try {
            response = restTemplate.exchange(String.format("https://user-info.herokuapp.com/users/%s", cpf),
                    HttpMethod.GET, request, ApiDto.class).getBody();
            if (response == null){
                throw new ExternalApiException("CPF inválido");
            }
        } catch (RestClientException | ExternalApiException e) {
            throw new ExternalApiException("CPF inválido");
        }
        return "ABLE_TO_VOTE".equals(response.getStatus());
    }
}