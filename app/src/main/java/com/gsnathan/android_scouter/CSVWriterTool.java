package com.gsnathan.android_scouter;

import android.os.Environment;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class CSVWriterTool {
    private static final String CSV_FILE = "scouter_data.csv";
    private static final String APP_FOLDER = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/DeepScouterApp";

    private File csvFile;

    CSVWriterTool() {
        csvFile = new File(new File(APP_FOLDER), CSV_FILE);
    }

    void writeLineToCSV(String[] data) throws IOException {
        CSVWriter writer;
        //append file
        if (csvFile.exists() && !csvFile.isDirectory()) {
            FileWriter mFileWriter = new FileWriter(csvFile, true);
            writer = new CSVWriter(mFileWriter);
        }
        //else create a file
        else {
            writer = new CSVWriter(new FileWriter(csvFile));
        }
        writer.writeNext(data);
        writer.close();
    }

    void clearCSV() throws IOException {
        CSVWriter writer;
        writer = new CSVWriter(new FileWriter(csvFile));
        writer.close();
    }
}
