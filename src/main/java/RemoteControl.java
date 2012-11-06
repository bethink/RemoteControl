import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: manojs
 * Date: 6/11/12
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class RemoteControl {


    private int lowestChannel;
    private int highestChannel;
    private int previousChannel;
    private int currentChannel;
    private int selectedChannel;
    private List<Integer> blockedChannels;

    public RemoteControl(){
        this.blockedChannels = new LinkedList<Integer>();
    }

    public List<Integer> getBlockedChannels() {
        return blockedChannels;
    }

    public void setBlockedChannels(List<Integer> blockedChannels) {
        this.blockedChannels = blockedChannels;
    }

    public int getSelectedChannel() {
        return selectedChannel;
    }

    public void setSelectedChannel(int selectedChannel) {
        this.selectedChannel = selectedChannel;
    }

    public int getLowestChannel() {
        return lowestChannel;
    }

    public void setLowestChannel(int lowestChannel) {
        this.lowestChannel = lowestChannel;
    }

    public int getHighestChannel() {
        return highestChannel;
    }

    public void setHighestChannel(int highestChannel) {
        this.highestChannel = highestChannel;
    }

    public int getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(int currentChannel) {
        this.previousChannel = this.getCurrentChannel();
        this.currentChannel = currentChannel;
    }

    public int getPreviousChannel() {
        return previousChannel;
    }

//    public void setPreviousChannel(int previousChannel) {
//        this.previousChannel = previousChannel;
//    }

}
