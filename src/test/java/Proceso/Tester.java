package Proceso;

import Excepciones.NumeroNoValidoException;
import Modelo.ConfiguracionQuery;
import Modelo.ContenedorDatos;
import Modelo.GestoresQuery.FactoryGestorQuery;
import Modelo.GestoresQuery.IGestorQuery;
import Modelo.TipoQuery;
import Procesamiento.ProcesadorAplicacion;
import Procesamiento.ProcesadorNumeroRomano;
import Procesamiento.ProcesadorQuery;
import Utilidades.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
//import sun.nio.ch.Util;

import java.util.ArrayList;
import java.util.List;

public class Tester {




    private  static List<ConfiguracionQuery> lstConfiguracionQuery;



    @Before
    public void ConfigurarTester()
    {

        //region ConfiguracionTipoQuery
        lstConfiguracionQuery = new ArrayList<ConfiguracionQuery>();
        lstConfiguracionQuery.add(new ConfiguracionQuery(TipoQuery.Declarativa,"^([A-Za-z]+) is ([I|V|X|L|C|D|M])$"));
        lstConfiguracionQuery.add(new ConfiguracionQuery(TipoQuery.Calculativa,"(.*) is ([0-9]+) ([c|C]redits)$"));
        lstConfiguracionQuery.add(new ConfiguracionQuery(TipoQuery.Crediticia,"^how many [C|c]redits is .*?$"));
        lstConfiguracionQuery.add(new ConfiguracionQuery(TipoQuery.Cuantitativa,"^how much is .*?$"));

        //endregion ConfiguracionTipoQuery


    }

    @Test
    public void ProbarRomanos() throws NumeroNoValidoException {
        int numDecimalI = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal("I");
        Assert.assertEquals(1, numDecimalI);
        int numDecimalV = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal("V");
        Assert.assertEquals(5, numDecimalV);
        int numDecimalX  = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal("X");
        Assert.assertEquals(10, numDecimalX);
        int numDecimalL  = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal("L");
        Assert.assertEquals(50, numDecimalL);
        int numDecimalC  = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal("C");
        Assert.assertEquals(100, numDecimalC);
        int numDecimalD  = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal("D");
        Assert.assertEquals(500, numDecimalD);
        int numDecimalM  = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal("M");
        Assert.assertEquals(1000, numDecimalM);


    }

    @Test
    public void ProbarDecimalesCompuestos() throws NumeroNoValidoException {
        int numDecimalII = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal("II");
        Assert.assertEquals(2,numDecimalII);
        Assert.assertNotEquals(3,numDecimalII);


        int numDecimalXXX = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal("XXX");
        Assert.assertEquals(30,numDecimalXXX);
        Assert.assertNotEquals(40,numDecimalXXX);

        int numDecimalIV = ProcesadorNumeroRomano.obtenerInstancia().convertirRomanoADecimal("IV");
        Assert.assertEquals(4,numDecimalIV);
        Assert.assertNotEquals(5,numDecimalIV);

    }


    @Test
    public void ProbarQueryPorTipo()
    {
        String queryDeclarativa = "glob is I";
        String queryCalculativa = "glob glob Silver is 34 Credits";
        String queryCuantitativa = "how much is pish tegj glob glob ?";
        String queryCrediticia = "how many Credits is glob prok Silver ?";
        String queryNoValida = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?";

        Assert.assertEquals(TipoQuery.Declarativa,obtenerTipoQueryPorQuery(queryDeclarativa));
        Assert.assertEquals(TipoQuery.Calculativa,obtenerTipoQueryPorQuery(queryCalculativa));
        Assert.assertEquals(TipoQuery.Cuantitativa,obtenerTipoQueryPorQuery(queryCuantitativa));
        Assert.assertEquals(TipoQuery.Crediticia,obtenerTipoQueryPorQuery(queryCrediticia));
        Assert.assertEquals(TipoQuery.NoValida,obtenerTipoQueryPorQuery(queryNoValida));
        Assert.assertTrue(TipoQuery.Calculativa != obtenerTipoQueryPorQuery(queryCrediticia));



    }

