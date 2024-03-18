package co.edu.uniquindio.unilocal.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("Usuario")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Usuario extends Cuenta implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String nickname;
    private String urlFotoPerfil;
    private String direccion;

    @Builder
    public Usuario(String nombre, String correo, String contrasenia, EstadoCuenta estadoCuenta, Ciudad ciudad, String id, String nickname, String urlFotoPerfil, String direccion) {
        super(nombre, correo, contrasenia, estadoCuenta, ciudad);
        this.id = id;
        this.nickname = nickname;
        this.urlFotoPerfil = urlFotoPerfil;
        this.direccion = direccion;
    }
}
