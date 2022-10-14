package br.com.convergencia.testejavar1.service;

import br.com.convergencia.testejavar1.config.validation.ExceptionTypeEnum;
import br.com.convergencia.testejavar1.gateway.CpfGateway;
import br.com.convergencia.testejavar1.model.dto.CpfDto;
import br.com.convergencia.testejavar1.service.exception.CpfNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CpfFindService {

    private final Logger logger = LoggerFactory.getLogger(CpfFindService.class);

    private final CpfGateway gateway;

    public CpfFindService(CpfGateway gateway) {
        this.gateway = gateway;
    }

    public CpfDto getByCpf(String cpf) {
        CpfDto foundCpf = gateway.getByCpf(cpf);

        if (Objects.nonNull(foundCpf)) {
            var foundCpfValue = foundCpf.value();
            logger.info("method=getByCpf, message=cpf {} was found", foundCpfValue);
            return foundCpf;
        }

        logger.info("method=getByCpf, message=cpf {} not found", cpf);
        throw new CpfNotFoundException(ExceptionTypeEnum.CPF_NOT_FOUND);
    }
}
