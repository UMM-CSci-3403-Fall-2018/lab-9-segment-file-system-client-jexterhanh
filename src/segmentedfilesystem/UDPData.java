package segmentedfilesystem;

public class UDPData {

    private byte status;
    private byte fileID;
    private byte[] packetNumber = new byte[2];
    private byte[] packetData = new byte[124];
    public int dataLength = 0;

    public UDPData(byte[] packet) {
        this.status = packet[0];
        this.fileID = packet[1];
        for (int i = 2; i < 4; i++) {
            packetNumber[i - 2] = packet[i];
        }
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

    public byte[] getPacketNumber() {
        return packetNumber;
    }
    public byte[] getPacketData() {
        return packetData;
    }

}
