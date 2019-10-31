package Modelo.GestoresQuery;

import Excepciones.NumeroNoValidoException;
import Modelo.ContenedorDatos;
import Procesamiento.ProcesadorNumeroRomano;
import Utilidades.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalculativaQuery implements IGestorQuery {

    @Override
    public String gestionarQuery(String query) throws NumeroNoValidoException {
        String res = "";
        //glob glob Silver is 34 Credits

        //split por "is"
        List<String> lstPrincipal = Utilidades.Utils.DescomponerTexto(" is ",query);

        //obtener del [0] los n-1
        List<String> lstIzquierda = Utilidades.Utils.DescomponerTexto(" ",lstPrincipal.get(0));

        String metal = lstIzquierda.get(lstIzquierda.size()-1);

        List<String> lstPalabrasExtraterrestres = new ArrayList<String>();

        //obtenemos solo los nomrbres exterrestres
        for(int i=0;i<lstIzquierda.size()-1;i++)
        {
            lstPalabrasExtraterrestres.add(lstIzquierda.get(i));
        }

        //pediro en el hashmap los caracteres de romano
        String nroRomano = ObtenerNumeroRomanoDePalabrasExtraterrestres(lstPalabrasExtraterrestres);
        //concatenar y pedir el numero en ProcesadorNumeroRomano

        int nroDecimal = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal(nroRomano);
        //hacer funcion de devolucion del Valor del metal

        int nroCreditos =Integer.parseInt( Utilidades.Utils.DescomponerTexto(" " ,lstPrincipal.get(1)).get(0));
        double valorMetal = ObtenerValorMetal(nroCreditos,nroDecimal);

        //guardar el metal con su valor
        ContenedorDatos.obtenerInstancia().AnadirValorMetal(metal,valorMetal);


        return res;
    }

    private Double ObtenerValorMetal(int nroCreditos, int nroDecimal) {
        return (((double)nroCreditos)/((double) nroDecimal));
    }


    private String ObtenerNumeroRomanoDePalabrasExtraterrestres(List<String> lstPalabrasExtraterrestres)
    {
        return ProcesadorNumeroRomano.obtenerInstancia().obtenerNumeroRomanoDePalabrasExtraterrestres(lstPalabrasExtraterrestres);
    }


    @Override
    public boolean devuelveOutput() {
        return false;
    }
}
