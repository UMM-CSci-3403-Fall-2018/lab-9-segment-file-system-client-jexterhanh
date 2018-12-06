package segmentedfilesystem;

import java.util.Comparator;

public class SortByPN implements Comparator<UDPData> {
    public int compare(UDPData a, UDPData b) {
        return a.getPacketNumber() - b.getPacketNumber();
    }
}
