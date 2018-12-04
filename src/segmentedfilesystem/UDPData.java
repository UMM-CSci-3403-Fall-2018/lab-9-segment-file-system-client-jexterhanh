package segmentedfilesystem;

public class UDPData {

    private byte status;
    private byte fileID;
    private int packetNumber;
    private byte[] packetData = new byte[124];
    public int dataLength = 0;

    public UDPData(byte[] packet) {
        this.status = packet[0];
        this.fileID = packet[1];
        // credit to @Ethan
        this.packetNumber = (packet[2] << 8) | packet[3] & 0xFF;
        for (int i = 4; i < packet.length; i++) {
            packetData[i - 4] = packet[i];
            dataLength += 1;
        }
    }

    public byte getStatus() {
        return this.status;
    }

    public byte getID() {
        return this.fileID;
    }

    public int getPacketNumber() {
        return this.packetNumber;
    }
    public byte[] getPacketData() {
        return this.packetData;
    }

}
