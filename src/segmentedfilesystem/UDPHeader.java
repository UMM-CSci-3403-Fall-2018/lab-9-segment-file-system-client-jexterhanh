package segmentedfilesystem;

public class UDPHeader {

    private byte status;
    private byte fileID;
    private String filename;

    public UDPHeader(byte[] packet, int length) {
        this.status = packet[0];
        this.fileID = packet[1];
        byte[] temp = new byte[length - 2];
        for (int i = 2; i < length; i++) {

            temp[i - 2] = packet[i];
        }
        this.filename = new String(temp);
    }

    public byte getStatus() {
        return this.status;
    }

    public byte getID() {
        return this.fileID;
    }

    public String getFilename() {
        return filename;
    }
}
