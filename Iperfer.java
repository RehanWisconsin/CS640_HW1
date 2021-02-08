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

        if(client && (port == -1 || time == -1 || hostname.equals("" || server))) {
            System.out.println("Error: missing or additional arguments");
        }
        
        if(client) {
            
        }

        else if(server) {

        }

    }
}