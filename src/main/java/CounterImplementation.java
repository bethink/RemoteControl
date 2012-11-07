/**
 * Created with IntelliJ IDEA.
 * User: manojs
 * Date: 6/11/12
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class CounterImplementation {

    ButtonCounter buttonCounter;
    RemoteControl remoteControl;
    int BIGNUM = 100;

    public RemoteControl getRemoteControl() {
        return remoteControl;
    }

    public void setRemoteControl(RemoteControl remoteControl) {
        this.remoteControl = remoteControl;
    }

    public CounterImplementation(ButtonCounter buttonCounter){
        this.buttonCounter = buttonCounter;
        this.remoteControl = buttonCounter.getRemoteControl();
    }

    public ButtonCounter getButtonCounter() {
        return buttonCounter;
    }

    public void setButtonCounter(ButtonCounter buttonCounter) {
        this.buttonCounter = buttonCounter;
    }

    public void countIndividualButtonPress() {

        if( this.getRemoteControl().getCurrentChannel() == 0 ){
            int digitCount = this.buttonDigitCount();
            buttonCounter.increaseCount(digitCount);
//            System.out.println("digitCount: " + digitCount);
            return;
        }

        if (isPrevChannelMaching()) {
            buttonCounter.increaseCount(1);
//            System.out.println("Back: " + 1);
        }else{

            int digitCount = this.buttonDigitCount();
            int upCount = this.upButtonCount();
            int downCount = this.downButtonCount();
            int backWithDownCount = this.backWithDownButtonCount();
            int backWithUpCount = this.backWithUpButtonCount();

//            System.out.println("digitCount: " + digitCount);
//            System.out.println("upCount: " + upCount);
//            System.out.println("downCount: " + downCount);
//            System.out.println("backWithDownCount: " + backWithDownCount);
//            System.out.println("backWithUpCount: " + backWithUpCount);
//            System.out.println();

            int count = digitCount < upCount ? digitCount : upCount;
            count = count < downCount ? count : downCount;
            count = count < backWithDownCount ? count : backWithDownCount;
            count = count < backWithUpCount ? count : backWithUpCount;
            buttonCounter.increaseCount(count);
        }

    }

    public boolean isPrevChannelMaching() {
        RemoteControl remote = this.getButtonCounter().getRemoteControl();
        return remote.getPreviousChannel() == remote.getSelectedChannel();
    }

    public int buttonDigitCount(){
        int channel = this.getRemoteControl().getSelectedChannel();
        return String.valueOf(channel).length();
    }

    public int backWithUpButtonCount(){
        RemoteControl remote = this.getRemoteControl();
        int prev = remote.getPreviousChannel();

        if (prev == 0){
            return BIGNUM;
        }

        int current = remote.getCurrentChannel();

        remote.setCurrentChannel(prev);
        int backWithUpCount = 1 + this.upButtonCount();
        remote.setCurrentChannel(current);

        return backWithUpCount;
    }

    public int backWithDownButtonCount(){
        RemoteControl remote = this.getRemoteControl();
        int prev = remote.getPreviousChannel();

        if (prev == 0){
            return BIGNUM;
        }

        int current = remote.getCurrentChannel();

        remote.setCurrentChannel(prev);
        int backWithDownCount = 1 + this.downButtonCount();
        remote.setCurrentChannel(current);

        return backWithDownCount;
    }

    public int upButtonCount(){

        RemoteControl remote = this.getRemoteControl();
        int selected = remote.getSelectedChannel();
        int current = remote.getCurrentChannel();
        int forwardCount = 0;

        if(current < selected){
            forwardCount = selected - current;
            forwardCount = removeBlocked(forwardCount, current, selected);
        }else{
            forwardCount = ( remote.getHighestChannel() - current) + ( selected - remote.getLowestChannel() + 1) ;
            forwardCount = removeBlocked(forwardCount, current, remote.getHighestChannel() );
            forwardCount = removeBlocked(forwardCount, remote.getLowestChannel(), selected );
        }

        return forwardCount;
    }

    public int downButtonCount(){

        RemoteControl remote = this.getRemoteControl();
        int selected = remote.getSelectedChannel();
        int current = remote.getCurrentChannel();
        int backwardCount = 0;

        if(current < selected){
            backwardCount = (current - remote.getLowestChannel()) + (remote.getHighestChannel() - selected + 1);
            backwardCount = removeBlocked(backwardCount, remote.getLowestChannel(), current);
            backwardCount = removeBlocked(backwardCount, selected, remote.getHighestChannel());
        }else{
            backwardCount = ( current - selected );
            backwardCount = removeBlocked(backwardCount, selected, current);
        }

        return backwardCount;
    }

    public int removeBlocked(int count, int low, int high){

        for(int blockedChannel : this.getRemoteControl().getBlockedChannels()){
            if( low <= blockedChannel && blockedChannel <= high ){
                count -= 1;
            }
        }

        return count;
    }

}
