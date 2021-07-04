/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author LuisGa && Sebas
 */
public class Client {

    private final String clientId;
    private final String clientName;
    private String phoneNumber;
    private String address;

    public Client(String clientId, String clientName, String phoneNumber, String address) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] dataName() {
        String[] dataName = {"clientId", "clientName", "phoneNumber", "address"};
        return dataName;
    }

    public String[] data() {

        String[] data = {clientId, clientName, phoneNumber, address};
        return data;
    }

    @Override
    public String toString() {
        return "Cliente:" + "\n\t  Cédula:  " + clientId + "\n\t  Nombre:  " + clientName + "\n\t  Teléfono:  " + phoneNumber + "\n\t  Dirección:  " + address;
    }

}
