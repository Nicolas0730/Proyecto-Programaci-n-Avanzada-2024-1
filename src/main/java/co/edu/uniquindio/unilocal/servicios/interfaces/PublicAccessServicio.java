package co.edu.uniquindio.unilocal.servicios.interfaces;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PublicAccessServicio {

    List<String> listarCiudades() throws Exception;
    List<String> listarTiposNegocio() throws Exception;
}
