package maybay.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THANATTHANAN on 7/5/2017.
 */

public class DbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "mycompany.db3";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE `Customers` (\n" +
                "\t`Id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`Name`\tTEXT NOT NULL,\n" +
                "\t`Surname`\tTEXT,\n" +
                "\t`Username`\tTEXT NOT NULL UNIQUE,\n" +
                "\t`Password`\tTEXT NOT NULL,\n" +
                "\t`Email`\tTEXT,\n" +
                "\t`Telephone`\tTEXT,\n" +
                "\t`Photo`\tTEXT,\n" +
                "\t`Gender`\tTEXT\n" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public long customerAdd (Customer customer)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Name",customer.Name);
        values.put("Surname",customer.Surname);
        values.put("Username",customer.Username);
        values.put("Password",customer.Password);
        values.put("Email",customer.Email);
        values.put("Telephone",customer.Telephone);
        values.put("Photo",customer.Photo);
        values.put("Gender",customer.Gender);

        long id = db.insert("Customers",null,values);
        db.close();

        return id;
    }

    public int customerUpdate(Customer customer)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Name",customer.Name);
        values.put("Surname",customer.Surname);
        values.put("Username",customer.Username);
        values.put("Password",customer.Password);
        values.put("Email",customer.Email);
        values.put("Telephone",customer.Telephone);
        values.put("Photo",customer.Photo);
        values.put("Gender",customer.Gender);

        String whereClause = "Id=?";
        String [] whereArgs = { String.valueOf(customer.Id)};

        int affected = db.update("Customers",values,whereClause,whereArgs);
        db.close();

        return affected;
    }

    public int customerDelete(long id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "Id=?";
        String[] whereArgs = { String.valueOf(id)};
        int affected = db.delete("Customers",whereClause,whereArgs);
        db.close();

        return affected;
    }

    public List<Customer> customerGet()
    {
        String sql = "select * from Customers";
        String [] selectionArgs = null;

        return customerSelect(sql,selectionArgs);
    }

    public List<Customer> customersSearch (String searchText) {
        String sql = "selet * \n" +
                "form Customers\n" +
                "where \n" +
                "\tName like '%' || ? || '%'" +
                "\tor Surname like '%' || ? || '%'";
        String[] selectionArgs = {searchText, searchText};

        return customerSelect(sql, selectionArgs);
    }

    private List<Customer> customerSelect (String sql , String[] selectionArgs)
    {
        SQLiteDatabase db = getReadableDatabase();
        List<Customer> customers = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql , selectionArgs);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            Customer customer = new Customer();
            customer.Id = cursor.getLong(0);
            customer.Name = cursor.getString(1);
            customer.Surname = cursor.getString(2);
            customer.Username = cursor.getString(3);
            customer.Password = cursor.getString(4);
            customer.Email = cursor.getString(5);
            customer.Telephone = cursor.getString(6);
            customer.Photo = cursor.getString(7);
            customer.Gender = cursor.getString(8);
            customers.add(customer);

            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return customers;
    }

    public boolean customerAuthen (String username, String password)
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql  = "select count(*) from Customers where username=? and password=?";
        String [] selectionArgs = { username, password};

        Cursor cursor = db.rawQuery(sql,selectionArgs);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();

        return count > 0 ;
    }


}
