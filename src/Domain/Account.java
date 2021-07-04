package Domain;

import java.util.Date;

/**
 *
 * @author LuisGa && Sebas
 */
public abstract class Account {

    private static int idCounter = 1;      //Contador de ids(Se generan automáticamente.)


    private int id;                //ID único de la cuenta

    private final char currency;         //Tipo de moneda 

    private final Date openingDate;      //Fecha de apertura

    private final String clientID;

    public Account(char currency, Date openingDate, String clientId) {
        this.id = idCounter;
        idCounter++;
        this.currency = currency;
        this.openingDate = openingDate;
        this.clientID = clientId;
    }

    public int getId() {
        return id;
    }

    public char getCurrency() {
        return currency;
    }

    public String getClientId() {
        return clientID;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", currency=" + currency + ", openingDate=" + openingDate.getDay()+"/"+openingDate.getMonth()+"/"+ openingDate.getYear()+1900+"/"+ ", clientID=" + clientID + '}';
    }
    
    

}
