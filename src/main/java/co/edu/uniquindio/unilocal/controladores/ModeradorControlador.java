package co.edu.uniquindio.unilocal.controladores;

import co.edu.uniquindio.unilocal.dto.MensajeDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.ItemNegocioDTO;
import co.edu.uniquindio.unilocal.dto.NegocioDTO.RegistroRevisionDTO;
import co.edu.uniquindio.unilocal.dto.moderadorDTO.ItemModeradorDTO;
import co.edu.uniquindio.unilocal.model.EstadoNegocio;
import co.edu.uniquindio.unilocal.servicios.interfaces.ModeradorServicio;
import co.edu.uniquindio.unilocal.servicios.interfaces.NegocioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderador")
@RequiredArgsConstructor
public class ModeradorControlador {

    private final ModeradorServicio moderadorServicio;
    private final NegocioServicio negocioServicio;

    @GetMapping("/obtener-datos-moderador/{idModerador}")
    public ResponseEntity<MensajeDTO<ItemModeradorDTO>> obtenerDatosModerador(@PathVariable String idModerador) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,moderadorServicio.obtenerDatosModerador(idModerador)));
    }

    @GetMapping("/filtrar-negocios-estado/{estadoNegocio}")
    public ResponseEntity<MensajeDTO<List<ItemNegocioDTO>>> filtrarPorEstado(@PathVariable EstadoNegocio estadoNegocio) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,negocioServicio.filtrarPorEstado(estadoNegocio)));
    }

    @PutMapping("/aprobar-negocio")
    public ResponseEntity<MensajeDTO<String>> aprobarNegocio(@Valid @RequestBody RegistroRevisionDTO negocioDTO) throws Exception{
        negocioServicio.aprobarNegocio(negocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio aprobado con éxito."));
    }
    @PutMapping("/rechazar-negocio")
    public ResponseEntity<MensajeDTO<String>> rechazarNegocio(@Valid @RequestBody RegistroRevisionDTO negocioDTO) throws Exception{
        negocioServicio.rechazarNegocio(negocioDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Negocio rechazado con éxito."));
    }
}
