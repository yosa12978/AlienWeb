package org.yosa.AlienWeb.services;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class LogService {

    public void log(Object inputData){
        Arrays.stream(inputData.toString().split("[\r\n]+")).forEach((data) ->{
            System.out.println("\u001B[0m"+ "AlienWeb ["+ new Date().getTime() +"] - " + data);
        });
    }

    public String read(Scanner scan, String before){
        System.out.print("AlienWeb ["+ new Date().getTime() +"] - "+before);
        String data = scan.nextLine();
        return data;
    }

    public String read(Scanner scan){
        System.out.print("AlienWeb ["+ new Date().getTime() +"] - ");
        String data = scan.nextLine();
        return data;
    }
}
