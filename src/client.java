
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import sun.awt.windows.ThemeReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author javier
 */
public class client extends JFrame implements Runnable {

    Json paquete;
    int puerto_udp = 20050;
    int puerto_tcp = 20060;
    int puerto_mtc = 20070;
    String dir_mtc;
    JComboBox ul;
    JButton aceptar;
    String host;
    DataInputStream in = null;
    DataOutputStream out = null;
    byte[] cli = new byte[1024];
    DatagramSocket clientSocket;
    Thread hilo;
    Jugador jugadores[];
    Socket cliente;
    String msgs;
    int ID,iID;
    Jugador yo = new Jugador(null, 0);
    //-------------------------------------------------------------------

    public client() {

        super("Blackjack-Cliente");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        paquete = new Json();

        ul = new JComboBox();
        ul.setBounds(35, 60, 200, 20);
        add(ul);
        ul.setVisible(true);

        aceptar = new JButton("Conectar");
        aceptar.setBounds(80, 100, 100, 50);
        add(aceptar);
        aceptar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                hilo.stop();
                clientSocket.close();
                System.out.println(host);
                hilotcp obj = new hilotcp();
                Thread hiThread = new Thread(obj);
                hiThread.start();
            }
        });

        hilo = new Thread(this);
        hilo.start();
    }
//---------------server udp-----------------------------------------

    @Override
    public void run() {
        try {

            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            byte[] receiveData;
            DatagramPacket receivePacket;
            clientSocket = new DatagramSocket(puerto_udp);
            Json x = new Json();
            String modifiedSentence;

            receiveData = new byte[1024];
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            modifiedSentence = new String(receivePacket.getData()).trim();
            String ip = "" + receivePacket.getAddress();
            String[] separar = ip.split("/");
            host = separar[1];

            if (modifiedSentence != null) {
                addserver(x.deco_1(modifiedSentence, 1).toString());
            }

        } catch (SocketException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addserver(String msg) {
        boolean bandera = false;
        for (int i = 0; i < ul.getItemCount(); i++) {
            if (ul.getItemAt(i).equals(msg)) {
                bandera = true;
            }
        }
        if (!bandera) {
            ul.addItem(msg);
            System.out.println("agregado " + msg);
        }
        repaint();
    }
//------------------------server tcp------------------------------------

    public class hilotcp implements Runnable {

        public hilotcp() {

        }

        @Override
        public void run() {
            try {

                Jugador[] jugadores = new Jugador[3];;

                System.out.println(host);
                cliente = new Socket(host, puerto_tcp);
                System.out.println("conecto");
                out = new DataOutputStream(cliente.getOutputStream());
                in = new DataInputStream(cliente.getInputStream());
                System.out.println("Se conecto.");
                String msgs = paquete.code_2("Cliente-jagg");
                out.write(msgs.getBytes());
                System.out.println("envie " + msgs);
                in.read(cli);
                msgs = new String(cli);
                ID = Integer.parseInt(paquete.deco_3(msgs.trim(), 3).toString());
                dir_mtc = paquete.deco_3(msgs.trim(), 2).toString();
                cli = new byte[1024];
                new juego_cliente();

            } catch (IOException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public class juego_cliente implements Runnable {

        public juego_cliente() {
            run();
        }

        public void run() {
            boolean band = true;
            try {
                byte[] b = new byte[1024];
                DatagramPacket dgram = new DatagramPacket(b, b.length);
                MulticastSocket socket = new MulticastSocket(puerto_mtc);

                while (true) {
                    if (band) {
                        band = false;
                        socket.joinGroup(InetAddress.getByName(dir_mtc));
                        //recibir mensaje de presentacion
                        socket.receive(dgram);
                        System.out.println(new String(dgram.getData()));
                        //guardando la cantidad de jugadores que se envio en el paq 4  
                        int cantjug = Integer.parseInt(paquete.deco_4(new String(dgram.getData()).trim(), 0).toString());
                        jugadores = new Jugador[cantjug];
                        for (int i = 0; i < jugadores.length; i++) {
                            String aux = paquete.deco_4(new String(dgram.getData()).trim(), i + 1).toString().trim();
                            String[] separar = aux.split(" ");
                            jugadores[i] = new Jugador(separar[0], Integer.parseInt(separar[1]));
                            if(Integer.parseInt(separar[1])==ID){
                                iID=i;
                            }
                            System.out.println("cliente creado: " + jugadores[i].getNombre());
                        }
                        b = new byte[1024];
                        dgram = new DatagramPacket(b, b.length);
                        socket.receive(dgram);

                        for (int i = 0; i < jugadores.length; i++) {
                            String aux = paquete.deco_5(new String(dgram.getData()).trim(), i + 1).toString().trim();
                            String[] separar = aux.split(" ");
                            for (int j = 0; j < jugadores.length; j++) {
                                if (jugadores[j].getId() == Integer.parseInt(separar[1])) {
                                    jugadores[j].setPuntaje(Integer.parseInt(separar[0]));
                                    
                                }
                            }
                        }

                    } else {
                        b = new byte[1024];
                        dgram = new DatagramPacket(b, b.length);
                        socket.receive(dgram);
                        msgs = new String(dgram.getData());

                        switch (paquete.getCode(msgs.trim())) {
                            case 9:
                                if (Integer.parseInt((String) paquete.deco_9(msgs.trim(), 1)) == ID) {
                                    System.out.println(paquete.deco_9(msgs.trim(), 1) + " " + paquete.deco_9(msgs.trim(), 2));
                        /*            Carta aux=new Carta(,paquete.deco_9(msgs.trim(), 2).toString().charAt(1));
                                    jugadores[iID].addCarta((Carta)paquete.deco_9(msgs.trim(), 2));
                          */      }
                                break;
                            default:
                                throw new AssertionError();
                        }

                    }

                }
            } catch (IOException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
