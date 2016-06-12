package hue.app;

import com.philips.lighting.hue.sdk.*;


public class Main {

    public static void main(String[] args) {

        System.out.println("Test");

        PHHueSDK hue = PHHueSDK.create();
        PHBridgeSearchManager sdkService = (PHBridgeSearchManager)hue.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        PHNotificationManager myHue = PHNotificationManager.getDefaultManager();

    }

}