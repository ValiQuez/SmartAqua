package ca.buckleupinc.it.smartaqua;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SmartAquaDownloaderManager {
    private final Context context;

    public SmartAquaDownloaderManager(Context context) {
        this.context = context;
    }

    public void saveDataToFile(String reading, String status) {
        String folderName = "SmartAquaQualityReadings";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        // Save JSON data
        String jsonFileName = "SmartAquaReading_" + timeStamp + ".json";
        File folder = new File(context.getExternalFilesDir(null), folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File jsonFile = new File(folder, jsonFileName);

        try {
            FileOutputStream jsonFileOutputStream = new FileOutputStream(jsonFile);
            OutputStreamWriter jsonOutputStreamWriter = new OutputStreamWriter(jsonFileOutputStream);

            JSONObject dataObject = new JSONObject();
            dataObject.put("Reading", reading);
            dataObject.put("Status", status);
            dataObject.put("DateTime", timeStamp);

            jsonOutputStreamWriter.write(dataObject.toString());

            jsonOutputStreamWriter.close();
            jsonFileOutputStream.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, R.string.wq_json_error, Toast.LENGTH_SHORT).show();
        }

        // Save text data
        String textFileName = "SmartAquaReading_" + timeStamp + ".txt";
        File textFile = new File(folder, textFileName);

        try {
            FileOutputStream textFileOutputStream = new FileOutputStream(textFile);
            OutputStreamWriter textOutputStreamWriter = new OutputStreamWriter(textFileOutputStream);

            textOutputStreamWriter.write("Reading: " + reading + "\n");
            textOutputStreamWriter.write("Status: " + status + "\n");
            textOutputStreamWriter.write("DateTime: " + timeStamp + "\n");

            textOutputStreamWriter.close();
            textFileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, R.string.wq_txt_error, Toast.LENGTH_SHORT).show();
        }
    }
}

