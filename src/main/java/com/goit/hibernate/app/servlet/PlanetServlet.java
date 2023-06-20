package com.goit.hibernate.app.servlet;

import com.goit.hibernate.app.configuration.Environment;
import com.goit.hibernate.app.configuration.hibernate.Datasource;
import com.goit.hibernate.app.dto.PlanetDto;
import com.goit.hibernate.app.entity.Planet;
import com.goit.hibernate.app.mapper.PlanetDtoMapper;
import com.goit.hibernate.app.mapper.PlanetEntityMapper;
import com.goit.hibernate.app.repository.PlanetEntityRepository;
import com.goit.hibernate.app.servlet.exception.HibernateAppBadRequestException;
import com.goit.hibernate.app.servlet.exception.HibernateAppNotFoundException;
import com.goit.hibernate.app.servlet.exception.handler.ServletExceptionHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import static com.goit.hibernate.app.servlet.ServletUtils.*;
import static com.goit.hibernate.app.util.Constants.APP_ENV;
import static java.util.Objects.isNull;

public class PlanetServlet extends HttpServlet {

    private PlanetEntityRepository planetEntityRepository;
    private PlanetDtoMapper dtoMapper;
    private PlanetEntityMapper entityMapper;
    private Gson gson;

    @Override
    public void init(ServletConfig config) {
        gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        Environment environment = (Environment) config.getServletContext().getAttribute(APP_ENV);
        planetEntityRepository = new PlanetEntityRepository(new Datasource(environment));
        dtoMapper = PlanetDtoMapper.instance();
        entityMapper = PlanetEntityMapper.instance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletExceptionHandler.builder()
                .servletResponse(response)
                .action(() -> {
                    List<PlanetDto> planetDtos = resolveNumericPathVariableString(request.getRequestURI())
                            .map(id -> {
                                Planet entity = planetEntityRepository.findById(id);
                                validateIsPlanetExists(id, entity);
                                return List.of(entityMapper.map(entity));
                            }).orElse(entityMapper.map(planetEntityRepository.findAll()));
                    sendJsonResponse(response, planetDtos);
                })
                .build()
                .doAction();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ServletExceptionHandler.builder()
                .servletResponse(response)
                .action(() -> {
                    String json = new String(request.getInputStream().readAllBytes());
                    PlanetDto planetDto = gson.fromJson(json, PlanetDto.class);
                    Planet entity = dtoMapper.map(planetDto);
                    Planet saved = planetEntityRepository.save(entity);
                    PlanetDto mapped = entityMapper.map(saved);
                    sendJsonResponse(response, mapped);
                })
                .build()
                .doAction();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        ServletExceptionHandler.builder()
                .servletResponse(response)
                .action(() -> {
                    String json = new String(request.getInputStream().readAllBytes());
                    PlanetDto planetDto = gson.fromJson(json, PlanetDto.class);
                    validatePlanet(planetDto);
                    Planet entity = dtoMapper.map(planetDto);
                    Planet saved = planetEntityRepository.save(entity);
                    PlanetDto mapped = entityMapper.map(saved);
                    sendJsonResponse(response, mapped);
                })
                .build()
                .doAction();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        ServletExceptionHandler.builder()
                .servletResponse(response)
                .action(() -> {
                    resolveNumericPathVariable(request.getRequestURI())
                            .map(id -> planetEntityRepository.deleteById(id.toString()));
                    response.setStatus(HttpCode.OK);
                })
                .build()
                .doAction();
    }

    private static void validatePlanet(PlanetDto planetDto) {
        if (isNull(planetDto.getId())) {
            throw new HibernateAppBadRequestException("Planet id is required");
        }
    }

    private static void validateIsPlanetExists(String id, Planet entity) {
        if (isNull(entity)) {
            throw new HibernateAppNotFoundException("Planet with id:" + id + " not found");
        }
    }
}
