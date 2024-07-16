package com.aluracursosandreachvz.forohub.services;

import com.aluracursosandreachvz.forohub.dto.topic.TopicData;
import com.aluracursosandreachvz.forohub.infra.exceptions.IntegrityValidation;
import com.aluracursosandreachvz.forohub.models.Topic;
import com.aluracursosandreachvz.forohub.repositories.TopicRepository;
import com.aluracursosandreachvz.forohub.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


    @Service
    public class TopicService
    {
        @Autowired
        UserRepository userRepository;

        @Autowired
        TopicRepository topicRepository;

        // Guarda un topico
        public TopicData publish(
                @RequestBody
                @Valid
                TopicData topicData
        )
        {
            if(!userRepository.findById(topicData.usuario()).isPresent())
            {
                throw new IntegrityValidation("Este usuario no existe");
            }

            var user = userRepository.findById(topicData.usuario()).get();
            Topic topic;
            topic = new Topic(
                    null,
                    user,
                    topicData.curso(),
                    topicData.titulo(),
                    topicData.mensaje(),
                    topicData.fecha(),
                    true
            );

            topicRepository.save(topic);
            return new TopicData(topic);
        }

    }

