package Utilidades;

//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {
    /**
     * Conversion de string a char array
     * @param str
     * @return
     */
    public static List<Character>
    convertStringToCharList(String str)
    {
        List<Character> chars = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            chars.add(ch);
        }

        return chars;
    }

    /**
     * Hace el match de una expresion regular con su texto
     * @param texto el texto de entrada
     * @param regEx expresion regular
     * @return true o false si es que se hace el match
     */
    public static boolean IsMatch(String texto, String regEx)
    {
        boolean isMatch = Pattern.matches(regEx, texto);
        return isMatch;
    }

    /**
     * Desompone testo en lista de strings dado un separador
     * @param separador
     * @param texto
     * @return lista de textos separados
     */
    public static List<String> DescomponerTexto(String separador, String texto)
    {
        List<String> lstTextoSeparado = new ArrayList<String>();
        String[]  arrTextoSeparado  = texto.split(separador);
        for (int i =0; i< arrTextoSeparado.length; i++)
        {
            lstTextoSeparado.add(RemoverEspaciosLados(arrTextoSeparado[i]));
        }
        return lstTextoSeparado;
    }


    private static String RemoverEspaciosLados(String texto)
    {
        return trimEnd(trimStart(texto));
    }

    public static String trimEnd(String value) {
        return value.replaceFirst("\\s+$", "");
    }

    public static String trimStart(String value) {
        return value.replaceFirst("^\\s+", "");
    }
    
    
    

}
