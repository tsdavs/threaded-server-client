import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread{

    private static final String ONE_HUNDRED_MEGABYTE_FILE = "test.txt";

    private Socket socket;

    public Client(String host, int port, String file) {
        Thread t = new Thread(() -> {
          try {
              socket = new Socket(host, port);
              sendFile(file);
          } catch (Exception e) {
              e.printStackTrace();
          }
        });
        t.start();
    }

    public void sendFile(String file) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        FileInputStream in = new FileInputStream(file);

        byte[] buffer = new byte[file.length()];

        System.out.println("Reading/Writing");

        while (in.read(buffer) > 0) {
            out.write(buffer);
        }

        System.out.println("Done");

        in.close();
        out.close();
    }

    public static void main(String[] args) {
        Thread[] threads = {
                new Client("localhost", 61174, ONE_HUNDRED_MEGABYTE_FILE),
                new Client("localhost", 61174, ONE_HUNDRED_MEGABYTE_FILE)
        };

        for(Thread t : threads){
            t.start();
        }
    }

}