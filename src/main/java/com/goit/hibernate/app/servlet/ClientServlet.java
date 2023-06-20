package com.goit.hibernate.app.servlet;

import com.goit.hibernate.app.configuration.Environment;
import com.goit.hibernate.app.configuration.hibernate.Datasource;
import com.goit.hibernate.app.dto.ClientDto;
import com.goit.hibernate.app.entity.Client;
import com.goit.hibernate.app.mapper.ClientDtoMapper;
import com.goit.hibernate.app.mapper.ClientEntityMapper;
import com.goit.hibernate.app.repository.ClientEntityRepository;
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

import static com.goit.hibernate.app.servlet.ServletUtils.resolveNumericPathVariable;
import static com.goit.hibernate.app.servlet.ServletUtils.sendJsonResponse;
import static com.goit.hibernate.app.util.Constants.APP_ENV;
import static java.util.Objects.isNull;

public class ClientServlet extends HttpServlet {

    private ClientEntityRepository clientEntityRepository;
    private ClientDtoMapper dtoMapper;
    private ClientEntityMapper entityMapper;
    private Gson gson;

    @Override
    public void init(ServletConfig config) {
        gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        Environment environment = (Environment) config.getServletContext().getAttribute(APP_ENV);
        clientEntityRepository = new ClientEntityRepository(new Datasource(environment));
        dtoMapper = ClientDtoMapper.instance();
        entityMapper = ClientEntityMapper.instance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletExceptionHandler.builder()
                .servletResponse(response)
                .action(() -> {
                    List<ClientDto> clientDtos = resolveNumericPathVariable(request.getRequestURI())
                            .map(id -> {
                                Client entity = clientEntityRepository.findById(id);
                                validateIsClientExists(id, entity);
                                return List.of(entityMapper.map(entity));
                            }).orElse(entityMapper.map(clientEntityRepository.findAll()));
                    sendJsonResponse(response, clientDtos);
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
                    ClientDto clientDto = gson.fromJson(json, ClientDto.class);
                    Client entity = dtoMapper.map(clientDto);
                    Client saved = clientEntityRepository.save(entity);
                    ClientDto mapped = entityMapper.map(saved);
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
                    ClientDto clientDto = gson.fromJson(json, ClientDto.class);
                    validateClient(clientDto);
                    Client entity = dtoMapper.map(clientDto);
                    Client saved = clientEntityRepository.save(entity);
                    ClientDto mapped = entityMapper.map(saved);
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
                            .map(id -> clientEntityRepository.deleteById(id));
                    response.setStatus(HttpCode.OK);
                })
                .build()
                .doAction();
    }

    private static void validateClient(ClientDto clientDto) {
        if (isNull(clientDto.getId())) {
            throw new HibernateAppBadRequestException("Client id is required");
        }
    }

    private static void validateIsClientExists(Long id, Client entity) {
        if (isNull(entity)) {
            throw new HibernateAppNotFoundException("Client with id:" + id + " not found");
        }
    }
}
