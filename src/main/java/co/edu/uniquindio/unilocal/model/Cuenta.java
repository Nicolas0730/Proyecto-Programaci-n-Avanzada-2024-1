package co.edu.uniquindio.unilocal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Cuenta {

    private String nombre;
    private String correo;
    private String contrasenia;
    private EstadoRegistro estadoRegistro;
    private Ciudad ciudad;
}
