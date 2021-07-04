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
public class accountMaintenance {

    LinkedList<Account> accountList;

    public accountMaintenance() {
        accountList = new LinkedList<>();
        loadAccounts();
    }

    public boolean addAccount(Account a) {
        if (accountList.add(a)) {
            saveAccounts();
            return true;
        }
        return false;
    }

    public LinkedList<Account> getAccountsByClientId(String clientId) {
        LinkedList<Account> clientAccounts = new LinkedList<>();

        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getClientId().equalsIgnoreCase(clientId)) {
                clientAccounts.add(accountList.get(i));
            }
        }
        return clientAccounts;
    }

    public boolean existAccount(int id) {

        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Account deleteAccount(int id) {

        Account a = null;

        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getId() == id) {
                a = accountList.get(i);
                accountList.remove(i);
            }
        }
        saveAccounts();
        return a;
    }

    public void saveAccounts() {
        FileXML fXML = new FileXML();

        if (!fXML.exist("SavingsAccount.xml")) { //Si el archivo no existe
            fXML.createXML("SavingsAccountXML", "SavingsAccount");
            if (!accountList.isEmpty()) {
//                writeAccounts();
            }
        } else {
            fXML.deleteFile("SavingsAccount");
            fXML.createXML("SavingsAccountXML", "SavingsAccount");
            if (!accountList.isEmpty()) {
//                writeAccounts();
            }
        }

        if (!fXML.exist("TermAccount.xml")) { //Si el archivo no existe
            fXML.createXML("TermAccountXML", "TermAccount");
            if (!accountList.isEmpty()) {
                writeAccounts();
            }
        } else {
            fXML.deleteFile("TermAccount");
            fXML.createXML("TermAccountXML", "TermAccount");
            if (!accountList.isEmpty()) {
                writeAccounts();
            }
        }
    }

    public void writeAccounts() {

        FileXML fXML = new FileXML();

        if (accountList.isEmpty()) {
            if (fXML.exist("SavingsAccount.xml")) {
                fXML.deleteFile("SavingsAccount");
            }
            if (fXML.exist("TermAccount.xml")) {
                fXML.deleteFile("TermAccount");
            }
        } else {

            for (int i = 0; i < accountList.size(); i++) {

                if (accountList.get(i) instanceof SavingsAccount) {

                    SavingsAccount a = (SavingsAccount) accountList.get(i);
                    try {

                        fXML.writeXML("SavingsAccount.xml", "SavingsAccount", a.dataName(), a.data());
                    } catch (TransformerException ex) {
                        Logger.getLogger(clientMaintenance.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(clientMaintenance.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(clientMaintenance.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                    TermAccount a = (TermAccount) accountList.get(i);
                    try {

                        fXML.writeXML("TermAccount.xml", "TermAccount", a.dataName(), a.data());
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
    }

    public void loadAccounts() {
        //Se encarga de rellenar las listas desde los XML
        //Se rellena la lista de estudiantes segun el xml Students.xml
        FileXML fXML = new FileXML();
        if (fXML.exist("SavingsAccount.xml")) {

            accountList = fXML.readXMLtoSAccountList("SavingsAccount");

        }
        if (fXML.exist("TermAccount.xml")) {

            LinkedList<Account> termAccountList = fXML.readXMLtoTAccountList("TermAccount");
            for (int i = 0; i < termAccountList.size(); i++) {
                accountList.add(termAccountList.get(i));
            }

        }

    }
}
