/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Jose
 */
import Domain.Account;
import Domain.SavingsAccount;
import Domain.TermAccount;
import Domain.accountMaintenance;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class ServidorUDP {

    private DatagramSocket socket;

    public ServidorUDP() {
        try { // crear objeto DatagramSocket para enviar y recibir paquetes

            socket = new DatagramSocket(6000); // utiliza el puerto 6000

        } catch (SocketException excepcionSocket) {
            System.exit(1);
        }
    }

    public void ejecutarServidor() throws ParseException {

        while (true) { // iterar infinitamente

            try {  // recibir paquete, mostrar su contenido, devolver copia al cliente             
                // establecer el paquete
                byte[] datos = new byte[1000];

                DatagramPacket recibirPaquete = new DatagramPacket(datos, datos.length);

                System.out.println("Esperando Conexión...");

                socket.receive(recibirPaquete); //esperar el paquete

                String mensaje = new String(recibirPaquete.getData(), 0, recibirPaquete.getLength());

                String[] packR = mensaje.split("#");

                accountMaintenance am = new accountMaintenance();

                String mensaje2 = "";//Mensaje de retorno

                if (packR.length == 1) {
                    System.out.println("Número de cedula");
                    LinkedList<Account> list = am.getAccountsByClientId(mensaje);
                    mensaje2 += list.size() + "/";

                    for (int i = 0; i < list.size(); i++) {
                        Account a = list.get(i);
                        mensaje2 += a.toString2();
                    }

                } else {
                    if (packR[0].equals("AHORROS")) {
                        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = format.parse(packR[3]);
                        SavingsAccount sa = new SavingsAccount(packR[2].charAt(0), date, Float.parseFloat(packR[6]), packR[7], Float.parseFloat(packR[4]));
                        sa.setId(Integer.parseInt(packR[1]));
                        accountMaintenance acm = new accountMaintenance();
                        acm.addAccount(sa);
                    } else {
                        if (packR[0].equals("A PLAZO")) {
                            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = format.parse(packR[3]);
                            TermAccount ta = new TermAccount(packR[2].charAt(0), date, packR[7], Float.parseFloat(packR[5]), Float.parseFloat(packR[4]), Integer.parseInt(packR[6]));
                            ta.setId(Integer.parseInt(packR[1]));
                            accountMaintenance acm = new accountMaintenance();
                            acm.addAccount(ta);
                        }
                    }
                }

                System.out.println("\n\nRepitiendo datos al cliente");

                byte[] datos2 = mensaje2.getBytes();

                DatagramPacket enviarPaquete = new DatagramPacket(datos2,
                        datos2.length, recibirPaquete.getAddress(),
                        recibirPaquete.getPort());

                System.out.println("\nPaquete Enviado\n");

                socket.send(enviarPaquete); // enviar paquete

            } catch (IOException excepcionES) {
                excepcionES.printStackTrace();
            }

        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        ServidorUDP aplicacion = new ServidorUDP();
        aplicacion.ejecutarServidor();
        // TODO code application logic here
    }
}
