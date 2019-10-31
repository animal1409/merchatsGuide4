package Modelo.GestoresQuery;

import Excepciones.NumeroNoValidoException;
import Modelo.ContenedorDatos;
import Procesamiento.ProcesadorNumeroRomano;
import Utilidades.Logger;
import Utilidades.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CrediticiaQuery implements IGestorQuery {
    @Override
    public String gestionarQuery(String query) throws NumeroNoValidoException {

        String res = "";

        query = query.replace("?", "");
        String textoPregunta = Utils.DescomponerTexto(" is ", query).get(1);
        //how many Credits is glob prok Iron ?
        List<String> lstPalabrasExtraterretres = Utils.DescomponerTexto(" ", textoPregunta);
        //el ultimo es el Metal
        String nombreMetal = lstPalabrasExtraterretres.get(lstPalabrasExtraterretres.size() - 1);
        //obtener el valor del Metal
        double valorMetal = ContenedorDatos.obtenerInstancia().getDicValorMetal().get(nombreMetal);
        List<String> lstExtraTerrestresRomanos = lstPalabrasExtraterretres.subList(0, lstPalabrasExtraterretres.size() - 1);

        ProcesadorNumeroRomano proc = ProcesadorNumeroRomano.obtenerInstancia();
        String nroRomano  = proc.obtenerNumeroRomanoDePalabrasExtraterrestres(lstExtraTerrestresRomanos);
        int nroDecimal = proc.convertirRomanoADecimal(nroRomano);
        res = ConstruirRespuesta(textoPregunta,nroDecimal,valorMetal);
        return res;

    }

    private String ConstruirRespuesta(String entrada, int nroDecimal, double valorMetal)
    {

        Logger.getLogger().Log("nroDecimal: "+nroDecimal);
        Logger.getLogger().Log("valorMetal "+valorMetal);
        double Valor = ((double)nroDecimal)*valorMetal;
        String strValor = new DecimalFormat("#").format(Valor);
        String respuesta = entrada+ " is "+ strValor + " Credits";
        return respuesta;
    }

    @Override
    public boolean devuelveOutput() {
        return true;
    }
}
