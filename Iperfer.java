import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.IOException;


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
                runClient(hostname, port, time);
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if(server) {
            try {
                runServer(port)
            }
    
            catch(Exception e) {
                e.printStackTrace();
            }
        }

    }


    private static void runClient(String hostname, int portNumber, int time)
    {
        double sent = 0;
        double speed = 0;
        byte[] packet = new byte[1000];
        long endTime;

        Socket client = new Socket();
        InetSocketAddress host = new InetSocketAddress(hostname, portNumber);

        try {
            //establishes a connection
            client.connect(host);
            endTime = System.currentTimeMillis() + (time * 1000);

            //sends packets until the time specified is up
            while (System.currentTimeMillis() < endTime)
            {
                client.getOutputStream().write(packet);
                sent++;
            }
            //closes the connection
            client.close();
        } catch (IOException e) {
            System.out.println("Error IO Exception");
            System.exit(4);
        }
        speed = (8.0*sent/1000.0)/(double)time;
        //prints the number of bytes sent and the speed it was sent
        System.out.println("sent=" + (int)(sent + 0.01) + " KB rate=" + speed + " Mbps");
    }

    private static void runServer(int portNumber)
    {
        double received = 0;
        double speed = 0;
        long startTime = 0;
        long endTime = 0;
        double time;
        byte[] packet = new byte[1000];
        double num = 0;

        try {
            ServerSocket server = new ServerSocket();
            InetSocketAddress host = new InetSocketAddress(portNumber);
            server.bind(host);
            //waits to connect to a client
            Socket client = server.accept();
            startTime = System.currentTimeMillis();
            //receives all the packets that the client is sending
            while(num > -1)
            {
                num = client.getInputStream().read(packet, 0, 1000);
                received += num/1000;
            }
            endTime = System.currentTimeMillis();

            //ends the client and server connections
            client.close();
            server.close();
        } catch (IOException e) {
            System.out.println("Error IO Exception");
            System.exit(4);
        }

        time = (double)(endTime - startTime) / 1000.0;
        speed = (8.0*received/1000.0)/time;
        //displays the bytes received and the speed it received them
        System.out.println("received=" + (int)(received + 0.01) + " KB rate=" + speed + " Mbps");
    }

}