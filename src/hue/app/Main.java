package hue.app;

import com.philips.lighting.hue.sdk.*;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Test");
        HueData hueData = new HueData();

        BufferedReader br;
        PHHueSDK hue = PHHueSDK.create();
        PHBridgeSearchManager sdkService = (PHBridgeSearchManager) hue.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        PHNotificationManager myHue = PHNotificationManager.getDefaultManager();
        myHue.registerSDKListener(new HueListener(hue, sdkService, hueData));

        sdkService.setPortalAddress("192.168.0.1");


        System.out.println("Searching for APs...");
        sdkService.ipAddressSearch();
        while (!hueData.isSearchDone()) {try {Thread.sleep(1000);} catch (InterruptedException ex){ex.getCause();}}

        System.out.println("Hue Bridges found: ");
        for (String ap : hueData.getBridgeList().keySet()) {
            System.out.println("Bridge ID: " + hueData.getBridgeList().get(ap).getBridgeId() + " -- IP: " + ap);
        }

        System.out.println("Please input the IP address of the bridge you wish to use: ");
        String input = null;
        try { br = new BufferedReader(new InputStreamReader(System.in)); input = br.readLine(); br.close();} catch (IOException ex) {ex.getCause();}



        System.out.println(input);



        hue.destroySDK();
    }
}

