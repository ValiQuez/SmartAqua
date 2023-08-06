/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

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
    private static final String FOLDER_NAME = "SmartAquaQualityReadings";
    private static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    private static final String FILE_NAME = "SmartAquaReading_";
    private static final String JSON_EXT = ".json";
    private static final String TXT_EXT = ".txt";
    private static final String READING = "Reading";
    private static final String STATUS = "Status";
    private static final String DATE_TIME = "Date_Time";
    private static final String SPACE = "\n";
    private static final String COLON = ": ";

    public SmartAquaDownloaderManager(Context context) {
        this.context = context;
    }

    public void saveWQDataToFile(String reading, String status) {
        String folderName = FOLDER_NAME;
        String timeStamp = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date());

        // Save JSON data
        String jsonFileName = FILE_NAME + timeStamp + JSON_EXT;
        File folder = new File(context.getExternalFilesDir(null), folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File jsonFile = new File(folder, jsonFileName);

        try {
            FileOutputStream jsonFileOutputStream = new FileOutputStream(jsonFile);
            OutputStreamWriter jsonOutputStreamWriter = new OutputStreamWriter(jsonFileOutputStream);

            JSONObject dataObject = new JSONObject();
            dataObject.put(READING, reading);
            dataObject.put(STATUS, status);
            dataObject.put(DATE_TIME, timeStamp);

            jsonOutputStreamWriter.write(dataObject.toString());

            jsonOutputStreamWriter.close();
            jsonFileOutputStream.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, R.string.wq_json_error, Toast.LENGTH_SHORT).show();
        }

        // Save text data
        String textFileName = FILE_NAME + timeStamp + TXT_EXT;
        File textFile = new File(folder, textFileName);

        try {
            FileOutputStream textFileOutputStream = new FileOutputStream(textFile);
            OutputStreamWriter textOutputStreamWriter = new OutputStreamWriter(textFileOutputStream);

            textOutputStreamWriter.write(READING + COLON + reading + SPACE);
            textOutputStreamWriter.write(STATUS + COLON + status + SPACE);
            textOutputStreamWriter.write(DATE_TIME + COLON + timeStamp + SPACE);

            textOutputStreamWriter.close();
            textFileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, R.string.wq_txt_error, Toast.LENGTH_SHORT).show();
        }
    }
}

