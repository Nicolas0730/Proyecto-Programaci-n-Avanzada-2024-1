package co.edu.uniquindio.unilocal.controladores;

import co.edu.uniquindio.unilocal.dto.LoginDTO;
import co.edu.uniquindio.unilocal.dto.MensajeDTO;
import co.edu.uniquindio.unilocal.dto.TokenDTO;
import co.edu.uniquindio.unilocal.dto.usuarioDTO.RegistroUsuarioDTO;
import co.edu.uniquindio.unilocal.servicios.interfaces.AutenticacionServicio;
import co.edu.uniquindio.unilocal.servicios.interfaces.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionControlador {

    private final AutenticacionServicio autenticacionServicio;
    private final UsuarioServicio usuarioServicio;
    @PostMapping("/login-usuario")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesionUsuario(@Valid @RequestBody
                                                                     LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = autenticacionServicio.iniciarSesionUsuario(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }
    @PostMapping("/registrar-usuario")
    public ResponseEntity<MensajeDTO<String>> registrarUsuario(@Valid @RequestBody RegistroUsuarioDTO registroClienteDTO) throws Exception{

        usuarioServicio.registrarUsuario(registroClienteDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Usuario registrado correctamente"));
    }

    @PostMapping("/login-administrador")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesionAdministrador(@Valid @RequestBody
                                                                     LoginDTO loginDTO) throws Exception {
        TokenDTO tokenDTO = autenticacionServicio.iniciarSesionModerador(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }
}
