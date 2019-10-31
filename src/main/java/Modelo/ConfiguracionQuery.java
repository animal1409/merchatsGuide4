package Modelo;

/**
 * Complex type para configuracion de consultas
 */
public class ConfiguracionQuery {


    private TipoQuery tipoQuery;
    private String regEx;

    public ConfiguracionQuery(TipoQuery tipoQueryVal, String regExVal)
    {
        this.tipoQuery = tipoQueryVal;
        this.regEx = regExVal;
    }

    // region Getters_Setters
    public TipoQuery getTipoQuery() {
        return tipoQuery;
    }

    public void setTipoQuery(TipoQuery tipoQuery) {
        this.tipoQuery = tipoQuery;
    }

    public String getRegEx() {
        return regEx;
    }

    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

    //endregion Getters_Setter
}


