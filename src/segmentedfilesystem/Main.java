package segmentedfilesystem;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Main {

    // set up the server site and the port #
    private static final String serverURL= "heartofgold.morris.umn.edu";
    private static final int port = 6014;


    // the main method
    public static void main(String[] args) throws IOException {
        Main segmentClient = new Main();
        segmentClient.start();
    }

    private static boolean isComplete(DatagramPacket packet) {
        if (packet.getData() == null) {
            System.out.println("You're done!");
            return true;
        }
        return false;
    }

    // start the client
    public static void start() throws IOException {

        // get a datagram socket
        DatagramSocket socket = new DatagramSocket();

        // send request
        byte[] buf = new byte[128];
        InetAddress address = InetAddress.getByName(serverURL);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);

        // get response
        packet = new DatagramPacket(buf, buf.length);

        // display response
        while (!isComplete(packet)) {
            socket.receive(packet);
            byte[] bytes = packet.getData();
            System.out.println(Arrays.toString(bytes));
        }



        socket.close();
    }

}