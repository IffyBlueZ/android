package com.jnu.myapplication.data;

import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataScore  {
    final String DATA_FILENAME = "score.data";
    private static final String TAG = "Serialization";

    public int loadScore(Context context) {
        int score = 0;
        try {
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            score = objectIn.readInt();
            objectIn.close();
            fileIn.close();
            Log.d(TAG, "Data loaded successfully: score = " + score);
        } catch (IOException e) {
            Log.d(TAG, "No data found, initializing score to 0");
        }
        return score;
    }

    public void saveScore(Context context, int score) {
        try {
            FileOutputStream fileOut = context.openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeInt(score);
            out.close();
            fileOut.close();
            Log.d(TAG, "Score is saved: score = " + score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
