import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private ServerSocket serverSocket;

    private Server()
    {
        try {
            serverSocket = new ServerSocket(61174);

            System.out.println("Server running");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        while(true){
            try{
                Socket clientSocket = serverSocket.accept();

                ServerThread serviceThread = new ServerThread(clientSocket);

                serviceThread.start();
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

    public static void main(String [] args)
    {
        new Server();
    }
}

class ServerThread extends Thread{
    private Socket clientSocket;

    ServerThread(Socket s){
        clientSocket = s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId() + " " + clientSocket);

        try{
            InputStream inputStream = clientSocket.getInputStream(); // Access the read stream

            byte [] buffer = new byte[1024];

            String ONE_HUNDRED_MEGABYTE_FILE = "100_megabyte_file_out(" + Thread.currentThread().getId() + ").txt";

            File file = new File(ONE_HUNDRED_MEGABYTE_FILE);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            int len;

            while ((len = inputStream.read(buffer)) > 0) {
                bufferedOutputStream.write(buffer, 0, len);
            }

            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            inputStream.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}


