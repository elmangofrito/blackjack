
import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
    int cont = 120;
    Socket[] clients = new Socket[4];
    int contClients;
    hilotcp hTCP[];
    JLabel cliente;
    DatagramSocket s;
    Thread hiloUDP;
    HiloControl controlsocket;
    ServerSocket server;

    public servidor(JFrame x) {
//-----------------jframe------------------------

        super("Blackjack-Server");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        hiloUDP = new Thread(this);
        hiloUDP.start();
        controlsocket = new HiloControl();
        Thread hiloTCP = new Thread(controlsocket);
        hiloTCP.start();
    }
//--------------------------udp-----------------------------------------------    

    @Override
    public void run() {
        try {
            int PUERTO = 20050;
            byte msg[] = new byte[1024];

            s = new DatagramSocket();
            System.out.println("Servidor Activo");
            InetAddress ip = InetAddress.getByName("255.255.255.255");

            while (true) {
                paquete = new Json();
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                String message = paquete.code_1("server-javkell", cont, 4 - contClients);
                System.out.println(message);
                msg = message.getBytes();
                DatagramPacket paquete = new DatagramPacket(msg, msg.length, ip, PUERTO);
                //   System.out.println("mensaje a enviar" + message);
                s.send(paquete);
                // System.out.println("mensaje enviado");
                cont--;
            }
        } catch (SocketException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addcliente(String nombre) {
        JLabel cliente = new JLabel(nombre);
        cliente.setBounds(50, 50 * (contClients + 1), 100, 20);
        add(cliente);
        cliente.setVisible(true);
        System.out.println("add hecho");
        repaint();

    }
//------------------------------tcp---------------------------------------    

    public class HiloControl implements Runnable {

        public HiloControl() {
            hTCP = new hilotcp[4];
            try {
                server = new ServerSocket(20060);
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
                    hTCP[contClients] = new hilotcp(clients[contClients]);
                    hTCP[contClients].run();
                    contClients++;

                } catch (IOException ex) {
                    Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public class hilotcp implements Runnable {

        String clientSentence, nombre;
        String mensaje;
        ServerSocket welcomeSocket;
        Socket cliente;

        private hilotcp(Socket client) {
            this.cliente = client;
        }

        @Override
        public void run() {
            try {
                DataOutputStream outToClient = new DataOutputStream(cliente.getOutputStream());
                DataInputStream in = new DataInputStream(cliente.getInputStream());
                paquete = new Json();
                byte[] cli = new byte[1024];
                in.read(cli);
                clientSentence = new String(cli);
                System.out.println(clientSentence);
                nombre = paquete.deco_2(clientSentence.trim(), 1).toString();
                System.out.println("recibido: " + nombre);
                addcliente(nombre);
                mensaje = paquete.code_3(true, "239.237.55.33", contClients);
                System.out.println(mensaje);
                outToClient.write(mensaje.getBytes());
                mensaje = null;
                paquete = new Json();
                mensaje = paquete.code_4("javier 01 kelly 02 jesus 03");
                outToClient.write(mensaje.getBytes());
                
                System.out.println(mensaje);
                if (contClients == 3) {
                    hiloUDP.stop();
                    s.close();

                }
            } catch (IOException ex) {
                Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    
    public class hiloMulticastServer implements Runnable{

    @Override
    public void run() {
        

        try {
            MulticastSocket socket = new MulticastSocket();
            
            
            byte[] b = "Martin Gigena".getBytes();
            DatagramPacket dgram;
            
            dgram = new DatagramPacket(b, b.length,InetAddress.getByName("235.1.1.1"), 4000);
            
            System.err.println("Enviando " + b.length + " bytes a " +
                    dgram.getAddress() + ':' + dgram.getPort());
            while(true) {
                System.err.print(".");
                socket.send(dgram);
               
            }
        } catch (IOException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

       
    }

}
    
}
