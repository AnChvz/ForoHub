package com.aluracursosandreachvz.forohub.infra.exceptions;

public class IntegrityValidation extends RuntimeException {
    public IntegrityValidation(String message) {
        super(message);
    }
}