import org.testng.annotations.*;
import static org.testng.Assert.assertEquals;

/**
* Created with IntelliJ IDEA.
* User: manojs
* Date: 6/11/12
* Time: 4:03 PM
* To change this template use File | Settings | File Templates.
*/
public class ButtonCounterTest {

    RemoteControl remote;
    ButtonCounter buttonCounter;

    @BeforeClass
    public void beforeClass(){
        remote = new RemoteControl();
        buttonCounter = new ButtonCounter(remote);
    }

    @Test
    public void testIncreaseCount() throws Exception {
        buttonCounter.increaseCount(5);
        assertEquals(5, buttonCounter.getCountButtonPress());
    }

    @Test(dependsOnMethods = "testIncreaseCount")
    public void testDecreaseCount() throws Exception {
        buttonCounter.decreaseCount(2);
        assertEquals(3, buttonCounter.getCountButtonPress());
    }

}
