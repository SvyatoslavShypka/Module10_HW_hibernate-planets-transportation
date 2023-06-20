package com.goit.hibernate.app.mapper;

import com.goit.hibernate.app.dto.ClientDto;
import com.goit.hibernate.app.entity.Client;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(staticName = "instance")
public class ClientEntityMapper implements Mapper<Client, ClientDto> {

    @Override
    public ClientDto map(Client entity) throws RuntimeException {
        return ClientDto.of(entity.getId(), entity.getName());
    }

    @Override
    public List<ClientDto> map(List<Client> source) throws RuntimeException {
        return source.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
