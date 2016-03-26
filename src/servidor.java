
import java.awt.Color;
import java.awt.Graphics;
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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import sun.java2d.pipe.DrawImage;

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
    int cont = 5;
    Socket[] clients = new Socket[4];
    int contClients;
    hilotcp hTCP[];
    JLabel cliente;
    DatagramSocket s;
    DatagramPacket dgram;
    Thread hiloUDP;
    HiloControl controlsocket;
    ServerSocket server;
    Jugador jugadores[] = new Jugador[5];
    Thread hiloTCP;
    int puerto_udp = 20050;
    int puerto_tcp = 20060;
    int puerto_mtc = 20070;
    String dir_mtc = "235.1.1.1";
    String dir_bro = "255.255.255.255";
    String mensaje, menString;
    JLabel fondo;
    public servidor(JFrame x) {
//-----------------jframe------------------------

        super("Blackjack-Server");
        setLayout(null);

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
     /*   fondo = new JLabel();
        fondo.setLocation(0, 0);
        fondo.setSize(getSize());
        fondo.setVisible(true);
        add(fondo);
        ImageIcon fot = new ImageIcon((getClass().getResource("/masimagenes/fondo.jpg")));
        ImageIcon icono = new ImageIcon(fot.getImage().getScaledInstance(fondo.getWidth(), fondo.getHeight(), java.awt.Image.SCALE_DEFAULT));
        fondo.setIcon(icono);
*/
        hiloUDP = new Thread(this);
        hiloUDP.start();
        controlsocket = new HiloControl();
        hiloTCP = new Thread(controlsocket);
        hiloTCP.start();
    }
