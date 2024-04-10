package co.edu.uniquindio.unilocal.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document("usuario")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario extends Cuenta implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String nickname;
    private String urlFotoPerfil;
    private String direccion;

    private List<String> negociosFavoritos;

    @Builder
    public Usuario(String nombre, String correo, String contrasenia, EstadoRegistro estadoRegistro, Ciudad ciudad) {
        super(nombre, correo, contrasenia, estadoRegistro, ciudad);
    }

}
