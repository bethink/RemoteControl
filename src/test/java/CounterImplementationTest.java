import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;
/**
* Created with IntelliJ IDEA.
* User: manojs
* Date: 6/11/12
* Time: 4:15 PM
* To change this template use File | Settings | File Templates.
*/
public class CounterImplementationTest {

    RemoteControl remote;
    ButtonCounter buttonCounter;
    CounterImplementation counter;

    @BeforeMethod
    public void beforeClass(){
        remote = new RemoteControl();
        buttonCounter = new ButtonCounter(remote);
        counter = buttonCounter.getCounter();
    }

    @Test
    public void testButtonDigitCount() throws Exception {
        remote.setSelectedChannel(2);
        assertEquals(counter.buttonDigitCount(), 1);

        remote.setSelectedChannel(23);
        assertEquals(counter.buttonDigitCount(), 2);

        remote.setSelectedChannel(231);
        assertEquals(counter.buttonDigitCount(), 3);
    }

    @Test
    public void testIsPrevChannelMaching() throws Exception {
        remote.setCurrentChannel(Integer.valueOf(3));
        remote.setCurrentChannel(3);
        remote.setSelectedChannel(3);
        assertTrue(counter.isPrevChannelMaching());

        remote.setSelectedChannel(2);
        assertFalse(counter.isPrevChannelMaching());
    }

    @Test
    public void testRemoveBlocked() throws Exception {

        remote.setBlockedChannels(Arrays.asList(3, 5, 6));
        assertEquals(counter.removeBlocked(5, 2, 7), 2);
        assertEquals(counter.removeBlocked(5, 2, 5), 3);
        assertEquals(counter.removeBlocked(5, 2, 4), 4);
        assertEquals(counter.removeBlocked(5, 5, 7), 3);
    }

    @Test
    public void testUpButtonCount() throws Exception {

        remote.setBlockedChannels(Arrays.asList(3, 5, 6, 9));
        remote.setLowestChannel(1);
        remote.setHighestChannel(10);

//        Senario 1
        remote.setCurrentChannel(2);
        remote.setSelectedChannel(4);
        assertEquals(counter.upButtonCount(), 1);

//        Senario 2
        remote.setCurrentChannel(2);
        remote.setSelectedChannel(10);
        assertEquals(counter.upButtonCount(), 4);

//        Senario 3
        remote.setCurrentChannel(8);
        remote.setSelectedChannel(4);
        assertEquals(counter.upButtonCount(), 4);
    }

    @Test
    public void testDownButtonCount() throws Exception {

        remote.setBlockedChannels(Arrays.asList(3, 5, 6, 9));
        remote.setLowestChannel(1);
        remote.setHighestChannel(10);

//        Senario 1
        remote.setCurrentChannel(2);
        remote.setSelectedChannel(4);
        assertEquals(counter.downButtonCount(), 5);

//        Senario 2
        remote.setCurrentChannel(2);
        remote.setSelectedChannel(10);
        assertEquals(counter.downButtonCount(), 2);

//        Senario 3
        remote.setCurrentChannel(8);
        remote.setSelectedChannel(4);
        assertEquals(counter.downButtonCount(), 2);
    }


//   =================== CountIndividualButtonPress ===================

    @Test
    public void testCountIndividualButtonPressBack() throws Exception {

        remote.setBlockedChannels(Arrays.asList(3, 5, 6, 9));
        remote.setLowestChannel(1);
        remote.setHighestChannel(100);

//        Senario: Back
        remote.setCurrentChannel(9);
        remote.setCurrentChannel(4);
        remote.setSelectedChannel(9);
        counter.countIndividualButtonPress();
        assertEquals(counter.getButtonCounter().getCountButtonPress(), 1);

    }

    @Test
    public void testCountIndividualButtonPressForward() throws Exception {

        remote.setLowestChannel(1);
        remote.setHighestChannel(100);
        remote.setBlockedChannels(Arrays.asList(99));

//        Senario Forward
        remote.setCurrentChannel(4);
        remote.setCurrentChannel(98);
        remote.setSelectedChannel(100);
        counter.countIndividualButtonPress();
        assertEquals(counter.getButtonCounter().getCountButtonPress(), 1);
    }

    @Test
    public void testCountIndividualButtonPressBackward() throws Exception {

        remote.setLowestChannel(1);
        remote.setHighestChannel(100);
        remote.setBlockedChannels(Arrays.asList(99));

//        Senario Backward
        remote.setCurrentChannel(77);
        remote.setCurrentChannel(1);
        remote.setSelectedChannel(100);
        counter.countIndividualButtonPress();
        assertEquals(counter.getButtonCounter().getCountButtonPress(), 1);
    }

    @Test
    public void testCountIndividualButtonPressBackWithDown() throws Exception {

        remote.setLowestChannel(1);
        remote.setHighestChannel(1000);
        remote.setBlockedChannels(Arrays.asList(999));

//        Senario Back with forward
        remote.setCurrentChannel(979);
        remote.setCurrentChannel(1);
        remote.setSelectedChannel(978);
        counter.countIndividualButtonPress();
        assertEquals(counter.getButtonCounter().getCountButtonPress(), 2);
    }

    @Test
    public void testCountIndividualButtonPressBackWithUp() throws Exception {

        remote.setLowestChannel(1);
        remote.setHighestChannel(1000);
        remote.setBlockedChannels(Arrays.asList(999));

//        Senario Back with forward
        remote.setCurrentChannel(977);
        remote.setCurrentChannel(1);
        remote.setSelectedChannel(978);
        counter.countIndividualButtonPress();
        assertEquals(counter.getButtonCounter().getCountButtonPress(), 2);
    }

}
