package segmentedfilesystem;

import java.util.ArrayList;

public class UDPFile {
    private byte fileID;
    private byte[] filename = new byte[124];
    private ArrayList<Byte> data;
    private boolean complete = false;
    private int length = 0;

    public UDPFile(UDPHeader header) {
        this.filename = header.getFilename();
        this.fileID = header.getID();
    }

    public boolean isComplete() {
        return complete;
    }

    public void addData(UDPData data) {
        //this.data.add()
    }

    public byte[] getData() {
        return new byte[0];
    }
}
