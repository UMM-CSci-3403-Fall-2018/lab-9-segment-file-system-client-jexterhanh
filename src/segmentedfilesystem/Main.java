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


        // display response
        while (!isComplete()) {
            socket.receive(packet);
            byte[] bytes = packet.getData();
            System.out.println(Arrays.toString(bytes));

            if (bytes[0] == 0) {
                UDPHeader header = new UDPHeader(bytes);
                for (int i = 0; i < 3; i ++) {
                    if (files[i].getID() == 0) {
                        files[i].mkUDPFile(header);
                    } else if (files[i].getID() == header.getID()) {
                        files[i].addHeader(header);
                    }
                }
            } else if ((bytes[0] == 3) || (bytes[0] == 1)) {
                UDPData data = new UDPData(bytes);
                for (int i = 0; i < 3; i ++) {
                    if (files[i].getID() == 0) {
                        files[i].mkUDPFile(data);
                    } else if (files[i].getID() == data.getID()) {
                        files[i].addData(data);
                    }
                }
            }

        }



        socket.close();
    }

    public static boolean isComplete() {
        return (files[0].isComplete() && files[1].isComplete() && files[2].isComplete());
    }

}