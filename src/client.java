
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
    int puerto = 20050;
    JComboBox ul;
    JButton aceptar;
    String host;
    DataInputStream in = null;
    DataOutputStream out = null;
    byte[] cli = new byte[1024];
    DatagramSocket clientSocket;
    Thread hilo;

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
            clientSocket = new DatagramSocket(puerto);
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
                String msg;
                Jugador[] jugadores = new Jugador[3];;
                Jugador yo = new Jugador(null, 0);
                System.out.println(host);
                Socket cliente = new Socket(host, 20060);
                System.out.println("conecto");
                out = new DataOutputStream(cliente.getOutputStream());
                in = new DataInputStream(cliente.getInputStream());
                System.out.println("Se conecto.");
                String msgs = paquete.code_2("Cliente-javkell");
                out.write(msgs.getBytes());
                System.out.println("envie " + msgs);
                while (true) {
                    out = new DataOutputStream(cliente.getOutputStream());
                    in = new DataInputStream(cliente.getInputStream());

                    cli = new byte[1024];
                    in.read(cli);
                    msgs = new String(cli);
                    System.out.println(msgs);
                    switch (paquete.getCode(msgs.trim())) {
                        case 3:
                            if (paquete.deco_3(msgs.trim(), 1).toString().compareTo("true") == 0) {
                                yo = new Jugador("Cliente-javkell", Integer.parseInt(paquete.deco_3(msgs.trim(), 3).toString()));
                            }
                            break;
                        case 4:

                            paquete.deco_4(msgs.trim(), 1);

                            /*      for (int i = 0; i < jugadores.length; i++) {
                             jugadores[i] = new Jugador(paquete.deco_4(msgs.trim(), i + 1).toString(),Integer.parseInt( paquete.deco_4(msgs, i + 2).toString()));
                             }
                             */ break;
                        case 5:
                            for (int i = 0; i < jugadores.length; i++) {
                                jugadores[i].setPuntaje(Integer.parseInt(paquete.deco_5(msgs.trim(), +1).toString()));
                            }
                            break;
                        case 7:
                            if (yo.getId() == (Integer.parseInt(paquete.deco_7(msgs.trim(), 1).toString()))) {
                                out.write(paquete.code_8(true).getBytes());
                            }
                            break;

                        default:
                            throw new AssertionError();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public class hiloMulticastClient implements Runnable {

        public void run() {

            try {
                byte[] b = new byte[100];
                DatagramPacket dgram = new DatagramPacket(b, b.length);
                MulticastSocket socket = new MulticastSocket(4000);
                socket.joinGroup(InetAddress.getByName("235.1.1.1"));

                while (true) {
                    socket.receive(dgram); // Se bloquea hasta que llegue un datagrama
                    System.err.println("Recivido " + dgram.getLength()
                            + " bytes de " + dgram.getAddress() + " " + new String(dgram.getData()));

                }
            } catch (IOException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
