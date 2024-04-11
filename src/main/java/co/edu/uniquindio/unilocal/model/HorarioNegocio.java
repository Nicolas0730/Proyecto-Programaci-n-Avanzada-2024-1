package co.edu.uniquindio.unilocal.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class HorarioNegocio {
    private LocalDateTime horaApertura;
    private LocalDateTime horaCierre;
    private String dia;

}
