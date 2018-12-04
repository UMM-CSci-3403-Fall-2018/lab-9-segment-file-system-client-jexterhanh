package segmentedfilesystem;

public class UDPHeader {

    private byte status;
    private byte fileID;
    private byte[] filename = new byte[126];

    public UDPHeader(byte[] packet) {
        this.status = packet[0];
        this.fileID = packet[1];
        for (int i = 2; i < packet.length; i++) {
            filename[i - 2] = packet[i];
        }
    }

    public byte getStatus() {
        return this.status;
    }

    public byte getID() {
        return this.fileID;
    }

    public byte[] getFilename() {
        return filename;
    }
}
