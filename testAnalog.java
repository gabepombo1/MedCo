package MedCo3Final;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class testAnalog {

    public static void main(String[] args) throws IOException {

        double randomAnalogTemperature = ((Math.random() * (40 - 36)) + 36);

        //need to enter in file path....
        String analogFileName = "C:\\Users\\gabep\\IdeaProjects\\Assignment7\\src\\MedCo3Final\\analogCheck.txt";
        String analogFileNameHeart = "C:\\Users\\gabep\\IdeaProjects\\Assignment7\\src\\MedCo3Final\\analogCheckStage5.txt";

        Analog analog = new Analog();
        Analog analogHeartBeat = new Analog(Analog.AnalogType.HEART_RATE);

        analog.addLimit("HIGH","High", 38.5, 36);
        analog.addLimit("CRIT","Critical", 39.5, 35);

        analogHeartBeat.addLimit("HIGH", "High", 90, 50);
        analogHeartBeat.addLimit("CRIT", "Critical", 100, 40);
        //throws exception!
        //analog.addLimit("BAD","", 41, 35.3);

        for (int i = 0; i < analog.limitList.size(); i++) {

            analog.limitList.get(i).setEnum();
            analogHeartBeat.limitList.get(i).setEnum();

            Limit testLoopLimit = analog.limitList.get(i);
            Limit testLoopLimitHeart = analogHeartBeat.limitList.get(i);

            String loopID = testLoopLimit.limitInstanceID;
            double lowLimit = testLoopLimit.lowSetting;
            double highLimit = testLoopLimit.highSetting;
            double currentLimitDeadband = testLoopLimit.deadband;
            double currentLimitTemperature = testLoopLimit.currentTemperature;
            boolean currentLimitDoLow = testLoopLimit.doLow;
            boolean currentLimitDoHigh = testLoopLimit.doHigh;

            String loopIDHeart = testLoopLimitHeart.limitInstanceID;
            double lowLimitHeart = testLoopLimitHeart.lowSetting;
            double highLimitHeart = testLoopLimitHeart.highSetting;
            double currentLimitDeadbandHeart = testLoopLimitHeart.deadband;
            double currentLimitTemperatureHeart = testLoopLimitHeart.currentTemperature;
            boolean currentLimitDoLowHeart = testLoopLimitHeart.doLow;
            boolean currentLimitDoHighHeart = testLoopLimitHeart.doHigh;

            System.out.println("Current Analog Type: " + analog.getAnalogType());
            System.out.println("Current State: " + testLoopLimit.currentState);
            System.out.println("Limit[" + i + "] ID: " + loopID + " Lower Limit: "
                    + lowLimit + " Upper Limit: " + highLimit + " Dead-band: " + currentLimitDeadband +" DoLow: " + currentLimitDoLow +
                    " DoHigh: " + currentLimitDoHigh + "\n");

            System.out.println("Current Analog Type: " + analogHeartBeat.getAnalogType());
            System.out.println("Current State: " + testLoopLimitHeart.currentState);
            System.out.println("Limit[" + i + "] ID: " + loopIDHeart + " Lower Limit: "
                    + lowLimitHeart + " Upper Limit: " + highLimitHeart + " Dead-band: " + currentLimitDeadbandHeart +" DoLow: " + currentLimitDoLowHeart +
                    " DoHigh: " + currentLimitDoHighHeart + "\n");


            //System.out.println("Current State: " + testLoopLimit.currentState);

            //System.out.println(testLoopLimit.checkLimit(41));
        }

        analog.limitList.remove(0);

        System.out.println("\nARRAYLIST WITH REMOVED INDEX:\n");

        for(int i = 0; i < analog.limitList.size(); i++) {

            analog.limitList.get(i).setEnum();
            analogHeartBeat.limitList.get(i).setEnum();

            Limit testLoopLimit = analog.limitList.get(i);
            Limit testLoopLimitHeart = analogHeartBeat.limitList.get(i);

            String loopID = testLoopLimit.limitInstanceID;
            double lowLimit = testLoopLimit.lowSetting;
            double highLimit = testLoopLimit.highSetting;
            double currentLimitDeadband = testLoopLimit.deadband;
            double currentLimitTemperature = testLoopLimit.currentTemperature;
            boolean currentLimitDoLow = testLoopLimit.doLow;
            boolean currentLimitDoHigh = testLoopLimit.doHigh;

            String loopIDHeart = testLoopLimitHeart.limitInstanceID;
            double lowLimitHeart = testLoopLimitHeart.lowSetting;
            double highLimitHeart = testLoopLimitHeart.highSetting;
            double currentLimitDeadbandHeart = testLoopLimitHeart.deadband;
            double currentLimitTemperatureHeart = testLoopLimitHeart.currentTemperature;
            boolean currentLimitDoLowHeart = testLoopLimitHeart.doLow;
            boolean currentLimitDoHighHeart = testLoopLimitHeart.doHigh;

            System.out.println("Current Analog Type: " + analog.getAnalogType());
            System.out.println("Current State: " + testLoopLimit.currentState);
            System.out.println("Limit[" + i + "] ID: " + loopID + " Lower Limit: "
                    + lowLimit + " Upper Limit: " + highLimit + " Dead-band: " + currentLimitDeadband + " DoLow: " + currentLimitDoLow +
                    " DoHigh: " + currentLimitDoHigh + "\n");

            System.out.println("Current Analog Type: " + analogHeartBeat.getAnalogType());
            System.out.println("Current State: " + testLoopLimitHeart.currentState);
            System.out.println("Limit[" + i + "] ID: " + loopIDHeart + " Lower Limit: "
                    + lowLimitHeart + " Upper Limit: " + highLimitHeart + " Dead-band: " + currentLimitDeadbandHeart + " DoLow: " + currentLimitDoLowHeart +
                    " DoHigh: " + currentLimitDoHighHeart + "\n");

            //analog.checkAnalog(32, analogFileName);

            //System.out.println(testLoopLimit.checkLimit(41));
        }

        analog.checkAnalog(40, analogFileName);
        analog.checkAnalog(34, analogFileName);
        analog.checkAnalog(59, analogFileName);

        analogHeartBeat.checkAnalog(100, analogFileNameHeart);
        analogHeartBeat.checkAnalog(60, analogFileNameHeart);
        analogHeartBeat.checkAnalog(40, analogFileNameHeart);

    }

}
