<<<<<<< HEAD:src/main/java/co/edu/uniquindio/unilocal/model/documentos/Moderador.java
package co.edu.uniquindio.unilocal.model.documentos;

import co.edu.uniquindio.unilocal.model.Cuenta;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("Moderador")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Moderador extends Cuenta implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private Integer idModerador;

}
=======
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
    public Moderador(String nombre, String correo, String contrasenia, EstadoCuenta estadoCuenta, Ciudad ciudad) {
        super(nombre, correo, contrasenia, estadoCuenta, ciudad);
    }
}
>>>>>>> ramaNicolas:src/main/java/co/edu/uniquindio/unilocal/model/Moderador.java
