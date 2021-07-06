/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastián Navarro Martínez
 */
public class ClientRequests implements Runnable {

    private Object mensaje;
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    public ClientRequests(Object mensaje, DatagramSocket socket, InetAddress address, int port) {
        this.mensaje = mensaje;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }



    @Override
    public void run() {
        String[] packR = String.valueOf(mensaje).split("#");

        accountMaintenance am = new accountMaintenance();

        String mensaje2 = "";//Mensaje de retorno

        if (packR.length == 1) {
            LinkedList<Account> list = am.getAccountsByClientId((String) mensaje);
            mensaje2 += list.size() + "/";

            for (int i = 0; i < list.size(); i++) {
                Account a = list.get(i);
                mensaje2 += a.toString2();
            }
        } else {
            if (packR[0].equals("AHORROS")) {
                try {
                    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = format.parse(packR[3]);
                    SavingsAccount sa = new SavingsAccount(packR[2].charAt(0), date, Float.parseFloat(packR[6]), packR[7], Float.parseFloat(packR[4]));
                    //sa.setId(Integer.parseInt(packR[1]));
                    accountMaintenance acm = new accountMaintenance();
                    acm.addAccount(sa);
                } catch (ParseException ex) {
                    Logger.getLogger(ClientRequests.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (packR[0].equals("A PLAZO")) {
                    try {
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = format.parse(packR[3]);
                        TermAccount ta = new TermAccount(packR[2].charAt(0), date, packR[7], Float.parseFloat(packR[5]), Float.parseFloat(packR[4]), Integer.parseInt(packR[6]));
                        //ta.setId(Integer.parseInt(packR[1]));
                        accountMaintenance acm = new accountMaintenance();
                        acm.addAccount(ta);
                    } catch (ParseException ex) {
                        Logger.getLogger(ClientRequests.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        System.out.println("\n\nRepitiendo datos al cliente");
        byte[] datos2 = mensaje2.getBytes();

        DatagramPacket enviarPaquete = new DatagramPacket(datos2,
                datos2.length, address,port);

        System.out.println("\nPaquete Enviado\n");
        
        try {
            socket.send(enviarPaquete); // enviar paquete
        } catch (IOException ex) {
            Logger.getLogger(ClientRequests.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

