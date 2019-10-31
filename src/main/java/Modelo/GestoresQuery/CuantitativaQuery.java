package Modelo.GestoresQuery;

import Excepciones.NumeroNoValidoException;
import Procesamiento.ProcesadorNumeroRomano;
import Utilidades.Utils;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class CuantitativaQuery implements IGestorQuery {
    @Override
    public String gestionarQuery(String query) throws NumeroNoValidoException {
        //how much is pish tegj glob glob ?
        //control signo pregunta
        query = query.replace("?","");


        String palabraPedidoExtraterrestre = Utils.DescomponerTexto(" is ", query).get(1);
        List<String> lstExtrarrestre = Utils.DescomponerTexto(" ", palabraPedidoExtraterrestre);
        ProcesadorNumeroRomano proc = ProcesadorNumeroRomano.obtenerInstancia();
        String nroRomano  = proc.obtenerNumeroRomanoDePalabrasExtraterrestres(lstExtrarrestre);
        int nroDecimal = proc.convertirRomanoADecimal(nroRomano);
        return construirRespuesta(palabraPedidoExtraterrestre,nroDecimal);
    }

    private String construirRespuesta(String palabraPedidoExtraterrestre, int nroDecimal)
    {
        return palabraPedidoExtraterrestre +" is "+ nroDecimal;
    }

    @Override
    public boolean devuelveOutput() {
        return true;
    }
}
