package org.aspire.utilities;

import java.util.Random;

public class CommonUtils {

    public static String removeSpecialCharFromString(String tempString,String remChar){
        if (tempString!=null&&!tempString.isEmpty()) {
            String requiredString=tempString.replace("$","");
            return requiredString;
        }
        return tempString;
    }

    public static Double convertStringToDecimal(String tempNumber){
        if (tempNumber!=null&&!tempNumber.isEmpty()) {
            Double number= Double.parseDouble(tempNumber);
            return number;
        }else{
            return null;
        }
    }

    public static int getRandomNumber(){
        Random random=new Random();
        return random.nextInt((9 - 1) + 1) + 1;
    }
}
