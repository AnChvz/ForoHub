package com.aluracursosandreachvz.forohub.dto.topic;

import com.aluracursosandreachvz.forohub.dto.UserData;

public record TopicDataResponse(
        Long id,
        UserData user,
        String curso,
        String titulo,
        String mensaje
) {
}