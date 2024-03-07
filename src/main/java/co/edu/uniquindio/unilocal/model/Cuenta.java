package co.edu.uniquindio.unilocal.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class Cuenta {

    private String nombre;
    private String correo;
    private String contraseña;
    private EstadoCuenta estadoCuenta;
    private Ciudad ciudad;
}
