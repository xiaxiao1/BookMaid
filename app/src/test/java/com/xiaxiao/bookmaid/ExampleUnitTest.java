package com.xiaxiao.bookmaid;

import android.util.Log;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        long l=System.currentTimeMillis();
        Date d = new Date(l);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String timeStr=s.format(l);
        System.out.print("nihao"+l+"   "+timeStr);
    }
}