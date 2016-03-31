
import java.awt.Color;
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
import java.lang.Enum;
import static java.lang.Thread.sleep;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    int ID, iID;
    Jugador yo = new Jugador(null, 0);
    JLabel fondo;
    //-------------------------------------------------------------------

    public client() {

        super("Blackjack-Cliente");
        setLayout(null);
        this.iniciarcomponentes();

        aceptar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                clientSocket.close();
                hilotcp obj = new hilotcp();
                Thread hiThread = new Thread(obj);
                hiThread.start();
                
                
                System.out.println(host);
                
            }
        });

        hilo = new Thread(this);
        hilo.start();
    }

    public void iniciarcomponentes() {
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

    }

    public void colocarcarta(String suit, int i) {

        switch (suit) {
            case "c":
                jugadores[i].labelcliente = new JLabel(new ImageIcon(getClass().getResource("/imagens/Corazon/" + jugadores[i].getMano()[jugadores[i].getNumCartas() - 1].getSNumber().toString().trim() + ".png")));
                break;
            case "t":
                jugadores[i].labelcliente = new JLabel(new ImageIcon(getClass().getResource("/imagens/Trebol/" + jugadores[i].getMano()[jugadores[i].getNumCartas() - 1].getSNumber().toString().trim() + ".png")));
                break;
            case "d":
                jugadores[i].labelcliente = new JLabel(new ImageIcon(getClass().getResource("/imagens/Diamante/" + jugadores[i].getMano()[jugadores[i].getNumCartas() - 1].getSNumber().toString().trim() + ".png")));
                break;
            case "p":
                jugadores[i].labelcliente = new JLabel(new ImageIcon(getClass().getResource("/imagens/Pica/" + jugadores[i].getMano()[jugadores[i].getNumCartas() - 1].getSNumber().toString().trim() + ".png")));
                break;

            default:
                throw new AssertionError();
        }
        switch (i) {
            case 0:
                jugadores[i].labelcliente.setLocation(70 - (15 * jugadores[iID].getNumCartas()), 300);
                jugadores[i].Mano.setLocation(70, 400);
                jugadores[i].puntos.setLocation(70, 410);
                break;
            case 1:
                jugadores[i].labelcliente.setLocation(250 - (15 * jugadores[iID].getNumCartas()), 450);
                jugadores[i].Mano.setLocation(250, 550);
                jugadores[i].puntos.setLocation(250, 560);
                break;
            case 2:
                jugadores[i].labelcliente.setLocation(450 - (15 * jugadores[iID].getNumCartas()), 450);
                jugadores[i].Mano.setLocation(450, 550);
                jugadores[i].puntos.setLocation(450, 560);
                break;
            case 3:
                jugadores[i].labelcliente.setLocation(660 - (15 * jugadores[iID].getNumCartas()), 300);
                jugadores[i].Mano.setLocation(660, 400);
                jugadores[i].puntos.setLocation(660, 410);
                break;
            default:
                throw new AssertionError();
        }

        jugadores[i].labelcliente.setSize(70, 90);
        jugadores[i].Mano.setSize(60, 15);
        jugadores[i].puntos.setSize(60, 15);
        jugadores[i].Mano.setForeground(Color.WHITE);
        jugadores[iID].Mano.setForeground(Color.RED);
        jugadores[i].puntos.setForeground(Color.WHITE);
        jugadores[iID].puntos.setForeground(Color.RED);
        setVisible(true);
        jugadores[i].Mano.setVisible(true);
        jugadores[i].puntos.setVisible(true);
        fondo.add(jugadores[i].Mano);
        fondo.add(jugadores[i].puntos);
        fondo.add(jugadores[i].labelcliente);

        fondo.repaint();
    }

    public void comenzarjuego() {
        setSize(800, 600);
        ul.setVisible(false);
        aceptar.setVisible(false);
        fondo = new JLabel();
        fondo.setLocation(0, 0);
        fondo.setSize(getSize());
        fondo.setVisible(true);
        add(fondo);
        ImageIcon fot = new ImageIcon((getClass().getResource("/masimagenes/fondo.jpg")));
        ImageIcon icono = new ImageIcon(fot.getImage().getScaledInstance(fondo.getWidth(), fondo.getHeight(), java.awt.Image.SCALE_DEFAULT));
        fondo.setIcon(icono);

    }

    public void limpiarjuego() {
        setSize(800, 600);
        fondo.removeAll();
        for (int i = 0; i < jugadores.length; i++) {
            fondo.add(jugadores[i].Mano);
            fondo.add(jugadores[i].puntos);

        }

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
                String msgs = paquete.code_2("Cliente-Javierkelly");
                out.write(msgs.getBytes());
                System.out.println("envie " + msgs);
                in.read(cli);
                msgs = new String(cli);
                
                ID = Integer.parseInt(paquete.deco_3(msgs.trim(), 3).toString());
                dir_mtc = paquete.deco_3(msgs.trim(), 2).toString();
                System.out.println(dir_mtc);
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
            byte[] b = new byte[1024];

            try {
                MulticastSocket socket = new MulticastSocket(puerto_mtc);
                out = new DataOutputStream(cliente.getOutputStream());
                in = new DataInputStream(cliente.getInputStream());

                DatagramPacket dgram = new DatagramPacket(b, b.length);

                band = false;
                socket.joinGroup(InetAddress.getByName(dir_mtc));
                System.out.println("creado");    
                //recibir mensaje de presentacion
                socket.receive(dgram);
                System.out.println(new String(dgram.getData()));
                //guardando la cantidad de jugadores que se envio en el paq 4  
                int cantjug = Integer.parseInt(paquete.deco_4(new String(dgram.getData()).trim(), 0).toString());
                jugadores = new Jugador[cantjug];
                System.out.println(new String(dgram.getData()));
                for (int i = 0; i < jugadores.length; i++) {
                    String aux = paquete.deco_4(new String(dgram.getData()).trim(), i + 1).toString().trim();
                    String[] separar = aux.split(" ");
                    jugadores[i] = new Jugador(separar[0], Integer.parseInt(separar[1]));
                    jugadores[i].Mano = new JLabel("Mano: " + jugadores[i].getSuma());
                    jugadores[i].puntos = new JLabel("Puntos: " + jugadores[i].getPuntaje());
                    if (Integer.parseInt(separar[1]) == ID) {
                        iID = i;
                    }

                }
                comenzarjuego();
                while (true) {
                    b = new byte[1024];
                    dgram = new DatagramPacket(b, b.length);
                    socket.receive(dgram);
                    msgs = new String(dgram.getData());
                    System.out.println("jugando " + msgs);
                    try {

                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    switch (paquete.getCode(msgs.trim())) {
                        case 5:
                            limpiarjuego();
                            for (int i = 0; i < jugadores.length; i++) {
                                jugadores[i].vaciarMano();
                                String aux = paquete.deco_5(new String(dgram.getData()).trim(), i + 1).toString().trim();
                                String[] separar = aux.split(" ");
                                for (int j = 0; j < jugadores.length; j++) {
                                    if (jugadores[j].getId() == Integer.parseInt(separar[1])) {
                                        jugadores[j].setPuntaje(Integer.parseInt(separar[0]));
                                        System.out.println("cliente creado: " + jugadores[i].getNombre());
                                    }
                                }
                            }

                            break;

                        case 9:

                            // System.out.println(paquete.deco_9(msgs.trim(), 1) + " " + paquete.deco_9(msgs.trim(), 2));
                            Suit x = null;
                            // System.out.println((paquete.deco_9(msgs.trim(), 2)).toString());
                            String msg = paquete.deco_9(msgs.trim(), 2).toString();
                            String suit = msg.charAt(msg.length() - 1) + "";
                            x.valueOf(suit);
                            String num;
                            if (msg.length() <= 2) {
                                num = msg.toString().charAt(0) + "";
                            } else {
                                num = msg.toString().charAt(0) + "";
                                num = num + msg.toString().charAt(1);
                            }
                            for (int i = 0; i < cantjug; i++) {
                                if (paquete.getID(msgs.trim()) == jugadores[i].getId()) {
                                    Carta aux = new Carta(x, num);
                                    jugadores[i].addCarta(aux);
                                    colocarcarta(suit, i);
                                    jugadores[i].Mano.setText("Mano: " + jugadores[i].getSuma());
                                    fondo.repaint();
                                } else {
                                }
                            }

                            System.out.println(jugadores[iID].getSuma());
                            if (Integer.parseInt((String) paquete.deco_9(msgs.trim(), 1)) == ID) {
                                if (jugadores[iID].getNumCartas() > 1) {
                                    if (jugadores[iID].getSuma() < 17) {
                                        System.out.println("tengo menos de 17 " + jugadores[iID].getSuma());

                                        in.read(cli);
                                        msgs = new String(cli);

                                        System.out.println("oferta recibida " + jugadores[iID]);
                                        if (jugadores[iID].getSuma() < 21) {
                                            System.out.println("oferta aceptada");
                                            msgs = paquete.code_8(true);
                                            System.out.println(msgs);
                                            out.write(msgs.getBytes());
                                            break;

                                        }
                                    } else {
                                        System.out.println("oferta denegada");
                                        msgs = paquete.code_8(false);
                                        out.write(msgs.getBytes());
                                        break;
                                    }
                                }
                            }

                            break;

                        case 10:
                            System.out.println("estadisticas recibidas");
                            Object msgss = paquete.deco_10(msgs.trim(), 2);
                            System.out.println(msgss);
                            break;
                        default:
                            throw new AssertionError();
                    }
                    System.out.println("puntaje " + jugadores[iID].getSuma());
                }

            } catch (IOException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
