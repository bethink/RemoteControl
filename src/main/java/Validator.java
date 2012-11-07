import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: manojs
 * Date: 7/11/12
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Validator {

    private List<String> errorMsg;

    public Validator(){
        this.errorMsg = new ArrayList<String>();
    }

    public List<String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(List<String> errorMsg) {
        this.errorMsg = errorMsg;
    }

//    public void validateButtonCounter(ButtonCounter counter){
//        validateLowHigh(counter.getRemoteControl());
//        validateBlockedChannels(counter.getRemoteControl());
//    }

    public void validateBlockedChannels(RemoteControl remote){

        if( remote.getBlockedChannels().size() > 40 ){
            this.getErrorMsg().add("No. of Block Channel should not be more than 40");
        }

        for( int channel : remote.getBlockedChannels() ){
            if (channel < remote.getLowestChannel() || channel > remote.getHighestChannel()){
                this.getErrorMsg().add("Some of the blocked channels are not in the range");
                break;
            }
        }

    }

    public void validateViewChannels(ButtonCounter counter){

        RemoteControl remote = counter.getRemoteControl();

        if( counter.getViewChannels().size() > 50 ){
            this.getErrorMsg().add("No. of view Channel should not be more than 50");
        }

        for( int channel : counter.getViewChannels() ){
            if (channel < remote.getLowestChannel() || channel > remote.getHighestChannel()){
                this.getErrorMsg().add("Some of the view channels are not in the range");
                break;
            }
        }

    }

    public List<String> validateLowHigh(RemoteControl remote){

        if(remote.getLowestChannel() < 0 || remote.getHighestChannel() > 10000){
            this.getErrorMsg().add("Channel range should be 0 to 10000");
        }

        return errorMsg;
    }

    public boolean isDataValid(){
        return this.getErrorMsg().isEmpty();
    }

    public String getErrorMessages(){
        String errorMsg = new String();

        for(String msg : this.getErrorMsg()){
            errorMsg = errorMsg.concat(msg);
            errorMsg = errorMsg.concat("\n");
        }

        return errorMsg;
    }

}
