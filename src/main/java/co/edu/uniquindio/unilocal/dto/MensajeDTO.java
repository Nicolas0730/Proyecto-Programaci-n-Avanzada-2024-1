package co.edu.uniquindio.unilocal.dto;

public record MensajeDTO<T>(boolean error,
                         T respuesta) {
}

