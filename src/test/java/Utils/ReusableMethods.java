package Utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class ReusableMethods {

    public String randomString(int length){

        return RandomStringUtils.randomAlphabetic(length);
    }

    public int randomInt(int length){
        return Integer.parseInt(RandomStringUtils.randomNumeric(length));
    }

    public String randomTrueOrFalse(){
        Random random = new Random();
        int i = random.nextInt(100);

        String res;

        return res = i%2==0 ? "true" : "false";
    }
}
