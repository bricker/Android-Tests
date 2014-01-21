package com.bryanricker.criminal_intent;

import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class CriminalIntentJSONSerializer
{
    private Context mContext;
    private String mFilename;

    public CriminalIntentJSONSerializer(Context c, String f)
    {
        mContext = c;
        mFilename = f;
    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException
    {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        InputStream in;
        StringBuilder jsonString = new StringBuilder();
        String line;

        try
        {
            if (externalStorageMounted())
            {
                String root = Environment.getExternalStorageDirectory().getAbsolutePath();
                File file = new File(root, mFilename);
                in = new FileInputStream(file);
            } else {
                in = mContext.openFileInput(mFilename);
            }

            reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) jsonString.append(line);
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for (int i = 0; i < array.length(); i++)
            {
                crimes.add(new Crime(array.getJSONObject(i)));
            }

        } catch (FileNotFoundException e) {
        } finally {
            if (reader != null) reader.close();
        }

        return crimes;
    }


    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException
    {
        JSONArray array = new JSONArray();
        for (Crime c : crimes) array.put(c.toJSON());

        Writer writer = null;
        OutputStream os;

        try
        {
            if (externalStorageMounted())
            {
                String root = Environment.getExternalStorageDirectory().getAbsolutePath();
                File file = new File(root, mFilename);
                os = new FileOutputStream(file);
            } else {
                os = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            }

            writer = new OutputStreamWriter(os);
            writer.write(array.toString());

        } finally {
            if (writer != null) writer.close();
        }
    }

    private boolean externalStorageMounted()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
