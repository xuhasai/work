package work.util;

import java.util.ArrayList;
import java.util.List;

public class EncryptPassword {

    public static String encryptPassword(String password){
        List<Integer> ints = new ArrayList<Integer>();
        String encryptPassword = "";
        for(int i=0;i<password.length();i++) {
            int a = (int)(Math.random()*50);
            char c = password.charAt(i);
            int j = c;
            j = j*a;
            char c2 = (char)a;
            if(i != password.length()-1) {
                encryptPassword += j+""+c2+"-";
            }else {
                encryptPassword += j+""+c2;
            }
        }
        return encryptPassword;
    }

}
