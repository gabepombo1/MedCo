package MedCo3Final;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Limit {

    enum LimitState {

        LOW,

        NORMAL,

        HIGH

    }

    public LimitState currentState = LimitState.NORMAL;

    public String analogAlarmMessage;

    public double currentTemperature;
    public double lowSetting;
    public double highSetting;
    public double deadband = 1.0;
    public Clock time = new Clock();
    public String lowAlarmMessage;
    public String normalTemperatureMessage;
    public String highAlarmMessage;
    public String limitInstanceID;
    public boolean doLow = true;
    public boolean doHigh = true;
    public boolean customize = false;

    LimitState lowAlert = LimitState.LOW;
    LimitState normalAlert = LimitState.NORMAL;
    LimitState highAlert = LimitState.HIGH;

    public Limit(){

        this.highSetting = 37.6;
        this.lowSetting = 36.5;
        setTemperature();
        this.analogAlarmMessage = "";

    }

    public Limit(double lowSettingInput, double highSettingInput){

        this.lowSetting = lowSettingInput;
        this.highSetting = highSettingInput;
        this.analogAlarmMessage = "";

    }

    //sets lower and upper limit
    public Limit(double lowSettingInput, double highSettingInput, String alarmMessageInput, String instanceID){

        this.lowSetting = lowSettingInput;

        this.highSetting = highSettingInput;

        setTemperature();

        setEnum();

        //input into the method that makes the file
        this.analogAlarmMessage = alarmMessageInput;

        this.limitInstanceID = instanceID;

    }

    public Limit(boolean doLowInput,double lowSettingInput, boolean doHighInput, double highSettingInput){



    }

    public void mainMenu(){

        Scanner keyboard = new Scanner(System.in);

        boolean runMenu = true;

        while (runMenu) {

            System.out.println("""
                    ////////////////////////////////////////
                    ///Welcome to MedCo Thermometer v1.0!///
                    ////////////////////////////////////////\s""");

            System.out.println("""
                    ////////////////////////////////////////
                    /////Do you want to customize your /////
                    ///thermometer? (Type 'yes' or 'no')////
                    ////////////////////////////////////////\s""");

            if (keyboard.nextLine().equals("yes")) {

                customize = true;
                runMenu = false;

            } else if (!keyboard.nextLine().equals("yes") || !keyboard.nextLine().equals("no")) {

                throw new IllegalArgumentException("ILLEGAL ARGUMENT! THIS IS CASE SENSITIVE!\n Enter in 'yes', or 'no'.");

            } else {

                customize = false;
                runMenu = false;

            }

        }

    }

    //sets temperature to a random temperature in the lowSetting - highSetting range
    public void setTemperature(){

        double temperatureInput = Math.random() * ((highSetting + 0.55) - (lowSetting - 0.55) + 1) + lowSetting;

        this.currentTemperature = temperatureInput;

    }

    public double getTemperature(){

        return currentTemperature;

    }

    //sets the currentState depending on currentTemperature
    public String setEnum(){
        //sets high alert limit
        if(currentTemperature > highSetting + deadband){

            currentState = highAlert;

            return "LimitState is HIGH! Temperature: " + currentTemperature;

        }
        //sets low alert limit
        if(currentTemperature < lowSetting - deadband){

            currentState = lowAlert;

            return "LimitState is LOW! Temperature: " + currentTemperature;

        }
        //else sets the temperature to NORMAL
        else{

            return "LimitState is NORMAL! Temperature: " + currentTemperature;

        }

    }

    public boolean disableLow(){

        boolean change = false;

        if(this.doLow){

            change = true;

        }

        doLow = false;

        return change;

    }

    public boolean disableHigh(){

        boolean change = false;

        if(this.doHigh){

            change = true;

        }

        doHigh = false;

        return change;

    }

    public boolean enableLow(){

        boolean change = false;

        if(!this.doLow){

            change = true;

        }

        doLow = true;

        return change;

    }

    public boolean enableHigh(){

        boolean change = false;

        if(!this.doHigh){

            change = true;

        }

        doHigh = true;

        return change;

    }

    public String checkLimit(Limit limitInput){
        //sets high alert limit
        if(limitInput.currentTemperature > limitInput.highSetting + limitInput.deadband){

            limitInput.currentState = limitInput.highAlert;

            return "LimitState is HIGH! Temperature: " + limitInput.currentTemperature;

        }
        //sets low alert limit
        if(limitInput.currentTemperature < limitInput.lowSetting - limitInput.deadband){

            limitInput.currentState = limitInput.lowAlert;

            return "LimitState is LOW! Temperature: " + limitInput.currentTemperature;

        }
        //else sets the temperature to NORMAL
        else{

            return "LimitState is NORMAL! Temperature: " + limitInput.currentTemperature;

        }

    }

    public LimitState checkLimit(double temperatureInput){
        //sets high alert limit
        if(temperatureInput > highSetting + deadband){

            //currentState = highAlert;

            return currentState = LimitState.HIGH;

        }
        //sets low alert limit
        if(temperatureInput < lowSetting - deadband){

            //currentState = lowAlert;

            return currentState = LimitState.LOW;

        }
        //else sets the temperature to NORMAL
        else{

            //currentState = normalAlert;

            return currentState = LimitState.NORMAL;

        }

    }

    public void runCustomization(boolean customOrNot){
        //use JOptionPane

        String deadBandRequestMessage = """
                ////////////////////////////////////////
                /////Enter in the desired dead-band/////
                ////////////////////////////////////////\s""";

        Scanner keyboard = new Scanner(System.in);

        String formattingHelpMessage = """
                    ////////////////////////////////////////
                    ///Use back slash 'n' to skip a line!///
                    ////////////////////////////////////////\s""";

        if(customOrNot){

            System.out.println("\n"+ """
                    ////////////////////////////////////////
                    ///Enter in your custom 'Alarm' alert///
                    //////////for the 'Low' limit://////////
                    ////////////////////////////////////////\s""");

            System.out.println(formattingHelpMessage);

            lowAlarmMessage = keyboard.nextLine();

            System.out.println("""
                    ////////////////////////////////////////
                    ///Enter in your custom 'Alarm' alert///
                    //////// for the 'Normal' range:////////
                    ////////////////////////////////////////\s""");

            System.out.println(formattingHelpMessage);

            normalTemperatureMessage = keyboard.nextLine();

            System.out.println("""
                    ////////////////////////////////////////
                    ///Enter in your custom 'Alarm' alert///
                    /////////for the 'High' limit: /////////
                    ////////////////////////////////////////\s""");

            System.out.println(formattingHelpMessage);

            highAlarmMessage = keyboard.nextLine();
            //print dead-band message
            System.out.println(deadBandRequestMessage);

            deadband = keyboard.nextDouble();

        }

    }

    public void writeToFile(double currentTemperatureInput, String fileName) throws IOException, IllegalArgumentException {

        //File file = new File(fileName);
        PrintWriter outputFile = new PrintWriter(new FileWriter(fileName, true));

        switch (currentState) {

            case LOW:
                outputFile.printf("%s" + lowAlarmMessage + "Temperature is currently %.1f\u2103! " +
                        "This is under the limit of %.1f\u2103!\n", time, currentTemperatureInput, lowSetting);
                outputFile.close();
                break;
            case NORMAL:
                outputFile.printf("%s" + normalTemperatureMessage + " Temperature is currently in range: %.1f\u2103.\n",
                        time, currentTemperatureInput);
                outputFile.close();
                break;
            case HIGH:
                outputFile.printf("%s " + highAlarmMessage + " Temperature is currently %.1f\u2103 " +
                        "This is over the limit of %.1f\u2103 of MT instance '%s'!\n"
                        , time, currentTemperatureInput, highSetting, limitInstanceID);
                outputFile.close();
                break;

        }

    }

}
