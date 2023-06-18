package com.goit.hibernate.app.mapper;

import com.goit.hibernate.app.dto.ClientDto;
import com.goit.hibernate.app.dto.PlanetDto;
import com.goit.hibernate.app.entity.Client;
import com.goit.hibernate.app.entity.Planet;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(staticName = "instance")
public final class PlanetDtoMapper implements Mapper<PlanetDto, Planet> {

    @Override
    public Planet map(PlanetDto source) {
        Planet target = new Planet();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }

    @Override
    public List<Planet> map(List<PlanetDto> source) throws RuntimeException {
        return source.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
