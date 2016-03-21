package com.zmousa.androidbackup.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.zmousa.androidbackup.application.BackupApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Utilities {

    public Utilities() {
    }

    public static void toast(String msg) {
        toast(BackupApplication.getContext(), msg);
    }

    public static void toast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static class DisplayToastInRunnable implements Runnable {
        String mText;

        public DisplayToastInRunnable(String text){
            mText = text;
        }

        public void run(){
            toast(mText);
        }
    }

    public static void callService(Class<?> newActivityClass)
    {
        Intent nIntent = new Intent(BackupApplication.getContext(), newActivityClass);
        BackupApplication.getContext().startService(nIntent);
    }

    public static String getDataFilePathWithFolder(String folderName, String fileName)
    {
        ContextWrapper cw = new ContextWrapper(BackupApplication.getContext());
        File directory = cw.getDir(folderName, Context.MODE_PRIVATE);

        if (!directory.exists())
            directory.mkdirs();
        File file = new File(directory, fileName);
        return file.getAbsolutePath();
    }

    public static File getDataFolder(String folderName)
    {
        ContextWrapper cw = new ContextWrapper(BackupApplication.getContext());
        File directory = cw.getDir(folderName, Context.MODE_PRIVATE);

        if (!directory.exists())
            directory.mkdirs();
        return directory;
    }

    public static String checkNullString(String input)
    {
        if(input!=null)
            return input;
        return "";
    }

    public static String getFileContent(File file) {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
            return text.toString();
        } catch (IOException e) {
            Log.i("service:error", e.toString());
            return "101";
        }
    }

    public static StringBuilder getFileContent(File file, StringBuilder text) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            text.setLength(0);
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
            return text;
        } catch (IOException e) {
            Log.i("service:error", e.toString());
            text.setLength(0);
            text.append("101");
            return text;
        }
    }

    public static String getImageAsString(File file) {
        String imageDataString = "";
        try {
            // Reading a Image file from file system
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);

            // Converting Image byte array into Base64 String
            imageDataString = encodeImage(imageData);

            /*// Converting a Base64 String into Image byte array
            byte[] imageByteArray = decodeImage(imageDataString);

            // Write a image byte array into file system
            FileOutputStream imageOutFile = new FileOutputStream(
                    "/Users/jeeva/Pictures/wallpapers/water-drop-after-convert.jpg");

            imageOutFile.write(imageByteArray);

            imageInFile.close();
            imageOutFile.close();*/
        } catch (FileNotFoundException e) {
            Log.i("service:error", "Image Not Found");
        } catch (IOException ioe) {
            Log.i("service:error", ioe.toString());
        }
        return imageDataString;
    }

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    public static byte[] getFileAsBytes(File file) {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte fileContent[] = new byte[(int) file.length()];
            fin.read(fileContent);
            return fileContent;
        } catch (Exception e){

        }
        return null;
    }
}