    private TipoQuery obtenerTipoQueryPorQuery(String entrada)
    {
        ProcesadorQuery procQuery =ProcesadorQuery.obtenerInstancia();
        procQuery.ConfigurarTipoQuery(lstConfiguracionQuery);
        return procQuery.obtenerTipoQuery(entrada) ;
    }

@Test
    public void ProbarContenedorDatos()
    {
        ContenedorDatos.ResetearDatos();
        ContenedorDatos contenedorDatos = ContenedorDatos.obtenerInstancia();

        //debe iniciar el I y luego reeamplazar el II
        contenedorDatos.AnadirExtraterrestreRomano("clink","I");
        contenedorDatos.AnadirExtraterrestreRomano("clink","II");

        //debe obtener la misma instancia y anadir clank como 2do elemento
        ContenedorDatos contenedor2 = ContenedorDatos.obtenerInstancia();
        contenedorDatos.AnadirExtraterrestreRomano("clank","III");

        //obtener 3ra instancia (la misma) para consultas
        ContenedorDatos contendor3 = ContenedorDatos.obtenerInstancia();

        //debe dar el ultimo elemento configurado en clink
        Assert.assertEquals("II",contendor3.getDicExtraterresteNumeroRomano().get("clink"));

        //debe haber anadido el clank en la 2da instancia
        Assert.assertEquals("III",contendor3.getDicExtraterresteNumeroRomano().get("clank"));

        //solo debe haber dos elementos en el hashmap
        Assert.assertEquals(2, contendor3.getDicExtraterresteNumeroRomano().size());
    }

@Test
public void ProbarSeparadorTexto()
{
    String texto = " elemento1  is  elemento2  is  elemento3 ";

    List<String> lstTextoSeparado = Utilidades.Utils.DescomponerTexto(" is ",texto);

    Assert.assertEquals(3,lstTextoSeparado.size());
    Assert.assertEquals("elemento1",lstTextoSeparado.get(0));
    Assert.assertEquals("elemento2",lstTextoSeparado.get(1));
    Assert.assertEquals("elemento3",lstTextoSeparado.get(2));

}

    @Test
    public void ProbarDeclarativeQuery() throws NumeroNoValidoException {
        ContenedorDatos.ResetearDatos();
        ProcesadorQuery procQuery = ProcesadorQuery.obtenerInstancia();
        procQuery.ConfigurarTipoQuery(lstConfiguracionQuery);
        TipoQuery tipoQuery = ProcesadorQuery.obtenerInstancia().obtenerTipoQuery("glob is I");
        Assert.assertEquals(TipoQuery.Declarativa, tipoQuery);

        IGestorQuery gestorQuery = new FactoryGestorQuery().obtenerGestorQuery(TipoQuery.Declarativa);

        gestorQuery.gestionarQuery("glob is I");
        gestorQuery.gestionarQuery("prok is V");
        gestorQuery.gestionarQuery("pish is X");
        gestorQuery.gestionarQuery("tegj is L");

        String txtRomanI = ContenedorDatos.obtenerInstancia().getDicExtraterresteNumeroRomano().get("glob");
        String txtRomanV = ContenedorDatos.obtenerInstancia().getDicExtraterresteNumeroRomano().get("prok");
        String txtRomanX = ContenedorDatos.obtenerInstancia().getDicExtraterresteNumeroRomano().get("pish");

        Assert.assertEquals("I", txtRomanI);
        Assert.assertEquals("V", txtRomanV);
        Assert.assertEquals("X", txtRomanX);

    }

    @Test
    public void ProbarCalculativaQuery() throws NumeroNoValidoException {


        //encerar datos
        ContenedorDatos.ResetearDatos();

        //configugrar el procesador de queries
        ProcesadorQuery procQuery = ProcesadorQuery.obtenerInstancia();
        procQuery.ConfigurarTipoQuery(lstConfiguracionQuery);

        //obtenermos el proce
        /*TipoQuery tipoQuery = ProcesadorQuery.obtenerInstancia().obtenerTipoQuery("glob is I");
        Assert.assertEquals(TipoQuery.Declarativa, tipoQuery);*/

        //obtenermos un gestor de query declarativa, se obtendrá automaticamente luego por el tipo de query
        IGestorQuery gestorQueryDeclarativa = new FactoryGestorQuery().obtenerGestorQuery(TipoQuery.Declarativa);

        gestorQueryDeclarativa.gestionarQuery("glob is I");
        gestorQueryDeclarativa.gestionarQuery("prok is V");
        gestorQueryDeclarativa.gestionarQuery("pish is X");
        gestorQueryDeclarativa.gestionarQuery("tegj is L");

        //obtenermos un gestor de Query calculativa para ingresar las variables de tipo clavulativas
        IGestorQuery gestorQueryCalculativa = new FactoryGestorQuery().obtenerGestorQuery(TipoQuery.Calculativa);

        String querySilver = "glob glob Silver is 34 Credits";
        String queryGold = "glob prok Gold is 57800 Credits";
        String queryIron = "pish pish Iron is 3910 Credits";
        gestorQueryCalculativa.gestionarQuery(querySilver);
        gestorQueryCalculativa.gestionarQuery(queryGold);
        gestorQueryCalculativa.gestionarQuery(queryIron);




        double valorMetalSilver = ContenedorDatos.obtenerInstancia().getDicValorMetal().get("Silver");
        double valorMetalGold = ContenedorDatos.obtenerInstancia().getDicValorMetal().get("Gold");
        double valorMetalIron = ContenedorDatos.obtenerInstancia().getDicValorMetal().get("Iron");
        Assert.assertEquals(17.00, valorMetalSilver, 0);
        Assert.assertEquals(14450, valorMetalGold, 0);
        Assert.assertEquals(195.5, valorMetalIron, 0);


    }

