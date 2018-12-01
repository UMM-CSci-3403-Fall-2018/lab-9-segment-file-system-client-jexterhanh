package segmentedfilesystem;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Main {

    // set up the server site and the port #; create a file array
    private static final String serverURL= "heartofgold.morris.umn.edu";
    private static final int port = 6014;
    public static UDPFile[] files = new UDPFile[3];



    // the main method
    public static void main(String[] args) throws IOException {
        Main segmentClient = new Main();
        segmentClient.start();
        for (int i = 0; i < files.length; i++) {
            files[i].outputFile();
        }
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

        for (int i = 0; i < files.length; i++) {
            files[i] = new UDPFile();
        }

        /**  Need to figure out how to actually distribute the packets
         *
         *

        // display response
        while (!isComplete()) {
            int position = 0;
            socket.receive(packet);
            byte[] bytes = packet.getData();
            System.out.println(Arrays.toString(bytes));
            if (bytes[0] == 0) {
                UDPHeader header = new UDPHeader(bytes);
                while (files[position].getID() != header.getID() && files[position].getID() != 0) {
                    position += 1;
                }
                files[position].addHeader(header);
            } else if (bytes[0] == 3) {
                UDPData data = new UDPData(bytes);
                // set amount of packets



            }

        }*/



        socket.close();
    }

    public static boolean isComplete() {
        for (int i = 0; i < 3; i++) {
            if (files[i] == null) {
                return false;
            }
        }
        return (files[0].isComplete() && files[1].isComplete() && files[2].isComplete());
    }

}