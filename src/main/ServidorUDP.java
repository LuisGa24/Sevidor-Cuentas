/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Jose
 */
import Domain.ClientRequests;
import java.io.*;
import java.net.*;
import java.text.ParseException;

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

                ClientRequests x = new ClientRequests(mensaje, socket, recibirPaquete.getAddress(), recibirPaquete.getPort());

                Thread t = new Thread(x);
                
                t.start();
                
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
