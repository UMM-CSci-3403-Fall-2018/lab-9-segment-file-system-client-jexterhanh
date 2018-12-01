package segmentedfilesystem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UDPFile {
    private byte fileID;
    private byte[] filename = new byte[124];
    private ArrayList<Byte> data;
    private boolean complete = false;
    private int length = 0;
    public int amount = 0;

    public UDPFile() {
        // create a UDPFile object
    }

    public boolean isComplete() {
        return complete;
    }

    public void setID(byte id) {
        this.fileID = id;
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
                this.length += 1;
            }
            this.complete = true;
        } else if (data.getStatus() == 1){
            for (int i = 0; i < data.dataLength; i++) {
                this.data.add(data.getPacketData()[i]);
                this.length += 1;
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
