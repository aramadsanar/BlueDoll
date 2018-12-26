package com.armadanasar.bluedoll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteDatabaseHelper  extends SQLiteOpenHelper {
    private static final String DBNAME = "BlueDoll.db";
    public static final int DBVER = 1;

    public static final String DOLL_TABLE_NAME = "dolls";
    public static final String DOLL_TABLE_ID_COLUMN = "id";
    public static final String DOLL_TABLE_NAME_COLUMN = "name";
    public static final String DOLL_TABLE_DESC_COLUMN = "desc";
    public static final String DOLL_TABLE_IMAGE_COLUMN = "image";


    public static final String USER_TABLE_NAME = "users";
    public static final String USER_TABLE_ID_COLUMN = "id";
    public static final String USER_TABLE_NAME_COLUMN = "name";
    public static final String USER_TABLE_EMAIL_COLUMN = "email";
    public static final String USER_TABLE_PASSWORD_COLUMN = "password";


    public SQLiteDatabaseHelper(Context context) {
        super(context, DBNAME, null, DBVER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME + "("
                + USER_TABLE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_TABLE_NAME_COLUMN+ " VARCHAR(255),"
                + USER_TABLE_EMAIL_COLUMN+ " VARCHAR(255) UNIQUE," + USER_TABLE_PASSWORD_COLUMN+ " VARCHAR(255)" + ");";

        String CREATE_DOLL_TABLE = "CREATE TABLE " + DOLL_TABLE_NAME + "("
                + DOLL_TABLE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT," + DOLL_TABLE_NAME_COLUMN+ " VARCHAR(255),"
                + DOLL_TABLE_DESC_COLUMN + " VARCHAR(255)," + DOLL_TABLE_IMAGE_COLUMN+ " INTEGER" + ");";


        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_DOLL_TABLE);

        //db.close();
        preloadDolls(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onCreate(db);
    }


    //check user existence in db
    public boolean checkUserExistence(String emailAddress) {
        SQLiteDatabase database = this.getReadableDatabase();

        String selection = USER_TABLE_EMAIL_COLUMN + "=?";
        String[] selectionArgs = {emailAddress};
        //database.query()
        Cursor cursor = database.query(
                USER_TABLE_NAME,
                new String[] {USER_TABLE_EMAIL_COLUMN},
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean result = (cursor.getCount() > 0);

        cursor.close();
        database.close();

        return (result) ? true : false;
    }


    //authenicate user
    public User authenticateUser(User user) {
        User result = null;
        SQLiteDatabase database = this.getReadableDatabase();

        String selection = USER_TABLE_EMAIL_COLUMN + "=?";
        String[] selectionArgs = {user.email};

        Cursor cursor = database.query(
                USER_TABLE_NAME,
                new String[] {USER_TABLE_ID_COLUMN, USER_TABLE_NAME_COLUMN, USER_TABLE_EMAIL_COLUMN, USER_TABLE_PASSWORD_COLUMN},
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int foundId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(USER_TABLE_ID_COLUMN)
            );

            String foundName = cursor.getString(
                    cursor.getColumnIndexOrThrow(USER_TABLE_NAME_COLUMN)
            );

            String foundEmailAdddress = cursor.getString(
                    cursor.getColumnIndexOrThrow(USER_TABLE_EMAIL_COLUMN)
            );

            String foundPassword = cursor.getString(
                    cursor.getColumnIndexOrThrow(USER_TABLE_PASSWORD_COLUMN)
            );


            if (user.email.equals(foundEmailAdddress) && user.password.equals(foundPassword)) {

                result = new User(foundId, foundName, foundEmailAdddress, foundPassword);
            }
        }

        cursor.close();
        //database.close();
        return result;
    }


    //gregister the user
    public boolean registerUser(User user) {
        if (checkUserExistence(user.email)) {
            return false;
        }


        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues newUser = new ContentValues();

        newUser.put(USER_TABLE_NAME_COLUMN, user.name);
        newUser.put(USER_TABLE_EMAIL_COLUMN, user.email);
        newUser.put(USER_TABLE_PASSWORD_COLUMN, user.password);

        long rowId = database.insertOrThrow(USER_TABLE_NAME, null, newUser);

        //database.close();
        return true;
    }


    //put a doll
    public void putDoll(Doll doll) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues newDoll = new ContentValues();

        newDoll.put(DOLL_TABLE_NAME_COLUMN, doll.name);
        newDoll.put(DOLL_TABLE_DESC_COLUMN, doll.description);
        newDoll.put(DOLL_TABLE_IMAGE_COLUMN, doll.imageResId);

        database.insert(DOLL_TABLE_NAME, null, newDoll);

        //database.close();
    }

    public void putDollInit(Doll doll, SQLiteDatabase database) {
        //SQLiteDatabase database = this.getWritableDatabase();

        ContentValues newDoll = new ContentValues();

        newDoll.put(DOLL_TABLE_NAME_COLUMN, doll.name);
        newDoll.put(DOLL_TABLE_DESC_COLUMN, doll.description);
        newDoll.put(DOLL_TABLE_IMAGE_COLUMN, doll.imageResId);

        database.insert(DOLL_TABLE_NAME, null, newDoll);

        //database.close();
    }


    public ArrayList<Doll> getAllDolls() {
        ArrayList<Doll> result  = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor  = database.rawQuery("SELECT * FROM " + DOLL_TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                result.add(
                        new Doll(
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow(DOLL_TABLE_ID_COLUMN)
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow(DOLL_TABLE_NAME_COLUMN)
                            ),
                            cursor.getString(
                                    cursor.getColumnIndexOrThrow(DOLL_TABLE_DESC_COLUMN)
                            ),
                            cursor.getInt(
                                    cursor.getColumnIndexOrThrow(DOLL_TABLE_IMAGE_COLUMN)
                            )
                        )
                );
            }
        }

        cursor.close();
        //database.close();
        return result;
    }

    public Doll getSingleDollById(int id) {
        Doll result = null;
        SQLiteDatabase database = this.getReadableDatabase();

        String selection = DOLL_TABLE_ID_COLUMN+ "=?";
        String[] selectionArgs = {new Integer(id).toString()};

        Cursor cursor = database.query(
                DOLL_TABLE_NAME,
                new String[] {DOLL_TABLE_ID_COLUMN, DOLL_TABLE_NAME_COLUMN, DOLL_TABLE_DESC_COLUMN, DOLL_TABLE_IMAGE_COLUMN},
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            result = new Doll(
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(DOLL_TABLE_ID_COLUMN)
                    ),
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(DOLL_TABLE_NAME_COLUMN)
                    ),
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(DOLL_TABLE_DESC_COLUMN)
                    ),
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(DOLL_TABLE_IMAGE_COLUMN)
                    )
            );
        }

        cursor.close();
        //database.close();
        return result;
    }


    public void updateDoll(Doll doll) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues updatedColumns = new ContentValues();

        updatedColumns.put(DOLL_TABLE_NAME_COLUMN, doll.name);
        updatedColumns.put(DOLL_TABLE_DESC_COLUMN, doll.description);
        updatedColumns.put(DOLL_TABLE_IMAGE_COLUMN, doll.imageResId);

        database.update(DOLL_TABLE_NAME, updatedColumns, DOLL_TABLE_ID_COLUMN + "=?", new String[]{new Integer(doll.id).toString()});
    }

    private void preloadDolls(SQLiteDatabase database) {
        putDollInit(new Doll(-1, "Boneka Kucing", "Boneka Kucing Lucu", R.drawable.boneka_kucing), database);
        putDollInit(new Doll(-1, "Boneka Rusa", "Boneka Rusa Lucu", R.drawable.boneka_rusa), database);
        putDollInit(new Doll(-1, "Boneka Panda", "Boneka Panda Lucu", R.drawable.boneka_panda), database);
        putDollInit(new Doll(-1, "Boneka Babi", "Boneka Babi Lucu", R.drawable.boneka_babi), database);
    }



}
