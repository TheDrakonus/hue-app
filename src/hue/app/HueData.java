package hue.app;

import com.philips.lighting.hue.sdk.PHAccessPoint;

import java.util.HashMap;

/**
 * Created by drakonus on 6/12/16.
 */
class HueData {

    private HashMap<String, PHAccessPoint> bridgeList = new HashMap<>();
    private PHAccessPoint selectedAP = null;
    private boolean searchDone = false;


    public void setSearchDone(boolean done){
        this.searchDone = done;
    }

    public boolean isSearchDone() {
        return searchDone;
    }

    public HueData()
    {

    }
    public HashMap<String, PHAccessPoint> getBridgeList() {
        return bridgeList;
    }

    public void setBridgeList(HashMap<String, PHAccessPoint> bridgeList) {
        this.bridgeList = bridgeList;
    }

    public void userSelectAP(PHAccessPoint ap) {

    }









}
