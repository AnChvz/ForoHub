package com.aluracursosandreachvz.forohub.controllers;

import com.aluracursosandreachvz.forohub.dto.TopicDataUpdate;
import com.aluracursosandreachvz.forohub.dto.UserData;
import com.aluracursosandreachvz.forohub.dto.topic.TopicData;
import com.aluracursosandreachvz.forohub.dto.topic.TopicDataResponse;
import com.aluracursosandreachvz.forohub.dto.topic.TopicListData;
import com.aluracursosandreachvz.forohub.models.Topic;
import com.aluracursosandreachvz.forohub.repositories.TopicRepository;
import com.aluracursosandreachvz.forohub.services.TopicService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

 @RestController
@RequestMapping("topics")
public class TopicController
{

    @Autowired
    private TopicService service;

    @Autowired
    TopicRepository repository;

    // Publica un nuevo topico
    @PostMapping
    @Transactional
    public ResponseEntity setTopic(
            @RequestBody
            @Valid
            TopicData topic
    )
    {
        var response = service.publish(topic);

        Object responseBody = new Object() {
            public final int httpStatus = HttpStatus.OK.value();
            public final Object topic = response;
        };

        return ResponseEntity.ok(responseBody);
    }

    // Trae todos los topicos
    @GetMapping
    public Page<TopicListData> getTopics(
            @PageableDefault(size = 5, sort = "fechaCreacion", direction = Sort.Direction.DESC)
            Pageable pageable
    )
    {
        return repository.findByStatusTrue(pageable)
                .map(TopicListData::new);
    }

    // Trae un topico por ID
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> getATopic(
            @PathVariable
            Long id
    )
    {
        Topic topic = repository.getReferenceById(id);
        var topicData = new TopicData(
                topic.getId(),
                topic.getUsuario().getId(),
                topic.getCurso(),
                topic.getTitulo(),
                topic.getMensaje(),
                topic.getFechaCreacion()
        );

        Object responseBody = new Object() {
            public final int httpStatus = HttpStatus.OK.value();
            public final Object topic = topicData;
        };

        return ResponseEntity.ok(responseBody);
    }

    // Actualizar un topico
    @PutMapping()
    @Transactional
    public ResponseEntity<Object> updateTopic(
            @RequestBody
            @Valid
            TopicDataUpdate topicData
    )
    {
        Topic topic = repository.getReferenceById(topicData.id());
        topic.updateTopicData(topicData);

        var response = new TopicDataResponse(
                topic.getId(),
                new UserData(
                        topic.getUsuario().getId(),
                        topic.getUsuario().getUsername()
                ),
                topic.getCurso(),
                topic.getTitulo(),
                topic.getMensaje()
        );

        Object responseBody = new Object() {
            public final int httpStatus = HttpStatus.OK.value();
            public final Object topic = response;
        };

        return ResponseEntity.ok(responseBody);
    }

    // Eliminar topico de la DB
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicDataResponse> deleteTopic(
            @PathVariable
            Long
                    id
    ){
        Topic topic = repository.getReferenceById(id);
        topic.disableTopic();
        return ResponseEntity.noContent().build();
    }
}