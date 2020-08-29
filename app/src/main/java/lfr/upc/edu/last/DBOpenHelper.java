package lfr.upc.edu.last;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBOpenHelper extends SQLiteOpenHelper {

    private Context mContext;

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    //创建用户数据表
    private static final String USERDB_CREATE =
            "create table USERINFO ( USER_NAME varchar(15),USER_KEY varchar(20),location varchar(100),qm varchar(100),phone varchar(100),uimgae BLOB);";

    //创建图书数据表
    private static final String BOOKDB_CREATE =
            "create table BOOKINFO (USER_NAME varchar(15),uimgae BLOB)";

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(USERDB_CREATE);
//        db.execSQL(BOOKDB_CREATE);
//        ContentValues values = new ContentValues();
//        values.put("USER_NAME","admin");
//        values.put("USER_KEY","admin");
//        db.insert("USERINFO",null,values);
//        values.clear();
//        values.put("BOOK_ID","10001");
//        values.put("BOOK_NAME","红楼梦");
//        values.put("BOOK_WRITER","曹雪芹");
//        values.put("BOOK_PUBLISHER","中华出版社");
//        values.put("BOOK_TOTALNUMBER",200);
//        values.put("BOOK_LEAVENUMBER",92);
//        db.insert("USERINFO",null,values);
//
//        values.clear();
//        values.put("BOOK_ID","10002");
//        values.put("BOOK_NAME","西游记");
//        values.put("BOOK_WRITER","吴承恩");
//        values.put("BOOK_PUBLISHER","中华出版社");
//        values.put("BOOK_TOTALNUMBER",50);
//        values.put("BOOK_LEAVENUMBER",25);
//        db.insert("USERINFO",null,values);
//
//        values.clear();
//        values.put("BOOK_ID","10003");
//        values.put("BOOK_NAME","三国演义");
//        values.put("BOOK_WRITER","罗贯中");
//        values.put("BOOK_PUBLISHER","中华出版社");
//        values.put("BOOK_TOTALNUMBER",120);
//        values.put("BOOK_LEAVENUMBER",75);
//        db.insert("USERINFO",null,values);
//
//        values.clear();
//        values.put("BOOK_ID","10004");
//        values.put("BOOK_NAME","第一行代码");
//        values.put("BOOK_WRITER","郭霖");
//        values.put("BOOK_PUBLISHER","人民邮电出版社");
//        values.put("BOOK_TOTALNUMBER",20);
//        values.put("BOOK_LEAVENUMBER",4);
//        db.insert("USERINFO",null,values);
//
//        values.clear();
//        values.put("BOOK_ID","10005");
//        values.put("BOOK_NAME","杀死舍友");
//        values.put("BOOK_WRITER","赵子楼");
//        values.put("BOOK_PUBLISHER","2221出版社");
//        values.put("BOOK_TOTALNUMBER",10);
//        values.put("BOOK_LEAVENUMBER",9);
//        db.insert("USERINFO",null,values);
//
//        values.clear();
//        values.put("BOOK_ID","10006");
//        values.put("BOOK_NAME","杨景文喂养手册");
//        values.put("BOOK_WRITER","华农兄弟");
//        values.put("BOOK_PUBLISHER","农业出版社");
//        values.put("BOOK_TOTALNUMBER",15);
//        values.put("BOOK_LEAVENUMBER",12);
//        db.insert("USERINFO",null,values);
//
//        values.clear();
//        values.put("BOOK_ID","10007");
//        values.put("BOOK_NAME","我与金瓶梅");
//        values.put("BOOK_WRITER","贾宇轩");
//        values.put("BOOK_PUBLISHER","2221出版社");
//        values.put("BOOK_TOTALNUMBER",6);
//        values.put("BOOK_LEAVENUMBER",1);
//        db.insert("USERINFO",null,values);
//
//        values.clear();
//        values.put("BOOK_ID","10008");
//        values.put("BOOK_NAME","我的后宫");
//        values.put("BOOK_WRITER","杨景文");
//        values.put("BOOK_PUBLISHER","2221出版社");
//        values.put("BOOK_TOTALNUMBER",200);
//        values.put("BOOK_LEAVENUMBER",92);
//        db.insert("USERINFO",null,values);
//
//        values.clear();
//        values.put("BOOK_ID","10009");
//        values.put("BOOK_NAME","三体");
//        values.put("BOOK_WRITER","刘慈欣");
//        values.put("BOOK_PUBLISHER","科幻世界");
//        values.put("BOOK_TOTALNUMBER",200);
//        values.put("BOOK_LEAVENUMBER",92);
//        db.insert("USERINFO",null,values);
//
//        values.clear();
//        values.put("BOOK_ID","10010");
//        values.put("BOOK_NAME","后宫管理手册");
//        values.put("BOOK_WRITER","杨景文");
//        values.put("BOOK_PUBLISHER","2221出版社");
//        values.put("BOOK_TOTALNUMBER",18);
//        values.put("BOOK_LEAVENUMBER",7);
//        db.insert("USERINFO",null,values);
//        Toast.makeText(mContext,"数据表创建成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
