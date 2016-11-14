import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Mark on 2016-11-13.
 */
public class RemoteKeyboard {

    private static JLabel curKey = new JLabel("");
    private static ConnectionHandler connectionHandler;
    static boolean connected = false;

    public static void main(String[] args) {

        try {
            int port = Integer.parseInt(JOptionPane.showInputDialog(null, "Port: "));

            JLabel ip = new JLabel(InetAddress.getLocalHost().getHostAddress()+":"+port);

            JFrame frame = new JFrame("RemoteKeyboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600,200);
            frame.addKeyListener(new KeyboardListener());
            curKey = new JLabel();
            curKey.setFont(new Font("TimesRoman", Font.BOLD, 100));
            curKey.setHorizontalAlignment(SwingConstants.CENTER);
            frame.add(curKey);
            frame.add(ip, BorderLayout.PAGE_END);
            frame.setVisible(true);


            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                curKey.setText("Waiting...");
                Socket socket = serverSocket.accept();
                connectionHandler = new ConnectionHandler(socket);
                (new Thread(connectionHandler)).start();
                curKey.setText("Waiting...");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

     static void pressed(int keyVal){
         if (connectionHandler!=null&&connected) {
             curKey.setText(KeyEvent.getKeyText(keyVal));
             connectionHandler.press(keyVal);
         }else{
             curKey.setText("Waiting...");
         }
    }

    static void released(int keyVal){
        if (connectionHandler!=null&&connected) {
            connectionHandler.release(keyVal);
        }else{
            curKey.setText("Waiting...");
        }
    }

}
