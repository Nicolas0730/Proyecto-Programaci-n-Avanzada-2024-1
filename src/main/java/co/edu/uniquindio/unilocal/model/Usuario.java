package co.edu.uniquindio.unilocal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("Usuario")
public class Usuario implements Serializable {

    @Id
    private Integer id;
    private String nickname;
    private String urlFotoPerfil;
    private String direccion;



}
