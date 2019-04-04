package com.gsnathan.android_scouter;

import android.os.Environment;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

class CSVWriterTool {
    private static final String CSV_FILE = "scouter_data.csv";
    private static final String APP_FOLDER = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/DeepScouter";

    private File csvFile;

    CSVWriterTool() {
        File folder = new File(APP_FOLDER);
        if (!folder.isDirectory()) {
            folder.mkdir();
        }
        csvFile = new File(folder, CSV_FILE);
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
            if (!csvFile.exists()) {
                try {
                    csvFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

    ArrayList<String[]> readCsvAsList() throws IOException {
        ArrayList<String[]> csvList = new ArrayList<String[]>();

        FileReader reader = new FileReader(csvFile);
        CSVReader csvReader = new CSVReader(reader);
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            csvList.add(nextLine);
        }

        return csvList;

    }
}

