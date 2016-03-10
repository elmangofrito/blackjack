
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author javier
 */
public class servidor extends JFrame implements Runnable {

    Json paquete;

    @Override
    public void run() {
        try {
            int PUERTO = 20050;
            byte msg[] = new byte[1024];

//Creamos el socket UDP del servidor en el pueto asociado
            DatagramSocket s = new DatagramSocket();
            System.out.println("Servidor Activo");
            InetAddress ip = InetAddress.getByName("localhost");
            paquete = new Json();
            while (true) {
                String message = paquete.code_1("server-javkell", "50", "4");
                msg = message.getBytes();
                DatagramPacket paquete = new DatagramPacket(msg, msg.length, ip, PUERTO);
                //   System.out.println("mensaje a enviar" + message);
                s.send(paquete);
                // System.out.println("mensaje enviado");
            }
        } catch (SocketException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public servidor() {
        Thread hiloUDP = new Thread(this);
        hiloUDP.start();
       // hilotcp servertcp = new hilotcp();
      //  Thread hiloTCP = new Thread(servertcp);
      //  hiloTCP.start();
        HiloControl controlsocket=new HiloControl();
        Thread hiloTCP = new Thread(controlsocket);
        hiloTCP.start();
    }
    Socket[] clients = new Socket[4];
    int contClients;
    hilotcp hTCP[];
    public class HiloControl implements Runnable{
        ServerSocket server;
        
        public HiloControl() {
            hTCP=new hilotcp[4];
            try {
                this.server = new ServerSocket(20060);
            } catch (IOException ex) {
                Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        @Override
        public void run() {
            while (true) {
                try {
                    clients[contClients] = server.accept();
                    System.out.println("Se conecto alguien");
                } catch (IOException ex) {
                    Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                hTCP[contClients] = new hilotcp(clients[contClients]);
                hTCP[contClients].run();
                contClients++;
            }
        }
    }

    public class hilotcp implements Runnable {

        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket;
        Socket cliente;

        private hilotcp(Socket client) {
             this.cliente=client;           
        }

        @Override
        public void run() {
            try {
                //welcomeSocket = new ServerSocket(20060);
               // Socket connectionSocket = cliente.accept();
            //    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(cliente.getOutputStream());
                DataInputStream in = new DataInputStream(cliente.getInputStream());
                byte[] b = new byte[1024];
                in.read(b);
                clientSentence = new String(b);

                System.out.println("Received: " + clientSentence);
                capitalizedSentence = clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitalizedSentence);
            } catch (IOException ex) {
                Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
