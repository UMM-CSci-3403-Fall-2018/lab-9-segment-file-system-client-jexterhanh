package segmentedfilesystem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

// UDPFile class
public class UDPFile {
    private byte fileID = 0;
    private String filename;
    private ArrayList<UDPData> datas = new ArrayList<>();
    private boolean complete = false;
    private int length = 0;
    public int amount = 0;
    public int expectAmount = Integer.MAX_VALUE;
    private boolean hasHeader = false;

    public UDPFile() {
        // create a UDPFile object
    }

    // set file ID when there wasn't a UDPFile
    public void mkUDPFile(UDPHeader header) {
        this.fileID = header.getID();
        this.filename = header.getFilename();
        this.hasHeader = true;
        checkComplete();
    }

    // set file ID when there wasn't a UDPFile
    public void mkUDPFile(UDPData data) {
        this.fileID = data.getID();
        if (data.getStatus() == 3) {
            this.datas.add(data);
            this.length += data.dataLength;
            //System.out.println(Arrays.toString(data.getPacketData()));
            this.expectAmount = data.getPacketNumber();
        } else if (data.getStatus() == 1){
            this.datas.add(data);
            this.length += data.dataLength;
            this.amount++;
        }
        checkComplete();
    }

    public void checkComplete() {
        if (this.hasHeader == true && this.amount == this.expectAmount) {
            this.complete = true;
            Collections.sort(this.datas, new SortByPN());
            System.out.println("this one is completed!");
        }
    }

    // check if the file is completed
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
            this.datas.add(data);
            this.length += data.dataLength;
            //System.out.println(Arrays.toString(datas.get(datas.size() - 1).getPacketData()));
            this.expectAmount = data.getPacketNumber();
        } else if (data.getStatus() == 1){
            this.datas.add(data);
            this.length += data.dataLength;
            this.amount++;
        } else {
            System.out.println("This data packet doesn't have a correct status number therefore I refuse to do anything!");
        }
        checkComplete();
    }

    // get data as array from the arrayList datas
    public byte[] getData() {
        byte[] result = new byte[length];
        int counter = 0;
        for (int i = 0; i < expectAmount + 1; i++) {
            for (int j = 0; j < this.datas.get(i).dataLength; j++) {
                result[counter] = this.datas.get(i).getPacketData()[j];
                counter++;
            }
        }
        return result;
    }

    // output received file to the file system
    public void outputFile() throws IOException{
        //System.out.println(filename);
        String data = new String(this.getData());
        //System.out.println(data);
        try (FileOutputStream fos = new FileOutputStream(this.filename)) {
            fos.write(getData());
            fos.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
