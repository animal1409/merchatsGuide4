package Procesamiento;

import Modelo.ConfiguracionQuery;
import Modelo.TipoQuery;
import Utilidades.Utils;

import java.util.List;
import java.util.Optional;

/**
 * Se encarga de realizar los procesos de la consulta de entrada
 */
public class ProcesadorQuery {

    private static ProcesadorQuery procesadorQuery;
    private List<ConfiguracionQuery> lstConfiguraiconQuery;

    private ProcesadorQuery()
    {


    }

    public static ProcesadorQuery obtenerInstancia() {

        if (procesadorQuery == null)
        {
            procesadorQuery = new ProcesadorQuery();
        }
        return procesadorQuery;

    }

    public void ConfigurarTipoQuery(List<ConfiguracionQuery> lstConfiguracionQueryVal)
    {
        this.lstConfiguraiconQuery = lstConfiguracionQueryVal;
    }

    /**
     * Obtiene el tipo de query en base al texto de entrada
     * @param textoEntrada texto de entrada
     * @return tpo de query a retornar
     */
    public TipoQuery obtenerTipoQuery(String textoEntrada) {
        TipoQuery res;
        Optional<ConfiguracionQuery> optConfQuery =this.lstConfiguraiconQuery.stream().filter(lstConf -> Utils.IsMatch(textoEntrada,lstConf.getRegEx())).findFirst();
        if (optConfQuery.isPresent())
        {
            res = optConfQuery.get().getTipoQuery();
        }
        else
        {
            res = TipoQuery.NoValida;
        }
           return res;
    }
}
