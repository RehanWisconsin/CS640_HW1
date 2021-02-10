import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetSocketAddress;

public class Iperfer {
    public static void main (String[] args) {
        boolean client = false;
        boolean server = false;
        
        String hostname = "";
        int port = -1;
        int time = -1;

        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("-c")) {
                client = true;
            }

            else if(args[i].equals("-s")) {
                server = true;
            }

            else if(args[i].equals("-h") && client) {
                hostname = args[i+1];
                i++;
            }

            else if(args[i].equals("-p")) {
                port = Integer.parseInt(args[i+1]);
                i++;

                if(port < 1024 || port > 65535) {
                    System.out.println("Error: port number must be in the range 1024 to 65535");
                    System.exit(0);
                }
            }

            else if(args[i].equals("-t") && client) {
                time = Integer.parseInt(args[i+1]);
                i++;
            }
        }

        if(client && (port == -1 || time == -1 || hostname.equals("") || server)) {
            System.out.println("Error: missing or additional arguments");
        }
        
        
        if(client) {
            try {
                Socket socket = new Socket();
                long start = System.nanoTime();
                long time_in_nano = (long) (time*Math.pow(10, 9));
                
                InetSocketAddress host = new InetSocketAddress(hostname, port);
                socket.connect(host);
                
                /*long numBytes = 0;
                
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while(System.nanoTime() - start < time_in_nano) {
                    System.out.println(System.nanoTime());
                }*/

                socket.close();
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(server) {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
    
                clientSocket.close();
                serverSocket.close();
            }
    
            catch(Exception e) {
                e.printStackTrace();
            }
        }

    }
}