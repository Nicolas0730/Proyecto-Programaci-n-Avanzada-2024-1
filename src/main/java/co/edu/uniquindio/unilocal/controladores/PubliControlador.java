package co.edu.uniquindio.unilocal.controladores;

import co.edu.uniquindio.unilocal.dto.MensajeDTO;
import co.edu.uniquindio.unilocal.dto.moderadorDTO.ItemModeradorDTO;
import co.edu.uniquindio.unilocal.model.Ciudad;
import co.edu.uniquindio.unilocal.servicios.interfaces.NegocioServicio;
import co.edu.uniquindio.unilocal.servicios.interfaces.PublicAccessServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PubliControlador {

    private final PublicAccessServicio publicAccessServicio;

    @GetMapping("/listar-ciudades")
    public ResponseEntity<MensajeDTO<List<String> >> listarCiudades() throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,publicAccessServicio.listarCiudades()));
    }

    @GetMapping("/listar-tipo-de-negocios")
    public ResponseEntity<MensajeDTO<List<String> >> listarTipoNegocio() throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false,publicAccessServicio.listarTiposNegocio()));
    }



}
