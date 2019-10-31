package Modelo.GestoresQuery;

import Modelo.ContenedorDatos;

import java.util.List;

public class DeclarativaQuery implements IGestorQuery {
    /**
     * Gestion el query para el tipo de query declarativa
     * @return
     */
    @Override
    public String gestionarQuery(String query) {
        String res = "";

        List<String> lstParametros = Utilidades.Utils.DescomponerTexto(" is ",query);
        ContenedorDatos.obtenerInstancia().AnadirExtraterrestreRomano(lstParametros.get(0),lstParametros.get(1));
        return res;
    }



    @Override
    public boolean devuelveOutput() {
        return false;
    }
}
