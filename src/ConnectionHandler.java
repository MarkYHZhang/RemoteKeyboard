import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Mark on 2016-11-13.
 */
public class ConnectionHandler implements Runnable{

    private volatile Socket socket;
    private volatile Queue<String> keyQueue = new LinkedList<>();
    private volatile int sizeOfQueue = 0;
    private volatile boolean run = true;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        RemoteKeyboard.connected = true;
    }

    @Override
    public void run() {
        try {
            System.out.println(socket.toString());
            PrintStream out = new PrintStream(socket.getOutputStream());
            while (run) {
                if (sizeOfQueue > 0) {
                    System.out.println(keyQueue);
                    out.println(keyQueue.poll());
                    if (out.checkError()){
                        socket.close();
                        out.close();
                        run = false;
                        RemoteKeyboard.connected = false;
                        System.out.println("Disconnected");
                        break;
                    }
                    sizeOfQueue--;
                }
            }
        }catch (IOException e){
            System.out.println("FUCK YA");
            e.printStackTrace();
        }
    }

    void press(int key){
        keyQueue.add("P,"+key);
        sizeOfQueue = keyQueue.size();
    }

    void release(int key){
        keyQueue.add("R,"+key);
        sizeOfQueue = keyQueue.size();
    }
}
