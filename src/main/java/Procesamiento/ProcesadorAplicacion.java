package Procesamiento;

import Modelo.ConfiguracionQuery;
import Modelo.ContenedorDatos;
import Modelo.GestoresQuery.FactoryGestorQuery;
import Modelo.GestoresQuery.IGestorQuery;
import Modelo.TipoQuery;
import Utilidades.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProcesadorAplicacion {

private List<ConfiguracionQuery> lstConfiguracionQuery;
private static ProcesadorAplicacion procesadorAplicacion;
private List<String> lstEntradas ;
private List<String> lstSalidas;

private  ProcesadorAplicacion()
{

}

public static ProcesadorAplicacion obtenerInstancia()
{
    if (procesadorAplicacion == null) {
        ContenedorDatos.ResetearDatos();
        procesadorAplicacion = new ProcesadorAplicacion();
        procesadorAplicacion.lstSalidas  = new ArrayList<String>();
    }
    return procesadorAplicacion;

}

    /**
     * Configuracion de queries con sus expresiones regulares
     *
     * @param lstConfiguracionQueryVal
     */
    public void configurarQueries(List<ConfiguracionQuery> lstConfiguracionQueryVal) {
        this.lstConfiguracionQuery = lstConfiguracionQueryVal;
    }

    public void ingresarEntradas(List<String> lstEntradasVal)
    {
        this.lstEntradas = lstEntradasVal;
    }

    /**
     * Hace el procesamienteo de todas las entradas configuradas
     */
    public void procesarEntradas()
    {
        try {
            this.lstEntradas.forEach(e -> {
                try {
                    procesarQuery(e);
                } catch (Exception ex) {
                    Logger.getLogger().GuardarExcepcion(ex);
                }
            });
        }
        catch (Exception ex)
        {
            Logger.getLogger().GuardarExcepcion(ex);
        }
    }

    /**
     * Procesa el query por unidad
      * @param query
     */
    private void procesarQuery(String query) throws Exception {
        try {
            ProcesadorQuery procesadorQuery = ProcesadorQuery.obtenerInstancia();
            procesadorQuery.ConfigurarTipoQuery(this.lstConfiguracionQuery);
            TipoQuery tipoQuery = procesadorQuery.obtenerTipoQuery(query);
            IGestorQuery gestorQuery = new FactoryGestorQuery().obtenerGestorQuery(tipoQuery);
            boolean esOutput = gestorQuery.devuelveOutput();
            String respuesta = gestorQuery.gestionarQuery(query);
            if (esOutput)
            {
                this.lstSalidas.add(respuesta);
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger().Log(ex.toString());
            throw ex;
        }
    }


    /**
     * Obtiene las respuestas
     * @return
     */
    public List<String> obtenerSalida()
    {
        return this.lstSalidas;
    }





}
