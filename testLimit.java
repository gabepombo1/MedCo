package MedCo3Final;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class testLimit {

    public static void main(String[] args) throws IOException {

        Limit testLimit = new Limit();
        String fileName = "C:\\Users\\gabep\\IdeaProjects\\Shitty\\src\\MedCo2\\limitCheck.txt";
        Scanner keyboard = new Scanner(System.in);

        testLimit.mainMenu();
        testLimit.runCustomization(testLimit.customize);

        System.out.println(testLimit.setEnum());
        testLimit.writeToFile(testLimit.getTemperature(), fileName);

    }

}
