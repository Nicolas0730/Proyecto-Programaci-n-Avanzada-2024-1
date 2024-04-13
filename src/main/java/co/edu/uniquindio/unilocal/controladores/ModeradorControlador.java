package co.edu.uniquindio.unilocal.controladores;

import co.edu.uniquindio.unilocal.dto.MensajeDTO;
import co.edu.uniquindio.unilocal.dto.moderadorDTO.ItemModeradorDTO;
import co.edu.uniquindio.unilocal.servicios.interfaces.ModeradorServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/moderador")
@RequiredArgsConstructor
public class ModeradorControlador {

    private final ModeradorServicio moderadorServicio;

    @GetMapping("/obtener-datos-moderador/{idModerador}")
    public ResponseEntity<MensajeDTO<ItemModeradorDTO>> obtenerDatosModerador(String idModerador) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,moderadorServicio.obtenerDatosModerador(idModerador)));
    }
}
