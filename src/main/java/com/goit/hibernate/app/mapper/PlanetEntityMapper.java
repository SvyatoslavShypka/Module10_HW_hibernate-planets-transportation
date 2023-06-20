package com.goit.hibernate.app.mapper;

import com.goit.hibernate.app.dto.PlanetDto;
import com.goit.hibernate.app.entity.Planet;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(staticName = "instance")
public class PlanetEntityMapper implements Mapper<Planet, PlanetDto> {

    @Override
    public PlanetDto map(Planet entity) throws RuntimeException {
        return PlanetDto.of(entity.getId(), entity.getName());
    }

    @Override
    public List<PlanetDto> map(List<Planet> source) throws RuntimeException {
        return source.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
