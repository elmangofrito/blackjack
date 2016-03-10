
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
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
public class client extends Thread {

    Json paquete;
    int puerto = 20050;

    public client() {
        run();
    }
    JComboBox ul;

    @Override
    public void run() {
        try {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            byte[] receiveData;
            DatagramPacket receivePacket;
            ul = new JComboBox();
            DatagramSocket clientSocket = new DatagramSocket(puerto);
            Json x = new Json();
            String modifiedSentence;
            while (true) {
                receiveData = new byte[1024];
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                modifiedSentence = new String(receivePacket.getData()).trim();

                if (modifiedSentence != null) {
                    addserver(x.deco_1(modifiedSentence, 1).toString());
                }

            }
        } catch (SocketException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addserver(String msg) {
        boolean bandera =false;
        for (int i = 0; i < ul.getItemCount(); i++) {
            if (ul.getItemAt(i).equals(msg)) {
                bandera = true;
            }
        }
        if (!bandera) {
            ul.addItem(msg);
            System.out.println("agregado " + msg);
        }
        
    }
    Socket cliente=null;
    String host;
    DataInputStream in=null;
    DataOutputStream out=null;
    public class hilotcp extends Thread {

        public hilotcp() {
            run();
        }

        @Override
        public void run() {

            String msg;
            try {
                InetAddress address;

                cliente = new Socket(host, 20060);

                out = new DataOutputStream(cliente.getOutputStream());

                System.out.println("Se conecto.");

                String msgs = "CLIENTE-ANGESUS";

                in = new DataInputStream(cliente.getInputStream());

                address = InetAddress.getLocalHost();
            } catch (IOException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
}