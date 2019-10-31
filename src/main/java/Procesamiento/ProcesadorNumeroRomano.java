package Procesamiento;

import Excepciones.NumeroNoValidoException;
import Modelo.ContenedorDatos;
import Modelo.NumeroRomano;
import Utilidades.Logger;
//import sun.nio.ch.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class ProcesadorNumeroRomano {

    private List<NumeroRomano> lstNumeroRomano = null;


    private static ProcesadorNumeroRomano procesadorNumeroRomano;

    /**
     * Construtcor
     */
    private ProcesadorNumeroRomano()
    {
        this.lstNumeroRomano = new ArrayList<NumeroRomano>();
            ConfigurarRomanos();
    }

    /**
     * Conifugra los valores de números romanos por defecto
     */
    private void ConfigurarRomanos()
    {

        this.lstNumeroRomano.add(new NumeroRomano('I', 1));
        this.lstNumeroRomano.add(new NumeroRomano('V',5));
        this.lstNumeroRomano.add(new NumeroRomano('X',10));
        this.lstNumeroRomano.add(new NumeroRomano('L',50));
        this.lstNumeroRomano.add(new NumeroRomano('C',100));
        this.lstNumeroRomano.add(new NumeroRomano('D',500));
        this.lstNumeroRomano.add(new NumeroRomano('M',1000));
    }



    public static ProcesadorNumeroRomano obtenerInstancia()
    {
            if (procesadorNumeroRomano == null)
            {
                procesadorNumeroRomano = new ProcesadorNumeroRomano();
            }
            return procesadorNumeroRomano;
    }



    private int obtenerDecimalPorCaracter(char caracterVal)
    {

        NumeroRomano numeroRomano=  lstNumeroRomano.stream().filter(x->x.getSigno()==caracterVal).findFirst().get();
        return numeroRomano.getnDecimal();
    }


    /**
     * Conviete un numero romano a decimal
     * @param numeroRomanoVal
     * @return
     * @throws NumeroNoValidoException
     */
    public  int convertirRomanoADecimal(String numeroRomanoVal) throws NumeroNoValidoException {

        try {
            if (!esRomanoValido(numeroRomanoVal)) {
                throw new NumeroNoValidoException();
            }

            List<Character> lstNumeroRomanoVal = Utilidades.Utils.convertStringToCharList(numeroRomanoVal);
            List<Integer> litNumeroDecimal = new ArrayList<Integer>();
            lstNumeroRomanoVal.forEach(charRomano -> litNumeroDecimal.add(obtenerDecimalPorCaracter(charRomano)));


            Collections.reverse(litNumeroDecimal);

            int resultado = 0;
            int valorActual = 0;
            int valorPrevio = 0;

            for (int i = 0; i < litNumeroDecimal.size(); i++) {
                valorActual = litNumeroDecimal.get(i);
                valorActual = valorPrevio > valorActual ? -valorActual : valorActual;
                resultado += valorActual;
                valorPrevio = valorActual;
            }

            return resultado;
        }
        catch (Exception ex)
        {
            Logger.getLogger().GuardarExcepcion(ex);
            throw ex;
        }

    }




    /**
     * Verifica si es que el texto contiene un caracter no valido
     * @param numeroRomanoVal numero romano
     * @return valido o no
     */
    private boolean esRomanoValido(String numeroRomanoVal) {

        List<Character> lstChar = Utilidades.Utils.convertStringToCharList(numeroRomanoVal);
        long nroNoValidos = lstChar.stream().filter(x -> !this.lstNumeroRomano.contains(x)).count();
        boolean respuesta;
        respuesta = nroNoValidos == 0 ? false : true;
        return respuesta;
    }

    /**
     * Obtiene el NumeroRomanoAPartirDePalabras Extraterrresters
     * @param lstPalabrasExtraterrestres
     * @return Romano
     */
    public String obtenerNumeroRomanoDePalabrasExtraterrestres(List<String> lstPalabrasExtraterrestres)
    {
        HashMap extNroRomano =  ContenedorDatos.obtenerInstancia().getDicExtraterresteNumeroRomano();
        StringBuilder sb = new StringBuilder();
        lstPalabrasExtraterrestres.forEach(x->{
            sb.append(extNroRomano.get(x));

            Logger.getLogger().Log(x);
        });

        Logger.getLogger().Log("La union de palabras extraterrestres");
        Logger.getLogger().Log(sb.toString());
        return sb.toString();
    }



    ///#region Getters_Setters
    public List<NumeroRomano> getLstNumeroRomano() {
        return lstNumeroRomano;
    }

    public void setLstNumeroRomano(List<NumeroRomano> lstNumeroRomano) {
        this.lstNumeroRomano = lstNumeroRomano;
   }
   ///#endregion Getters_Setters
}
