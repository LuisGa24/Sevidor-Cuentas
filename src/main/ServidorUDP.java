/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Jose
 */

import Domain.SavingsAccount;
import java.io.*;
import java.net.*;

public class ServidorUDP {

    private DatagramSocket socket;

    public ServidorUDP() {
        try { // crear objeto DatagramSocket para enviar y recibir paquetes

            socket = new DatagramSocket(6000); // utiliza el puerto 6000

        } catch (SocketException excepcionSocket) {
            System.exit(1);
        }
    }

    public void ejecutarServidor() {

        while (true) { // iterar infinitamente

            try {  // recibir paquete, mostrar su contenido, devolver copia al cliente             
                // establecer el paquete
                byte[] datos = new byte[1000];

                DatagramPacket recibirPaquete = new DatagramPacket(datos, datos.length);

                System.out.println("Esperando Conexión...");

                socket.receive(recibirPaquete); //esperar el paquete


               
                String mensaje = new String(recibirPaquete.getData(), 0, recibirPaquete.getLength());
                
                System.out.println(mensaje);
                
                
                System.out.println("\n\nRepitiendo datos al cliente");
                

                String mensaje2 = "luiyuiyiuuyui123";
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
    public static void main(String[] args) {
        ServidorUDP aplicacion = new ServidorUDP();
        aplicacion.ejecutarServidor();
        // TODO code application logic here
    }
}
