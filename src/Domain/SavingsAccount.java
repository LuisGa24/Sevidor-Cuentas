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
public final class SavingsAccount extends Account implements Serializable{
    
    private static final long serialVersionUID = 3258698714674442547L;
    private float interestRate;           //Tasa de interes *Puede cambiar.
    private char state;        //Almacena el estado de la cuenta (activa/inactiva)
    private float balance;

    public SavingsAccount(char currency, Date openingDate, float balance, String clientId, float interestRate) {
        super(currency, openingDate, clientId);
        this.interestRate = interestRate;
        this.balance = balance;
        this.state = 'A';
    }

    public String setInterestRate(float interestRate) {
        //Solo se puede cambiar la taza de interés cada 6 meses.
        this.interestRate = interestRate;

        return "tasa de interés actualizada";

    }

    public void setState(Character state) {
        
            this.state = state ;
       
    }

    public String deposit(float amount) {
        if (state == 'A') {
            this.balance = (this.balance + amount);
            return "Monto depositado con exito";
        } else {
            return "La transacción no se pudo realizar porque la cuenta está inactiva";
        }
    }

    public String withdrawal(float amount) {
        if (state == 'A') {
            if (this.balance >= amount) {
                this.balance = (this.balance - amount);
                return "Monto retirado con exito";
            } else {
                return "El monto solicitado supera el saldo actual";
            }
        } else {
            return "La transacción no se pudo realizar porque la cuenta está inactiva";
        }
    }

    public float getInterestRate() {
        return interestRate;
    }

    public char getState() {
        return state;
    }

    public float getBalance() {
        return balance;
    }
    
     public String[] dataName() {
        String[] dataName = {"id", "currency", "openingDate", "clientID", "interestRate", "state","balance"};
        return dataName;
    }

    public String[] data() {
        
        String[] data = {String.valueOf(super.getId()), String.valueOf(super.getCurrency()), new SimpleDateFormat("dd-MM-yyyy").format(super.getOpeningDate()), super.getClientId(), String.valueOf(interestRate), String.valueOf(state),String.valueOf(balance)};
        return data;
    }

    @Override
    public String toString() {
        
            return "AHORROS - ID: "+String.valueOf(super.getId())+"  Tipo de moneda: "+ String.valueOf(super.getCurrency())+"  Fecha de apertura: "+  new SimpleDateFormat("dd-MM-yyyy").format(super.getOpeningDate()) +"  Tasa de interes: "+ String.valueOf(interestRate)+"  Estado: "+ String.valueOf(state)+"  Saldo: "+String.valueOf(balance);
        
       
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
    public static SavingsAccount fromByteArray (byte [] bytes)
    {
        try
        {
            // Se realiza la conversi�n usando un ByteArrayInputStream y un
            // ObjectInputStream
            ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
            ObjectInputStream is = new ObjectInputStream(byteArray);
            SavingsAccount aux = (SavingsAccount)is.readObject();
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