    @Test
    public void ProbarCualitativaQuery() throws NumeroNoValidoException {

        //region PasosPrevios

        //encerar datos
        ContenedorDatos.ResetearDatos();

        //configugrar el procesador de queries
        ProcesadorQuery procQuery = ProcesadorQuery.obtenerInstancia();
        procQuery.ConfigurarTipoQuery(lstConfiguracionQuery);


        //obtenermos un gestor de query declarativa, se obtendrá automaticamente luego por el tipo de query
        IGestorQuery gestorQueryDeclarativa = new FactoryGestorQuery().obtenerGestorQuery(TipoQuery.Declarativa);

        gestorQueryDeclarativa.gestionarQuery("glob is I");
        gestorQueryDeclarativa.gestionarQuery("prok is V");
        gestorQueryDeclarativa.gestionarQuery("pish is X");
        gestorQueryDeclarativa.gestionarQuery("tegj is L");

        //obtenermos un gestor de Query calculativa para ingresar las variables de tipo clavulativas
        IGestorQuery gestorQueryCalculativa = new FactoryGestorQuery().obtenerGestorQuery(TipoQuery.Calculativa);

        String querySilver = "glob glob Silver is 34 Credits";
        String queryGold = "glob prok Gold is 57800 Credits";
        String queryIron = "pish pish Iron is 3910 Credits";
        gestorQueryCalculativa.gestionarQuery(querySilver);
        gestorQueryCalculativa.gestionarQuery(queryGold);
        gestorQueryCalculativa.gestionarQuery(queryIron);

        //#endregion PasosPrevios

        IGestorQuery gestorCuantitativa = new FactoryGestorQuery().obtenerGestorQuery(TipoQuery.Cuantitativa);
        String respuestaCuanto = gestorCuantitativa.gestionarQuery("how much is pish tegj glob glob ?");
        //XLII
        Assert.assertEquals("pish tegj glob glob is 42",respuestaCuanto);

    }

    @Test
    public void ProbarCrediticiaQuery() throws NumeroNoValidoException {
        //region PasosPrevios

        //encerar datos
        ContenedorDatos.ResetearDatos();

        //configugrar el procesador de queries
        ProcesadorQuery procQuery = ProcesadorQuery.obtenerInstancia();
        procQuery.ConfigurarTipoQuery(lstConfiguracionQuery);


        //obtenermos un gestor de query declarativa, se obtendrá automaticamente luego por el tipo de query
        IGestorQuery gestorQueryDeclarativa = new FactoryGestorQuery().obtenerGestorQuery(TipoQuery.Declarativa);

        gestorQueryDeclarativa.gestionarQuery("glob is I");
        gestorQueryDeclarativa.gestionarQuery("prok is V");
        gestorQueryDeclarativa.gestionarQuery("pish is X");
        gestorQueryDeclarativa.gestionarQuery("tegj is L");

        //obtenermos un gestor de Query calculativa para ingresar las variables de tipo clavulativas
        IGestorQuery gestorQueryCalculativa = new FactoryGestorQuery().obtenerGestorQuery(TipoQuery.Calculativa);

        String querySilver = "glob glob Silver is 34 Credits";
        String queryGold = "glob prok Gold is 57800 Credits";
        String queryIron = "pish pish Iron is 3910 Credits";
        gestorQueryCalculativa.gestionarQuery(querySilver);
        gestorQueryCalculativa.gestionarQuery(queryGold);
        gestorQueryCalculativa.gestionarQuery(queryIron);



        IGestorQuery gestorCuantitativa = new FactoryGestorQuery().obtenerGestorQuery(TipoQuery.Cuantitativa);
        String respuestaCuanto = gestorCuantitativa.gestionarQuery("how much is pish tegj glob glob");
        //XLII




        //#endregion PasosPrevios

        String entradaCrediticia = "how many Credits is glob prok Silver ?";
        TipoQuery tipoQueryCrediticia = procQuery.obtenerTipoQuery(entradaCrediticia);

        Assert.assertEquals(TipoQuery.Crediticia,tipoQueryCrediticia);

        IGestorQuery gestorQueryCrediticia = new FactoryGestorQuery().obtenerGestorQuery(TipoQuery.Crediticia);
        String salidaCrediticia = gestorQueryCrediticia.gestionarQuery(entradaCrediticia);
        Assert.assertEquals("glob prok Silver is 68 Credits",salidaCrediticia);

    }

