package co.edu.uniquindio.unilocal.servicios.interfaces;

public interface ModeradorServicio extends CuentaServicio{

    /**
     * Método que verifica que un negocio se pueda autorizar o rechazar
     * verificando que este no exista ya
     * @param idNegocio
     */
    void validarNegocio(String idNegocio);

    void autorizarNegocio();

    void rechazarNegocio();

    void eliminarNegocio();

}
