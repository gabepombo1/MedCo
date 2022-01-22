package MedCo3Final;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Analog {

    /**
     *  @author Gabriel Pombo
     *  MedCo Stage III
     */

    public enum AnalogType{

        HEART_RATE,

        TEMP

    }

    public ArrayList<Limit> limitList = new ArrayList<Limit>(1000);

    Limit.LimitState currentState;

    String currentID;

    private double temperature;

    private AnalogType analogType = AnalogType.TEMP;

    String alarmIDMessage;
    String units;
    String printStr;

    public Analog(){

        limitList.add(new Limit());
        this.currentID = limitList.get(0).limitInstanceID;
        this.currentState = limitList.get(0).currentState;

    }

    public Analog(String thing){

        System.out.println("Temperature Analog");

        for(int i = 0; i < limitList.size(); i++){

            System.out.println(limitList.get(i));

        }

    }

    public Analog(AnalogType analogTypeInput){

        switch (analogTypeInput) {

            case TEMP -> {
                analogType = AnalogType.TEMP;
                units = "C";
                printStr = "Temperature";
                //limit already has a default temperature setting when Limit() is called
                limitList.add(new Limit());
            }

            case HEART_RATE -> {
                analogType = AnalogType.HEART_RATE;
                units = "BPM";
                printStr = "Heart Rate";
                limitList.add(new Limit(50, 90));
            }

        }

    }

    public Analog(AnalogType analogTypeInput, String printStrInput, String unitsInput){

        this.analogType = analogTypeInput;
        this.printStr = printStrInput;
        this.units = unitsInput;

        switch (analogTypeInput) {

            case TEMP -> {
                //limit already has a default temperature setting when Limit() is called
                limitList.add(new Limit());
            }

            case HEART_RATE -> {
                limitList.add(new Limit(50, 90));
            }

        }

    }

    public AnalogType getAnalogType() {

        return analogType;

    }

    /**
     * Adds a new Limit object to the limits ArrayList
     * @param instanceIDInput
     * @param alarmStringInput
     * @param inputLow
     * @param inputHigh
     */
    public void addLimit(String instanceIDInput, String alarmStringInput, double inputHigh, double inputLow){

        //double random = ((Math.random() * (40 - 36)) + 36);

        Limit previousLimit = limitList.get(limitList.size() - 1);

        //Limit limitInstance = new Limit(inputLow, inputHigh, alarmStringInput, instanceIDInput);
        if(inputHigh >= previousLimit.highSetting && inputLow <= previousLimit.lowSetting) {

            limitList.add(new Limit(inputLow, inputHigh, alarmStringInput, instanceIDInput));

        }
        else {

            throw new IllegalArgumentException();

        }

    }

    /**
     * Adds a new Limit object to the limits ArrayList
     * @param instanceIDInput
     * @param alarmStringInput
     * @param inputLow
     * @param inputHigh
     */
    public void addLimit(String instanceIDInput, String alarmStringInput, boolean doHighInput,
                         double inputHigh, boolean doLowInput, double inputLow){

        //double random = ((Math.random() * (40 - 36)) + 36);

        Limit previousLimit = limitList.get(limitList.size() - 1);

        //Limit limitInstance = new Limit(inputLow, inputHigh, alarmStringInput, instanceIDInput);
        if(inputHigh >= previousLimit.highSetting && inputLow <= previousLimit.lowSetting) {

            limitList.add(new Limit(inputLow, inputHigh, alarmStringInput, instanceIDInput));

        }
        else {

            throw new IllegalArgumentException();

        }

    }

    /**
     * Deletes a Limit with matching ID from the limitList ArrayList
     * @param idToDelete the value to find and delete
     * @return
     */
    public boolean deleteLimit(String idToDelete){

        boolean isDeleted = false;

        for(int i = 0; i < limitList.size(); i++){

            String limitInstanceID = limitList.get(i).limitInstanceID;

            if(limitInstanceID.equals(idToDelete)){

                Limit limitInstance = limitList.get(i);
                limitList.remove(limitInstance);

            }

        }

        return isDeleted;

    }

    /**
     * Checks to see if the state has changed
     * @param analogTemperatureInput
     * @param fileName
     */
    public void checkAnalog(double analogTemperatureInput, String fileName) throws IOException, IllegalArgumentException {

        //File file = new File(fileName);
        //PrintWriter outputFile = new PrintWriter(new FileWriter(fileName, true));

        Limit loopLimit;
        Limit.LimitState changedState = Limit.LimitState.NORMAL;
        String changedID = "";
        double changedHigh = 0.0;
        double changedLow = 0.0;
        String changedAlarmMessage = "";
        Clock clock = new Clock();

        switch (this.analogType) {
            case TEMP:
                units = "C";
                printStr = "";
            case HEART_RATE:
                units = "BPM";
                printStr = "Heart Rate";
        }

        for(int i = 0; i < limitList.size(); i++) {

            loopLimit = limitList.get(i);
            //loopLimit.currentTemperature = analogTemperatureInput;
            if(loopLimit.checkLimit(analogTemperatureInput) != Limit.LimitState.NORMAL){

                changedLow = loopLimit.lowSetting;
                changedHigh = loopLimit.highSetting;
                changedState = loopLimit.currentState;
                changedID = loopLimit.limitInstanceID;
                changedAlarmMessage = loopLimit.analogAlarmMessage;

            }

            if(changedState == currentState){

                return;

            }

            currentState = changedState;
            currentID = changedID;

            try(PrintWriter outputFile = new PrintWriter(new FileWriter(fileName, true))){

                if (changedState == Limit.LimitState.HIGH ){

                    changedAlarmMessage = "High";

                    outputFile.printf("%s: %s : " + printStr + " is currently %.1f\u2103! " +
                                    "This is over the limit of %.1f" + units + "!\n",
                            clock, changedAlarmMessage ,analogTemperatureInput, changedHigh);

                }
                else if (changedState == Limit.LimitState.LOW){

                    changedAlarmMessage = "Low";

                    outputFile.printf("%s: %s : " + printStr + " is currently %.1f" + units + "! " +
                            "This is under the limit of %.1f\u2103!\n", clock, changedAlarmMessage, analogTemperatureInput, changedLow);

                }
                else if (changedState == Limit.LimitState.NORMAL){

                    outputFile.printf("%s Return: " + printStr + " is currently in range: %.1f" + units + "!\n", clock, analogTemperatureInput);

                }
            } catch (IOException e){

                throw e;

            }

        }

    }

}
