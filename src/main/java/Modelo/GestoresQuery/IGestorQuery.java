package Modelo.GestoresQuery;

import Excepciones.NumeroNoValidoException;

public interface IGestorQuery {
    /**
     * Gestiona el query de entrada
     * @param query entrada
     * @return
     */
    public String  gestionarQuery(String query) throws NumeroNoValidoException;

    public boolean devuelveOutput();



}
