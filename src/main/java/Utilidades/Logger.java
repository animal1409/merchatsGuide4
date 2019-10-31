package Utilidades;

public class Logger {

    private static  Logger logger;
    private boolean isOn = false;
    private Logger()
    {


    }

    public static Logger getLogger()
    {
        if(logger ==null)
            logger = new  Logger();

        return logger;
    }

    public void  Log(String texto)
    {
        if (this.isOn) {
            System.out.println(texto);
        }
    }


    public void GuardarExcepcion(Exception ex)
    {
            System.out.println(ex.toString());
    }

}
