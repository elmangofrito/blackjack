
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author javier
 */
public class servidor extends Thread {

    @Override
    public void run() {
        try {
            int PUERTO = 8050;
            
            byte msg[] = new byte[1024];
            
//Creamos el socket UDP del servidor en el pueto asociado
            DatagramSocket s = new DatagramSocket();
            System.out.println("Servidor Activo");
            InetAddress ip=InetAddress.getByName("localhost");
            while (true) {
               
//se prepara el mensaje 
                String message = "Servidor-Ap1";
                msg = message.getBytes();
                
//se crea el datagrama que contendrá al mensaje
                DatagramPacket paquete = new DatagramPacket(msg, msg.length,ip ,8050);
                
//se le envia al cliente
                System.out.println("mensaje a enviar" + msg);
                s.send(paquete);
                System.out.println("mensaje enviado");
            }
        } catch (SocketException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public servidor() {
        System.out.println("hola mundo");
        this.run();
    }
}
