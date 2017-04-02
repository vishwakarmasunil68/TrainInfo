package com.sundroid.traininfo.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sunil on 29-03-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DB_PATH ="data/data/com.sundroid.traininfo/databases";
    //"/data/data/com.tst/databases/";

    public static String DB_NAME = "traindb";

    public static SQLiteDatabase _database;

    private final Context myContext;
    public static String apstorphe = "'";
    public static String sep = ",";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH = myContext.getFilesDir().toString() + "/";
    }

    private static DatabaseHelper instance;
    public static synchronized DatabaseHelper getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }
    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.

            try {
                copyDataBase();
                Log.i("database created", "");
            } catch (IOException e) {
                Log.e("error", e.toString());
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
//		SQLiteDatabase checkDB = null;
//
//		try {
//			String myPath = DB_PATH + DB_NAME;
//			checkDB = SQLiteDatabase.openDatabase(myPath, null,
//					SQLiteDatabase.OPEN_READWRITE);
//		} catch (SQLiteException e) {
//			// database does't exist yet.
//		}
//
//		if (checkDB != null) {
//			checkDB.close();
//		}
//
//		return checkDB != null ? true : false;
        try {
            final String mPath = DB_PATH + DB_NAME;
            final File file = new File(mPath);
            if (file.exists())
                return true;
            else
                return false;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void copyDataBase() throws IOException {

//		// Open your local db as the input stream
//		InputStream myInput = myContext.getAssets().open(DB_NAME);
//
//		// Path to the just created empty db
//		String outFileName = DB_PATH + DB_NAME;
//
//		// Open the empty db as the output stream
//		OutputStream myOutput = new FileOutputStream(outFileName);
//
//		// transfer bytes from the inputfile to the outputfile
//		byte[] buffer = new byte[2048];
//		int length;
//		while ((length = myInput.read(buffer)) > 0) {
//			myOutput.write(buffer, 0, length);
//		}
//
//		// Close the streams
//		myOutput.flush();
//		myOutput.close();
//		myInput.close();
        try {

            InputStream mInputStream = myContext.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream mOutputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = mInputStream.read(buffer)) > 0) {
                mOutputStream.write(buffer, 0, length);
            }
            mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    SQLiteDatabase openDataBase() throws SQLException {
        // Open the database
        if (_database == null) {
            _database = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
                    SQLiteDatabase.OPEN_READWRITE
                            | SQLiteDatabase.CREATE_IF_NECESSARY);
        } else if (!_database.isOpen()) {
            _database = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
                    SQLiteDatabase.OPEN_READWRITE
                            | SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        return _database;
    }

    public static void closedatabase() {
        if (_database != null)
            _database.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


	/*public static Cursor get(String query_str)
	{
		Cursor c;
		if (_database != null)
		{
			try
			{
				openDataBase();
				c = _database.rawQuery(query_str, null);
				return c;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
		else
			return null;
	}*/

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it return dir.delete(); }
        return dir.delete();
    }

}
