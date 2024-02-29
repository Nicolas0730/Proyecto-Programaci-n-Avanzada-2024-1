package co.edu.uniquindio.unilocal.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class HistorialNegocio {

    private int id;
    private String descripcion;
    private LocalDateTime fecha;
    private EstadoRegistro estadoNegocio;
    private Moderador idModerador;
}
