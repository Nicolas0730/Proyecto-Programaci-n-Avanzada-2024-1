package co.edu.uniquindio.unilocal.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document("Moderador")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Moderador extends Cuenta implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @Field("idModerador")
    private String idModerador;


    @Builder
    public Moderador(String nombre, String correo, String contrasenia, EstadoRegistro estadoRegistro, Ciudad ciudad) {
        super(nombre, correo, contrasenia, estadoRegistro, ciudad);
    }
}
