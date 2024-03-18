package co.edu.uniquindio.unilocal.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Cuenta {

    private String nombre;
    private String correo;
    private String contrasenia;
    private EstadoCuenta estadoCuenta;
    private Ciudad ciudad;
}
