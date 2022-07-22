package dev.luanfernandes.votacao.infrastructure.implementation;

import dev.luanfernandes.votacao.api.exceptions.ExternalApiException;
import dev.luanfernandes.votacao.api.exceptions.ValidationException;
import dev.luanfernandes.votacao.domain.dto.UserInfoDto;
import dev.luanfernandes.votacao.domain.service.UserInfoService;
import dev.luanfernandes.votacao.domain.utils.DocumentValidator;
import dev.luanfernandes.votacao.domain.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    private final RestTemplate restTemplate;

    public UserInfoServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void enableToVote(String cpf) {
        if (!DocumentValidator.isValidCpf(StringUtil.removeMask(cpf))){
            throw new ValidationException("Cpf inválido");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(headers);
        UserInfoDto response = restTemplate.exchange(
                String.format("https://user-info.herokuapp.com/users/%s", cpf),
                HttpMethod.GET, request, UserInfoDto.class).getBody();
        if (!"ABLE_TO_VOTE".equals(response != null ? response.getStatus() : null)){
            throw new ExternalApiException("UNABLE_TO_VOTE");
        }
    }
}