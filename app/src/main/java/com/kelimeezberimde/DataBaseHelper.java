package com.kelimeezberimde;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static String DB_PATH = "/data/data/com.kelimeezberimde/databases/";
    private static String DB_NAME = "Kelime_Ezberimde";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, VERSION);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {
        System.out.println("create database");
        boolean dbExist = checkDataBase();

        if(dbExist){

        }else{

            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");

            }
        }

    }

    public boolean checkDataBase(){

        File dbFile = myContext.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    public void copyDataBase() throws IOException{


        System.out.println("copy database");
        InputStream myInput = myContext.getAssets().open(DB_NAME);


        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);


        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("fatih","sqlhatası");
                ;
            }


    }
}