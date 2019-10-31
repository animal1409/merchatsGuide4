package Modelo.GestoresQuery;

import Modelo.TipoQuery;

public class FactoryGestorQuery {

    public IGestorQuery obtenerGestorQuery(TipoQuery tipoQuery)
    {

        IGestorQuery res = null;
        switch (tipoQuery)
        {

            case Declarativa:
                res = new DeclarativaQuery();
                break;
            case Calculativa:
                res = new CalculativaQuery();
                break;
            case Crediticia:
                res = new CrediticiaQuery();
                break;
            case Cuantitativa:
                res = new CuantitativaQuery();
                break;
            case NoValida:
                res = new NoValidaQuery();
                break;
        }
        return res;

    }


}
