package co.edu.uniquindio.unilocal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("Moderador")
public class Moderador extends Cuenta implements Serializable {

    @Id
    private Integer idModerador;

}
