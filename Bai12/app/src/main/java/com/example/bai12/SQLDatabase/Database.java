package com.example.bai12.SQLDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bai12.model.Item;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ChiTieu.db";
    private static int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "category TEXT," +
                "price TEXT," +
                "date TEXT)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();

        SQLiteDatabase st = getReadableDatabase();

        String order = "date DESC";

        Cursor rs = st.query("items", null, null,
                null, null, null, order);

        while (rs != null && rs.moveToNext()) {
            list.add(new Item(rs.getInt(0),
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }

        return list;
    }

    public long addItem(Item i) {
        ContentValues values = new ContentValues();

        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());

        SQLiteDatabase st = getWritableDatabase();

        return st.insert("items", null, values);
    }

    public List<Item> getByDate(String date) {
        List<Item> list = new ArrayList<>();

        String where = "date like ?";
        String[] args = {date};

        SQLiteDatabase st = getReadableDatabase();

        Cursor rs = st.query("items", null, where, args, null, null, null);

        while (rs != null && rs.moveToNext()) {
            list.add(new Item(rs.getInt(0),
                    rs.getString(1), rs.getString(2), rs.getString(3), date));
        }

        return list;
    }

    public int update(Item i) {
        ContentValues values = new ContentValues();

        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());

        String where = "id = ?";
        String[] args = {i.getId() + ""};

        SQLiteDatabase st = getWritableDatabase();

        return st.update("items", values, where, args);
    }

    public int delete(int id) {
        String where = "id = ?";
        String[] args = {id + ""};

        SQLiteDatabase st = getWritableDatabase();

        return st.delete("items", where, args);
    }

    public List<Item> searchByTitle(String title) {
        List<Item> list = new ArrayList<>();

        String where = "title like ?";
        String[] args = {"%" + title + "%"};

        SQLiteDatabase st = getReadableDatabase();

        Cursor rs = st.query("items", null, where, args, null, null, null);

        while (rs != null && rs.moveToNext()) {
            list.add(new Item(rs.getInt(0),
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }

        return list;
    }

    public List<Item> searchByCategory(String category) {
        List<Item> list = new ArrayList<>();

        String where = "category like ?";
        String[] args = {category};

        SQLiteDatabase st = getReadableDatabase();

        Cursor rs = st.query("items", null, where, args, null, null, null);

        while (rs != null && rs.moveToNext()) {
            list.add(new Item(rs.getInt(0),
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }

        return list;
    }

    public List<Item> searchByDateFromTo(String from, String to) {
        List<Item> list = new ArrayList<>();

        String where = "date BETWEEN ? AND ?";
        String[] args = {from.trim(), to.trim()};

        SQLiteDatabase st = getReadableDatabase();

        Cursor rs = st.query("items", null, where, args, null, null, null);

        while (rs != null && rs.moveToNext()) {
            list.add(new Item(rs.getInt(0),
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }

        return list;
    }
}
