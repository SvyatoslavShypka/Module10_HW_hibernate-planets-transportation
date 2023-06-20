package com.goit.hibernate.app.mapper;

import com.goit.hibernate.app.dto.ClientDto;
import com.goit.hibernate.app.entity.Client;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(staticName = "instance")
public final class ClientDtoMapper implements Mapper<ClientDto, Client> {

    @Override
    public Client map(ClientDto source) {
        Client target = new Client();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }

    @Override
    public List<Client> map(List<ClientDto> source) throws RuntimeException {
        return source.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
