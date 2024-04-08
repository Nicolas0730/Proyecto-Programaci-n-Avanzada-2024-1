package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.dto.LoginDTO;
import co.edu.uniquindio.unilocal.dto.TokenDTO;
import co.edu.uniquindio.unilocal.model.Usuario;
import co.edu.uniquindio.unilocal.repositorio.UsuarioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.AutenticacionServicio;
import co.edu.uniquindio.unilocal.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AutenticacionServicioImpl implements AutenticacionServicio {

    private final UsuarioRepo usuarioRepo;
    private final JWTUtils jwtUtils;

    @Override
    public TokenDTO iniciarSesionCliente(LoginDTO loginDTO) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepo.findByCorreo(loginDTO.correo());
        if (usuarioOptional.isEmpty()) {
            throw new Exception("El correo no se encuentra registrado");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Usuario usuario = usuarioOptional.get();
        if( !passwordEncoder.matches(loginDTO.contrasenia(), usuario.getContrasenia()) ) {
            throw new Exception("La contraseña es incorrecta");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rol", "CLIENTE");
        map.put("nombre", usuario.getNombre());
        map.put("id", usuario.getId());
        return new TokenDTO( jwtUtils.generarToken(usuario.getCorreo(), map) );
    }

    @Override
    public TokenDTO iniciarSesionModerador(LoginDTO loginDTO) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepo.findByCorreo(loginDTO.correo());
        if (usuarioOptional.isEmpty()) {
            throw new Exception("El correo no se encuentra registrado");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Usuario usuario = usuarioOptional.get();
        if( !passwordEncoder.matches(loginDTO.contrasenia(), usuario.getContrasenia()) ) {
            throw new Exception("La contraseña es incorrecta");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rol", "Moderador");
        map.put("nombre", usuario.getNombre());
        map.put("id", usuario.getId());
        return new TokenDTO( jwtUtils.generarToken(usuario.getCorreo(), map) );
    }
}
