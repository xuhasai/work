package hasai.util;

public class DecryptPassword {
    public static String decryptPassword(String password){
        String[] charpass = password.split("-");
        String decryptPassword = "";
        for(int i=0;i<charpass.length;i++) {
            String str2 = charpass[i];
            int j = (int)str2.charAt(charpass[i].length()-1);
            int h = Integer.parseInt(str2.substring(0, str2.length()-1));
            int z = h/j;
            char c = (char)z;
            decryptPassword += c;
        }
        return decryptPassword;
    }



}
