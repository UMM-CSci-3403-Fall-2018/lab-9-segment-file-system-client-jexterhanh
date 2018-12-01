package segmentedfilesystem;

public class UDPData {
    private byte[] dataPacket = new byte[128];

    UDPData(byte[] packet) {
        this.dataPacket = packet;
    }

    public void updatePacket(byte[] response) {
        this.dataPacket = response;
    }

    public byte getStatus() {
        return dataPacket[0];
    }

    public byte getID() {
        return dataPacket[1];
    }

    public byte getPNumber() {
        return dataPacket[3];
    }

}