    @Test
    public void ProbarNoValidaQuery() throws NumeroNoValidoException {
        //region PasosPrevios

        //encerar datos
        ContenedorDatos.ResetearDatos();

        //configugrar el procesador de queries
        ProcesadorQuery procQuery = ProcesadorQuery.obtenerInstancia();
        procQuery.ConfigurarTipoQuery(lstConfiguracionQuery);

        //confiugracmos la entrada no valida
        String entradaNoValida = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?";
        TipoQuery tipoQueryNoValida = procQuery.obtenerTipoQuery(entradaNoValida);

        //gestiona el ingreso del query
        IGestorQuery gestorQueryNoValida = new FactoryGestorQuery().obtenerGestorQuery(tipoQueryNoValida);
        String resultado = gestorQueryNoValida.gestionarQuery(entradaNoValida);
        Assert.assertEquals("I have no idea what you are talking about",resultado);

    }

@Test
    public void ProbarProcesoAplicacion()
    {


        //region ConfiguracionInicial

        List<ConfiguracionQuery> lstConfQuery = new ArrayList<ConfiguracionQuery>();
        lstConfQuery.add(new ConfiguracionQuery(TipoQuery.Declarativa,"^([A-Za-z]+) is ([I|V|X|L|C|D|M])$"));
        lstConfQuery.add(new ConfiguracionQuery(TipoQuery.Calculativa,"(.*) is ([0-9]+) ([c|C]redits)$"));
        lstConfQuery.add(new ConfiguracionQuery(TipoQuery.Crediticia,"^how many [C|c]redits is .*?$"));
        lstConfQuery.add(new ConfiguracionQuery(TipoQuery.Cuantitativa,"^how much is .*?$"));

        ProcesadorAplicacion procesadorAplicacion = ProcesadorAplicacion.obtenerInstancia();
        procesadorAplicacion.configurarQueries(lstConfQuery);

        //endregion ConfiguracionInicial

        //region ConfigurarEntradaDatos
        List<String> lstEntradas = new ArrayList<String>();

        lstEntradas.add("glob is I");
        lstEntradas.add("prok is V");
        lstEntradas.add("pish is X");
        lstEntradas.add("tegj is L");
        lstEntradas.add("glob glob Silver is 34 Credits");
        lstEntradas.add("glob prok Gold is 57800 Credits");
        lstEntradas.add("pish pish Iron is 3910 Credits");
        lstEntradas.add("how much is pish tegj glob glob ?");
        lstEntradas.add("how many Credits is glob prok Silver ?");
        lstEntradas.add("how many Credits is glob prok Gold ?");
        lstEntradas.add("how many Credits is glob prok Iron ?");
        lstEntradas.add("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
        //endregion ConfigurarEntradaDatos


        procesadorAplicacion.ingresarEntradas(lstEntradas);
        procesadorAplicacion.procesarEntradas();

         List<String> lstSalidasResultados = procesadorAplicacion.obtenerSalida();
         List<String> lstSalidasDefinidas = new ArrayList<String>();

         //region configuracionSalidasDefinidas
        lstSalidasDefinidas.add("pish tegj glob glob is 42");
        lstSalidasDefinidas.add("glob prok Silver is 68 Credits");
        lstSalidasDefinidas.add("glob prok Gold is 57800 Credits");
        lstSalidasDefinidas.add("glob prok Iron is 782 Credits");
        lstSalidasDefinidas.add("I have no idea what you are talking about");

        //endregion configuracionSalidasDefinidas

        Assert.assertEquals(lstSalidasDefinidas,lstSalidasResultados);



    }


}
