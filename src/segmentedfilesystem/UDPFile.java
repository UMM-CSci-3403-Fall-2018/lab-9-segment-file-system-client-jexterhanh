package segmentedfilesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class UDPFile {
    private byte fileID = 0;
    private byte[] filename = new byte[126];
    private ArrayList<Byte> data = new ArrayList<>();
    private boolean complete = false;
    private int length = 0;
    public int amount = 0;
    public int expectAmount = Integer.MAX_VALUE;
    private boolean hasHeader = false;

    public UDPFile() {
        // create a UDPFile object
    }

    public void mkUDPFile(UDPHeader header) {
        this.fileID = header.getID();
        this.filename = header.getFilename();
        this.hasHeader = true;
        checkComplete();
    }

    public void mkUDPFile(UDPData data) {
        this.fileID = data.getID();
        if (data.getStatus() == 3) {
            for (int i = 0; i < data.dataLength; i++) {
                this.data.add(data.getPacketData()[i]);
                this.length++;
            }
            this.expectAmount = data.getPacketNumber();
        } else if (data.getStatus() == 1){
            for (int i = 0; i < data.dataLength; i++) {
                this.data.add(data.getPacketData()[i]);
                this.length++;

            }
            this.amount++;
        }
        checkComplete();
    }

    public void checkComplete() {
        if (this.hasHeader == true && this.amount == this.expectAmount) {
            this.complete = true;
            Collections.sort(this.data);
            System.out.println("this one is completed!");
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
        this.hasHeader = true;
        checkComplete();
    }

    public void addData(UDPData data) {
        if (data.getStatus() == 3) {
            for (int i = 0; i < data.dataLength; i++) {
                this.data.add(data.getPacketData()[i]);
                this.length++;
            }
            this.expectAmount = data.getPacketNumber();
        } else if (data.getStatus() == 1){
            for (int i = 0; i < data.dataLength; i++) {
                this.data.add(data.getPacketData()[i]);
                this.length++;
            }
            this.amount++;
        } else {
            System.out.println("This data packet doesn't have a correct status number therefore I refuse to do anything!");
        }
        checkComplete();
    }

    public byte[] getData() {
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = this.data.get(i);
        }
        return result;
    }


    public void outputFile() {
        String path = "../";
        String filename = new String(this.filename);
        File file = new File(path + filename);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(getData());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
