package segmentedfilesystem;

public class UDPHeader {
    private byte[] headerPacket = new byte[128];

    UDPHeader(byte[] packet) {
        this.headerPacket = packet;
    }

    public void updatePacket(byte[] response) {
        this.headerPacket = response;
    }

    public byte getStatus() {
        return headerPacket[0];
    }

    public byte getID() {
        return headerPacket[1];
    }

    public byte getPNumber() {
        return headerPacket[3];
    }
}
