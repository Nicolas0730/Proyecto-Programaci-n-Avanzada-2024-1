package co.edu.uniquindio.unilocal.servicios.implementacion;

import co.edu.uniquindio.unilocal.model.Ciudad;
import co.edu.uniquindio.unilocal.model.TipoNegocio;
import co.edu.uniquindio.unilocal.repositorio.NegocioRepo;
import co.edu.uniquindio.unilocal.servicios.interfaces.PublicAccessServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicAccessServicioImpl implements PublicAccessServicio {

    /**
     * Método que lista todos los valores que puede tener una enumeración de tipo ciudad
     * @return
     * @throws Exception
     */
    @Override
    public List<String> listarCiudades() throws Exception {
        return obtenerValoresEnumeracion(Ciudad.class);
    }

    // Método para obtener todos los valores de una enumeración y almacenarlos en una lista
    public static <T extends Enum<T>> List<String> obtenerValoresEnumeracion(Class<T> enumeration) {
            List<String> valores = new ArrayList<>();
            // Recorre todos los valores de la enumeración y los agrega a la lista
            for (T enumConstant : enumeration.getEnumConstants()) {
                valores.add(enumConstant.toString());
            }
            return valores;
    }


    /**
     * Método que lista todos los valores que puede tener una enumeración de tipo negocio
     * @return
     * @throws Exception
     */
    @Override
    public List<String> listarTiposNegocio() throws Exception {
        return obtenerValoresEnumeracion(TipoNegocio.class);
    }
}
