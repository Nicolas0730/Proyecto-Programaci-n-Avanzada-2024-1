package co.edu.uniquindio.unilocal.controladores;

import co.edu.uniquindio.unilocal.dto.MensajeDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.RegistroComentarioDTO;
import co.edu.uniquindio.unilocal.dto.comentarioDTO.ResponderComentarioDTO;
import co.edu.uniquindio.unilocal.servicios.interfaces.ComentarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comentario")
@RequiredArgsConstructor
public class ComentarioControlador {

    private final ComentarioServicio comentarioServicio;

    @PostMapping("/registrar-comentario")
    public ResponseEntity<MensajeDTO<String>> registrarComentario(@Valid @RequestBody RegistroComentarioDTO registroComentarioDTO) throws Exception{
        comentarioServicio.registrarComentario(registroComentarioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Comentario registrado correctamente."));
    }

    @PutMapping("/responder-comentario")
    public ResponseEntity<MensajeDTO<String>> responderComentario(@Valid @RequestBody ResponderComentarioDTO responderComentarioDTO) throws Exception{
        comentarioServicio.responderComentario(responderComentarioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Comentario respondido correctamente."));
    }

}