//--------------------------udp-----------------------------------------------    

    @Override
    public void run() {
        try {

            byte msg[] = new byte[1024];

            s = new DatagramSocket();
            System.out.println("Servidor Activo");
            InetAddress ip = InetAddress.getByName(dir_bro);

            while (true) {
                paquete = new Json();
                try {

                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                String message = paquete.code_1("server-javkell", cont, 4 - contClients);
                System.out.println("tiempo " + cont);
                msg = message.getBytes();
                DatagramPacket paquete = new DatagramPacket(msg, msg.length, ip, puerto_udp);
                //   System.out.println("mensaje a enviar" + message);
                s.send(paquete);
                // System.out.println("mensaje enviado");
                cont--;
                if (contClients == 4 || cont == 0) {
                    jugadores[contClients] = new Jugador("Servidor", 0001);
                    contClients++;
                    try {

                        sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    new Juego();

                    hiloUDP.stop();
                    s.close();
                    hiloTCP.stop();
                    server.close();

                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addcliente(String nombre) {
        JButton cliButton=new JButton();
        cliButton.setSize(400, 100);
        cliButton.setLocation(100, (contClients+1)*100);
        add(cliButton);
        JLabel imgcliente=new JLabel();
        imgcliente.setBounds(150,40, 50 , 50);
        ImageIcon fot = new ImageIcon((getClass().getResource("/masimagenes/"+(contClients+1)+".png")));
        ImageIcon icono = new ImageIcon(fot.getImage().getScaledInstance(imgcliente.getWidth(), imgcliente.getHeight(), java.awt.Image.SCALE_DEFAULT));
        imgcliente.setIcon(icono);
        cliButton.add(imgcliente);
        cliButton.setText(nombre);
        
  /*      JLabel labelcliente = new JLabel(nombre);
        labelcliente.setBounds(220, 50, 100, 20);
        cliButton.add(labelcliente);
        labelcliente.setVisible(true);
    */    System.out.println("add hecho");
        repaint();

    }
//------------------------------tcp---------------------------------------    

    public class HiloControl implements Runnable {

        public HiloControl() {
            hTCP = new hilotcp[4];
            try {
                server = new ServerSocket(puerto_tcp);
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

        Socket cliente;

        private hilotcp(Socket client) {
            this.cliente = client;
        }

        public void run() {
            try {
                DataOutputStream outToClient = new DataOutputStream(cliente.getOutputStream());
                DataInputStream in = new DataInputStream(cliente.getInputStream());
                paquete = new Json();
                byte[] cli = new byte[1024];
                in.read(cli);
                clientSentence = new String(cli);
                nombre = paquete.deco_2(clientSentence.trim(), 1).toString();
                System.out.println("recibido: " + nombre);
                addcliente(nombre);
                int asigid = (int) Math.floor(Math.random() * (7376284 - 3242343) + 3242343);
                jugadores[contClients] = new Jugador(nombre, asigid);
                jugadores[contClients].cliente = this.cliente;
                mensaje = paquete.code_3(true, dir_mtc, asigid);
                //       System.out.println(mensaje);
                outToClient.write(mensaje.getBytes());

            } catch (IOException ex) {
                Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
//---------------------multicast y juego--------------------------------

    public class Juego implements Runnable {

        public Juego() {
            run();
        }

        @Override
        public void run() {
            Mazo mazojuego = new Mazo(3, true);
            try {
                MulticastSocket socket = new MulticastSocket();
                String aux = "";
                paquete = new Json();
                for (int i = 0; i < contClients; i++) {
                    aux = aux + jugadores[i].getNombre() + " " + jugadores[i].getId() + " ";
                }
                //     System.out.println(contClients+"  "+paquete.code_4(aux.trim()));
                byte[] b = paquete.code_4(aux.trim()).getBytes();
                //-------------presentacion del juego--------------------------
                dgram = new DatagramPacket(b, b.length, InetAddress.getByName(dir_mtc), puerto_mtc);
                socket.send(dgram);
                //-------------comienzo de ronda---------------------
                aux = "";
                paquete = new Json();
                for (int i = 0; i < contClients; i++) {
                    aux = aux + jugadores[i].getPuntaje() + " " + jugadores[i].getId() + " ";
                }
                System.out.println(paquete.code_5(aux.trim()));
                b = paquete.code_5(aux.trim()).getBytes();
                dgram = new DatagramPacket(b, b.length, InetAddress.getByName(dir_mtc), puerto_mtc);
                socket.send(dgram);
                Carta cartaaux;
                for (int i = 0; i < contClients - 1; i++) {
                    //-----------carta 1---------------------------------
                    cartaaux = mazojuego.sacarCarta();
                    System.out.println(cartaaux);
                    mensaje = cartaaux.getSNumber() + "" + cartaaux.getMySuit();
                    System.out.println(mensaje);
                    menString = paquete.code_9(jugadores[i].getId(), mensaje.trim());
                    b = menString.getBytes();
                    dgram = new DatagramPacket(b, b.length, InetAddress.getByName(dir_mtc), puerto_mtc);
                    socket.send(dgram);

                }
                
                for (int i = 0; i < contClients - 1; i++) {
                    DataOutputStream outToClient = new DataOutputStream(jugadores[i].cliente.getOutputStream());
                    DataInputStream in = new DataInputStream(jugadores[i].cliente.getInputStream());
                    //-----------carta 2---------------------------------
                    cartaaux = mazojuego.sacarCarta();
                    mensaje = cartaaux.getSNumber() + "" + cartaaux.getMySuit();
                    System.out.println(mensaje);
                   
                    menString = paquete.code_9(jugadores[i].getId(), mensaje.trim());
                    b = menString.getBytes();
                    dgram = new DatagramPacket(b, b.length, InetAddress.getByName(dir_mtc), puerto_mtc);
                    socket.send(dgram);

                }
            /*    for (int i = 0; i < contClients - 1; i++) {
                    DataOutputStream outToClient = new DataOutputStream(jugadores[i].cliente.getOutputStream());
                    DataInputStream in = new DataInputStream(jugadores[i].cliente.getInputStream());
                    mensaje = paquete.code_7(jugadores[i].getId());
                    System.out.println(mensaje);
                    outToClient.write(mensaje.getBytes());
                    b = new byte[2014];
                    in.read(b);
                    mensaje = null;
                    mensaje = new String(b);

                    if (paquete.deco_8(mensaje.trim(), 1).equals("true")) {
                        System.out.println("pide");
                    }
                }

                    
                    */
            } catch (IOException ex) {
                Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
