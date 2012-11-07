import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: manojs
 * Date: 6/11/12
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ButtonCounter {

    private List<Integer> viewChannels;
    private RemoteControl remoteControl;
    private int countButtonPress;

    private CounterImplementation counter;

    public ButtonCounter(RemoteControl remote) {
        this.setRemoteControl(remote);
        this.setViewChannels(new LinkedList<Integer>());
        this.setCountButtonPress(0);
        this.setCounter(new CounterImplementation(this));
    }

    public CounterImplementation getCounter() {
        return counter;
    }

    public void setCounter(CounterImplementation counter) {
        this.counter = counter;
    }

    public RemoteControl getRemoteControl() {
        return remoteControl;
    }

    public void setRemoteControl(RemoteControl remoteControl) {
        this.remoteControl = remoteControl;
    }

    public List<Integer> getViewChannels() {
        return viewChannels;
    }

    public void setViewChannels(List<Integer> viewChannels) {
        this.viewChannels = viewChannels;
    }

    public int getCountButtonPress() {
        return countButtonPress;
    }

    public void setCountButtonPress(int countButtonPress) {
        this.countButtonPress = countButtonPress;
    }

    private void countButtonPress() {
        for (Integer channel : this.getViewChannels()) {
            this.getRemoteControl().setSelectedChannel(channel);
            counter.countIndividualButtonPress();
            this.getRemoteControl().setCurrentChannel(channel);
        }
    }

    public void increaseCount(int count){
        this.setCountButtonPress(this.getCountButtonPress() + count);
    }

    public void decreaseCount(int count){
        this.setCountButtonPress(this.getCountButtonPress() - count);
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Input:");
        BufferedReader lowestHighestReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader blockedChannelsReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader ViewChannelsReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String[] lowestHighestArray = lowestHighestReader.readLine().trim().split("\\s+");
            String[] blockedChannelsArray = blockedChannelsReader.readLine().trim().split("\\s+");
            String[] viewChannelsArray = ViewChannelsReader.readLine().trim().split("\\s+");

            RemoteControl remote = new RemoteControl();
            ButtonCounter buttonCounter = new ButtonCounter(remote);

            Validator validator = new Validator();

//          =============== Lowest Highest input ===============
            remote.setLowestChannel(Integer.parseInt(lowestHighestArray[0]));
            remote.setHighestChannel(Integer.parseInt(lowestHighestArray[1]));

//          =============== Block channels input ===============
            for (String numberString : blockedChannelsArray) {
                remote.getBlockedChannels().add(Integer.parseInt(numberString));
            }
            remote.getBlockedChannels().remove(0);

//          =============== View channels input ===============
            for (String numberString : viewChannelsArray) {
                buttonCounter.getViewChannels().add(Integer.parseInt(numberString));
            }

            buttonCounter.getViewChannels().remove(0);

//            TODO: Validation should be added after each input has been taken
            validator.validateLowHigh(buttonCounter.getRemoteControl());
            validator.validateBlockedChannels(buttonCounter.getRemoteControl());
            validator.validateViewChannels(buttonCounter);

            if( !validator.isDataValid() ){
                System.out.println("Error:");
                System.out.println(validator.getErrorMessages());
                System.exit(0);
            }


            buttonCounter.countButtonPress();

            System.out.println("Output: " + buttonCounter.getCountButtonPress());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Exception exception = new Exception("Error: Invalid input data");
            exception.printStackTrace();
        }


    }

}

//Test Case #1
//        1 20
//        2 18 19
//        5 15 14 17 1 17
//
//
//        Test Case #2
//        103 108
//        1 104
//        5 105 106 107 103 105
//
//
//        Test Case #3
//        1 100
//        4 78 79 80 3
//        8 10 13 13 100 99 98 77 81
//
//
//        Test Case #4
//        1 200
//        0
//        4 1 100 1 101
