package co.edu.uniquindio.unilocal.model;

import java.time.LocalDateTime;

import co.edu.uniquindio.unilocal.model.documentos.Moderador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class HistorialNegocio {

    private String id;
    private String descripcion;
    private LocalDateTime fecha;
    private EstadoRegistro estadoNegocio;
    private String idModerador;
}
