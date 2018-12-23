package com.example.dell.mygym;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserService {
    private DbHelper dbHelper;

    public UserService(Context context) {
        dbHelper = new DbHelper(context);
    }
    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    public boolean Login(String username, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from suser where username = ? and password = ? ";
        Cursor rawQuery = sqLiteDatabase.rawQuery(sql, new String[] { username,
                password });
        if (rawQuery.moveToFirst() == true) {
            rawQuery.close();
            return true;
        }
        return false;
    }
    /**
     * 用户注册
     *
     * @param user
     * @return
     */

    public boolean Register(SLUser user) {
        if( Queryexist(user.getUsername())==true) {
            return false;
        }
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "insert into suser (username,password) values (?,?)";
        Object obj[] = { user.getUsername(), user.getPassword()};
        sqLiteDatabase.execSQL(sql, obj);
        return true;
    }
    public boolean Queryexist(String username){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select *from suser where username = ?";
        Cursor rawQuery = sqLiteDatabase.rawQuery(sql, new String[] { username});
        if (rawQuery.moveToFirst() == true) {
            rawQuery.close();
            return true;
        }
        return false;
    }

}

