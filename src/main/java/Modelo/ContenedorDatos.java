package Modelo;

import java.util.HashMap;

public class ContenedorDatos {
    private HashMap<String,String> dicExtraterresteNumeroRomano;
    private HashMap<String,Double>  dicValorMetal;

    private static ContenedorDatos contendorDatos;

    private ContenedorDatos()
    {
        this.dicExtraterresteNumeroRomano =  new HashMap<String,String>();
        this.dicValorMetal = new HashMap<String,Double>();

    }
    public static ContenedorDatos obtenerInstancia()
    {
        if (contendorDatos == null)
            contendorDatos = new ContenedorDatos();

        return contendorDatos;

    }

    public void AnadirExtraterrestreRomano(String palabraExtraterrestre, String valorRomano)
    {
                    this.getDicExtraterresteNumeroRomano().put(palabraExtraterrestre,valorRomano);

    }

    public void AnadirValorMetal(String nombreMetal, Double valorMetal)
    {
        this.getDicValorMetal().put(nombreMetal,valorMetal);
    }


    public HashMap<String, String> getDicExtraterresteNumeroRomano() {
        return dicExtraterresteNumeroRomano;
    }

    public HashMap<String, Double> getDicValorMetal() {
        return dicValorMetal;
    }

    /**
     * Resetea los datos guardados
     */
    public static void ResetearDatos(){
        contendorDatos = null;
    }
}
