package com.xiaxiao.bookmaid;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    private static String[] colorNumber={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        long l=System.currentTimeMillis();
        Date d = new Date(l);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String timeStr=s.format(l);
        System.out.print("nihao"+l+"   "+timeStr);
        System.out.print("nihao"+getRandomColor());

    }

    public String getRandomColor() {
        int i;
        StringBuffer sb = new StringBuffer();
        for (int a=0;a<6;a++) {
            i= (int) (Math.random() * 16);
            sb.append(colorNumber[i]);
        }
        return sb.toString();
    }
}