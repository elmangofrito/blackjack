
import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier
 */
public class client implements Runnable {

    Json paquete;
    public client() {
        run();
    }

    JComboBox ul;
    @Override
    public void run() {
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                ul=new JComboBox();
                
  //      while (true) {
            
            try {
                DatagramSocket clientSocket = new DatagramSocket(8050);
                clientSocket.receive(receivePacket);
                String modifiedSentence = new String(receivePacket.getData());
                addserver(modifiedSentence);
                clientSocket.close();
            } catch (SocketException ex) {
                Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    //}

    public void addserver(String msg ) {
        boolean in = false;
        
        
        for (int i = 0; i < ul.getItemCount(); i++) {
            if (ul.getItemAt(i).equals(msg)) {
                in = true;
            } 
        }
        if (!in) {
            ul.addItem(msg);
            System.out.println("agregado "+ul.getItemAt(0));
        }
        
        
    }
    
}
