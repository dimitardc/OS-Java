import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread{

    private int port;
    public TCPServer(int port){
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("TCP is starting");
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socker server failed to start");
            return;
        }
        System.out.println("TCP started");
        System.out.println("Waiting for connections");
        while(true){
            Socket socket = null;
            try{
                socket = serverSocket.accept();
                new WorkerThread(socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TCPServer server = new TCPServer(9000);
        server.start();
    }
}
