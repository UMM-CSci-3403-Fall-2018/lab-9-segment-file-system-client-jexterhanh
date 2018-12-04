package segmentedfilesystem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UDPFile {
    private byte fileID = 0;
    private byte[] filename = new byte[126];
    private ArrayList<Byte> data = new ArrayList<>();
    private boolean complete = false;
    private int length = 0;
    public int amount = 1;
    public int expectAmount = Integer.MAX_VALUE;

    public UDPFile() {
        // create a UDPFile object
    }

    public void mkUDPFile(UDPHeader header) {
        this.fileID = header.getID();
        this.filename = header.getFilename();
    }

    public void mkUDPFile(UDPData data) {
        this.fileID = data.getID();
        if (data.getStatus() == 3) {
            for (int i = 0; i < data.dataLength; i++) {
                this.data.add(data.getPacketData()[i]);
                this.length++;
                }
            if (data.getPacketNumber()[1] < 0) {
                this.expectAmount = 256 * data.getPacketNumber()[0] + 256 + data.getPacketNumber()[1];
                System.out.println(this.expectAmount);
            } else {
                this.expectAmount = 256 * data.getPacketNumber()[0] + data.getPacketNumber()[1];
                System.out.println(this.expectAmount);
            }
            //this.complete = true;
        } else if (data.getStatus() == 1){
            for (int i = 0; i < data.dataLength; i++) {
                this.data.add(data.getPacketData()[i]);
                this.length++;
                this.amount++;
                if (this.amount == this.expectAmount) {
                    this.complete = true;
                    System.out.println("this one is completed!");
                }
            }
        }
    }

    public boolean isComplete() {
        return complete;
    }

    public byte getID() {
        return fileID;
    }

    public void addHeader(UDPHeader header) {
        this.filename = header.getFilename();
    }

    public void addData(UDPData data) {
        if (data.getStatus() == 3) {
            for (int i = 0; i < data.dataLength; i++) {
                this.data.add(data.getPacketData()[i]);
                this.length++;

            }
            if (data.getPacketNumber()[1] < 0) {

                this.expectAmount = 256 * data.getPacketNumber()[0] + 256 + data.getPacketNumber()[1];
                System.out.println(this.expectAmount);
            } else {
                System.out.println("I got here");
                this.expectAmount = 256 * data.getPacketNumber()[0] + data.getPacketNumber()[1];
                System.out.println(this.expectAmount);
            }
            //this.complete = true;
        } else if (data.getStatus() == 1){
            for (int i = 0; i < data.dataLength; i++) {
                this.data.add(data.getPacketData()[i]);
                this.length++;
                this.amount++;
                if (this.amount == this.expectAmount) {
                    this.complete = true;
                    System.out.println("this one is completed!");
                }
            }
        } else {
            System.out.println("This data packet doesn't have a correct status number therefore I refuse to do anything!");
        }
    }

    public byte[] getData() {
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = this.data.get(i);
        }
        return result;
    }


    public void outputFile() {
        String filename = new String(this.filename);
        try (FileOutputStream fos = new FileOutputStream("../" + filename)) {
            fos.write(getData());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
