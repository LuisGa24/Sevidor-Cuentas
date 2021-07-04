/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author LuisGa
 */
public final class clientMaintenance {

    LinkedList<Client> clientList;

    public clientMaintenance() {
        clientList = new LinkedList<>();
        loadClients();
    }

    public boolean addClient(Client c) {
        if (clientList.add(c)) {
            saveClients();
            return true;
        }
        return false;
    }

    public boolean existClient(String clientId) {

        for (int i = 0; i < clientList.size(); i++) {
            if (clientList.get(i).getClientId().equalsIgnoreCase(clientId)) {
                return true;
            }
        }
        return false;
    }

    public Client getClient(String id) {
        for (int i = 0; i < clientList.size(); i++) {
            if (clientList.get(i).getClientId().equalsIgnoreCase(id)) {
                return clientList.get(i);
            }
        }
        return null;
    }

    public void saveClients() {
        FileXML fXML = new FileXML();

        if (!fXML.exist("Client.xml")) { //Si el archivo no existe
            fXML.createXML("ClientXML", "Client");
            if (!clientList.isEmpty()) {
                writeClients();
            }
        } else {
            fXML.deleteFile("Client");
            fXML.createXML("ClientXML", "Client");
            if (!clientList.isEmpty()) {
                writeClients();
            }
        }
    }

    public void writeClients() {

        FileXML fXML = new FileXML();

        if (clientList.isEmpty()) {
            if (fXML.exist("Client.xml")) {
                fXML.deleteFile("Client");
            }
        } else {

            for (int i = 0; i < clientList.size(); i++) {
                Client c = (Client) clientList.get(i);
                try {

                    fXML.writeXML("Client.xml", "Client", c.dataName(), c.data());
                } catch (TransformerException ex) {
                    Logger.getLogger(clientMaintenance.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(clientMaintenance.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(clientMaintenance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void loadClients() {
        //Se encarga de rellenar las listas desde los XML
        //Se rellena la lista de estudiantes segun el xml Students.xml
        FileXML fXML = new FileXML();
        if (fXML.exist("Client.xml")) {

            clientList = fXML.readXMLtoClientList("Client");

        }

    }
}
