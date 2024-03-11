package co.edu.uniquindio.unilocal.servicios.interfaces;

public interface NegocioServicio {

    void crearNegocio();
    void actualizarNegocio();
    void eliminarNegocio();
    void buscarNegocio();
    void filtrarPorEstado();
    void listarNegociosPropietario();
    void cambiarEstado();
    void registrarRevision();


}
