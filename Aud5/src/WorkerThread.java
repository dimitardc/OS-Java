import java.io.*;
import java.net.Socket;

public class WorkerThread extends Thread{

    private Socket socket = null;

    public WorkerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        PrintWriter pw = null;
        try{
            System.out.println("Connected:"+ socket.getInetAddress()+":"+socket.getPort());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String line = null;
            line = br.readLine();
            while (line!=null){
                System.out.printf("New message from %s:%d:%s",socket.getInetAddress(), socket.getPort(),line);
                pw.write(line);
                pw.flush();
                br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(pw!=null){
                pw.close();
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
