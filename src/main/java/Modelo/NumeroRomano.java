package Modelo;

import Excepciones.NumeroNoValidoException;
import Procesamiento.ProcesadorNumeroRomano;

public class NumeroRomano {
    private char signo;
    private int nDecimal;

    //region Getters_Setters
    public char getSigno() {
        return signo;
    }

    public void setSigno(char signo) {
        this.signo = signo;
    }

    public int getnDecimal() {
        return nDecimal;
    }

    public void setnDecimal(int nDecimal) {
        this.nDecimal = nDecimal;
    }

    //endregion Getters_Setters

    //region Constructors
    public NumeroRomano (char signoVal) throws NumeroNoValidoException {
        this.signo= signoVal;
        this.nDecimal = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal(Character.toString(signo));
    }

    public NumeroRomano(char signoVal, int nDecimalVal)
    {
        this.signo = signoVal;
        this.nDecimal = nDecimalVal;
    }

    //endregion Constructors


}
