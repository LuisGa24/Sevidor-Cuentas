/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author LuisGa && Sebas
 */
public final class TermAccount extends Account implements Serializable{

    private static final long serialVersionUID = 3258698714674442547L;
    private final float interestRate;               //Tasa de interes fija.(FINAL)
    private final int term;                         //Plazo en meses.
    private float startingAmount;             //Monto inicial.

    public TermAccount(char currency, Date openingDate, String clientId, float amount, float interestRate, int term) {
        super(currency, openingDate, clientId);
        this.interestRate = interestRate;
        this.startingAmount = (amount);               //El depósito inicial debe definirse al crear la cuenta.
        this.term = term;
    }

    public String projection() {                     //Proyección del monto final.
        return "Monto acumulado al final de plazo:" + (startingAmount + ((startingAmount * interestRate) * term));
    }

    public float getInterestRate() {
        return interestRate;
    }

    public float getStartingAmount() {
        return startingAmount;
    }
    
     public String[] dataName() {
        String[] dataName = {"id", "currency", "openingDate", "clientID", "interestRate", "startingAmount","term"};
        return dataName;
    }

    public String[] data() {
        
        String[] data = {String.valueOf(super.getId()), String.valueOf(super.getCurrency()), new SimpleDateFormat("dd-MM-yyyy").format(super.getOpeningDate()), super.getClientId(), String.valueOf(interestRate), String.valueOf(startingAmount),String.valueOf(term)};
        return data;
    }

    @Override
    public String toString() {
        
        return "A PLAZO - ID: "+String.valueOf(super.getId())+"  Tipo de moneda: "+ String.valueOf(super.getCurrency())+"  Fecha de apertura: "+ new SimpleDateFormat("dd-MM-yyyy").format(super.getOpeningDate()) +"  Tasa de interes: "+ String.valueOf(interestRate)+"  Monto inicial: "+ String.valueOf(startingAmount)+"  Plazo: "+String.valueOf(term);
    }

    /**
     * Se autoconvierte esta clase a array de bytes.
     * @return La clase convertida a array de bytes.
     */
    public byte [] toByteArray()
    {
        try
        {
             // Se hace la conversi�n usando un ByteArrayOutputStream y un
             // ObjetOutputStream.
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream (bytes);
            os.writeObject(this);
            os.close();
            return bytes.toByteArray();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Se convierte el array de bytes que recibe en un objeto DatoUdp.
     * @param bytes El array de bytes
     * @return Un DatoUdp.
     */
    public static TermAccount fromByteArray (byte [] bytes)
    {
        try
        {
            // Se realiza la conversi�n usando un ByteArrayInputStream y un
            // ObjectInputStream
            ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
            ObjectInputStream is = new ObjectInputStream(byteArray);
            TermAccount aux = (TermAccount)is.readObject();
            is.close();
            return aux;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    
}
