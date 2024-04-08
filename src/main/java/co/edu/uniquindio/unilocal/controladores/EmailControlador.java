package co.edu.uniquindio.unilocal.controladores;

import co.edu.uniquindio.unilocal.dto.EmailDTO;
import co.edu.uniquindio.unilocal.dto.MensajeDTO;
import co.edu.uniquindio.unilocal.servicios.interfaces.EmailServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailControlador {

    private final EmailServicio emailServicio;
    @PostMapping("/eviar-correo")
    public ResponseEntity<MensajeDTO<String>> enviarCorreo(EmailDTO emailDTO) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Correo enviado correctamente."));
    }
}
