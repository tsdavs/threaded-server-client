import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread{

    private static final String ONE_HUNDRED_MEGABYTE_FILE = "100_megabyte_file.txt";

    private Socket socket;

    int count = 0;

    public Client(String file) {
        Thread t = new Thread(() -> {
          try {
              socket = new Socket("localhost", 61174);

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

        System.out.println("Reading/Sending");

        int len;

        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }

        System.out.println("Done");

        in.close();

        out.close();
    }

    public static void main(String[] args) {
        Thread[] threads = {
                new Client(ONE_HUNDRED_MEGABYTE_FILE),
                new Client(ONE_HUNDRED_MEGABYTE_FILE)
        };

        for(Thread t : threads){
            t.start();
        }
    }

}