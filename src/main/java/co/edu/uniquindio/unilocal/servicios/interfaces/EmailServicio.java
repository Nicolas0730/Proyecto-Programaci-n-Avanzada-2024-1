package co.edu.uniquindio.unilocal.servicios.interfaces;

import co.edu.uniquindio.unilocal.dto.EmailDTO;
import org.springframework.stereotype.Service;

public interface EmailServicio {

    void enviarCorreo(EmailDTO emailDTO) throws Exception;
}
