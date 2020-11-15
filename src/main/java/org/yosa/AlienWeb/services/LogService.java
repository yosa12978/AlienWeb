package org.yosa.AlienWeb.services;

import java.util.Arrays;
import java.util.Date;

public class LogService {

    public void log(Object inputData){
        Arrays.stream(inputData.toString().split("[\r\n]+")).forEach((data) ->{
            System.out.println("AlienWeb ["+ new Date().getTime() +"] - " + data);
        });
    }
}
