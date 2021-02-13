package com.kastik.moriapaneladikon;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void mTest() {
        ArrayList paok = new ArrayList();
        paok.add(4);
        paok.add(3);
        Log.d("as", paok.toString());
    }

}