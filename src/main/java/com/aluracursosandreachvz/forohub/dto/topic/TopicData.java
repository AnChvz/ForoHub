package com.aluracursosandreachvz.forohub.dto.topic;

import com.aluracursosandreachvz.forohub.models.Topic;

import java.time.LocalDateTime;

public record TopicData(
        Long id,
        Long usuario,
        String curso,
        String titulo,
        String mensaje,
        LocalDateTime fecha
)
{

    public TopicData(Topic topic)
    {
        this(
                topic.getId(),
                topic.getUsuario().getId(),
                topic.getCurso(),
                topic.getTitulo(),
                topic.getMensaje(),
                topic.getFechaCreacion()
        );
    }
}