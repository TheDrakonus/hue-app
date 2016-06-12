package hue.app;

import com.philips.lighting.hue.sdk.*;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

import java.util.*;
import java.io.*;


/**
 * Created by drakonus on 6/11/16.
 */
public class HueListener implements PHSDKListener {

    private HashMap<String, PHAccessPoint> bridgeList = new HashMap<>();
    private PHBridge connectedBridge = null;
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private PHHueSDK hue = null;
    private PHBridgeSearchManager sdkService;

    public HueListener(PHHueSDK hue, PHBridgeSearchManager sdkService) {

        this.hue = hue;
        this.sdkService = sdkService;

    }

    @Override
    public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {
        //@TODO need to figure out what this does
        System.out.println("Cache Updated!");

    }

    @Override
    public void onBridgeConnected(PHBridge phBridge, String s) {

        System.out.println("Bridge Connected!");
        connectedBridge = phBridge;

    }

    @Override
    public void onAuthenticationRequired(PHAccessPoint phAccessPoint) {

        System.out.println("Authentication required. Please input UserName: ");


        String user = null;

        try
        {
            user = br.readLine();
        }
        catch(IOException io)
        {
            io.printStackTrace();
        }

        phAccessPoint.setUsername(user);
    }



    @Override
    public void onAccessPointsFound(List<PHAccessPoint> list) {

        bridgeList.clear();
        for (PHAccessPoint point : list) {
            System.out.println(point.getBridgeId());
            System.out.println(point.getIpAddress());
            bridgeList.put(point.getBridgeId(), point);
        }

    }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onConnectionResumed(PHBridge phBridge) {

    }

    @Override
    public void onConnectionLost(PHAccessPoint phAccessPoint) {

        System.out.println("Bridge connection lost! Reconnect? (y/n) ");

        char choice = ' ';
        try
        {
            choice = br.readLine().charAt(0);
        }
        catch(IOException io)
        {
            io.printStackTrace();
        }

        if (choice == 'y')
        {
            System.out.println("Reconnecting...");
            hue.connect(phAccessPoint);
        }
        else
        {
            System.out.println("Not reconnecting.");
            hue.destroySDK();
            System.exit(0);
        }

    }

    @Override
    public void onParsingErrors(List<PHHueParsingError> list) {

    }
}
