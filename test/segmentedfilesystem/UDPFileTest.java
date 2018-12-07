package segmentedfilesystem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UDPFileTest {

    UDPFile testFile001 = new UDPFile();
    UDPFile testFile002 = new UDPFile();

    byte a = 1;
    byte b = 2;
    byte c = 3;
    byte d = 4;
    byte e = 5;
    byte[] helperByteArray = new byte[] {a, b, c, d, e};
    UDPHeader helperHeader = new UDPHeader(helperByteArray, 5);

    @Before
    public void setUp() throws Exception {
        testFile002.mkUDPFile(helperHeader);
    }

    @Test
    public void checkComplete() {
        assertTrue(!testFile001.isComplete());
        assertTrue(!testFile002.isComplete());
    }

    @Test
    public void isComplete() {
        assertTrue(!testFile001.isComplete());
        assertTrue(!testFile002.isComplete());
    }

    @Test
    public void isAssigned() {
        assertTrue(!testFile001.isAssigned());
        assertTrue(testFile002.isAssigned());
    }

    @Test
    public void getID() {
        assertEquals(testFile001.getID(), 0);
        assertEquals(testFile002.getID(), 2);
    }
}