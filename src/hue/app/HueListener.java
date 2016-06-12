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
    private BufferedReader br;
    private PHHueSDK hue = null;
    private PHBridgeSearchManager sdkService;
    private HueData hueData;

    public HueListener(PHBridgeSearchManager sdkService, HueData hueData) {

        this.hue = PHHueSDK.getInstance();
        this.sdkService = sdkService;
        this.hueData = hueData;

    }

    @Override
    public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {
        //@TODO need to figure out what this does
        System.out.println("Cache Updated!");

    }

    @Override
    public void onBridgeConnected(PHBridge phBridge, String s) {

        hue.setSelectedBridge(phBridge);
        hue.enableHeartbeat(phBridge, PHHueSDK.HB_INTERVAL);
        hueData.setSelectedBridge(phBridge);
        System.out.println("Bridge Connected!");
        System.out.println("UserName: " + s);
    }

    @Override
    public void onAuthenticationRequired(PHAccessPoint phAccessPoint) {

        System.out.println("Authentication required. Please push link button within 30 seconds ");
        hue.setAppName("hue-app");
        hue.setDeviceName("Java-PC");

        hue.startPushlinkAuthentication(phAccessPoint);


/*
        String user = null;

        try
        {
            br = new BufferedReader(new InputStreamReader(System.in));
            user = br.readLine();
            br.close();
        }
        catch(IOException io)
        {
            io.printStackTrace();
        }

        phAccessPoint.setUsername(user);
        */
    }



    @Override
    public void onAccessPointsFound(List<PHAccessPoint> list) {

        bridgeList.clear();
        for (PHAccessPoint point : list) {
            //System.out.println(point.getBridgeId());
            //System.out.println(point.getIpAddress());
            bridgeList.put(point.getIpAddress(), point);
        }
        hueData.setBridgeList(bridgeList);
        hueData.setSearchDone(true);
    }

    @Override
    public void onError(int i, String s) {
        System.out.println(s);
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
            br = new BufferedReader(new InputStreamReader(System.in));
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
