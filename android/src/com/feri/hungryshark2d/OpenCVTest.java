package com.feri.hungryshark2d;

import android.util.Log;

import org.opencv.android.OpenCVLoader;

public class OpenCVTest {

   public static void jejmiga(){
        if(OpenCVLoader.initDebug()){
            Log.d("TAG", "DELA");
        }
        else{
            Log.d("TAG", "NE DELA");
        }
    }
}
