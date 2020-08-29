package lfr.upc.edu.last;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class MySelf extends Activity{
    String user_name;
    public TextView myname;
    public TextView sex1;
    public TextView location1;
    public TextView qm1;
    public TextView phone1;
    private Button mymain,friendslist;
    private ImageView selectIcon;
    private ImageView showIcon;
    private Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_self);
        Intent intent = getIntent();
        //String name=intent.getStringExtra("name");
        user_name = intent.getStringExtra("name");
        showIcon = (ImageView) findViewById(R.id.iv_showIcon);
        initView();

//        selectIcon.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, null);
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        "image/*");
//                startActivityForResult(intent, 0x1);
//            }
//        });

//        btnQuery.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                DBOpenHelper dbOpenHelper = new DBOpenHelper(MySelf.this, "LIBRARY_DB.db", null, 1);
//                SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
//                Cursor c = db.query("USERINFO", new String[]{"USER_NAME","uimgae"}, "USER_NAME=?", new String[]{user_name}, null, null, null);
//                while (c.moveToNext()) {
////                    String id = c.getString(c.getColumnIndex("id"));
////                    String name = c.getString(c.getColumnIndex("uname"));
//                    byte[] in = c.getBlob(c.getColumnIndex("uimgae"));
//                    Bitmap bit = BitmapFactory.decodeByteArray(in, 0, in.length);
//                    showIcon.setImageBitmap(bit);
//                }
//            }
//        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            Uri u = data.getData();
            ContentResolver cr = MySelf.this.getContentResolver();
            try {
                bit = BitmapFactory.decodeStream(cr.openInputStream(u));
                selectIcon.setImageBitmap(bit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void initView(){
        myname = (TextView) findViewById(R.id.myname);
        myname.setText(user_name);
//        DBOpenHelper dbOpenHelper = new DBOpenHelper(MySelf.this, "LIBRARY_DB.db", null, 1);
//        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
//        Cursor c = db.query("USERINFO", new String[]{"USER_NAME","uimgae"}, "USER_NAME=?", new String[]{user_name}, null, null, null);
//        while (c.moveToNext()) {
//                    String id = c.getString(c.getColumnIndex("id"));
//                    String name = c.getString(c.getColumnIndex("uname"));
            //byte[] in = c.getBlob(c.getColumnIndex("uimgae"));
            byte[] in=Database.getInstance().select2("select * from USERINFO where USER_NAME ='" + user_name + "';","uimage");
            Bitmap bit = BitmapFactory.decodeByteArray(in, 0, in.length);
            showIcon.setImageBitmap(bit);
       // }
//        String excl = "select * from USERINFO where USER_NAME ='" + user_name + "';";
//        Cursor cursor = db.rawQuery(excl, null);
//        cursor.moveToFirst();
//        System.out.println("查询成功");
        //do {
            //String location = cursor.getString(cursor.getColumnIndex("location"));
            String location = Database.getInstance().select1("select * from USERINFO where USER_NAME ='" + user_name + "';","location");
            String phone = Database.getInstance().select1("select * from USERINFO where USER_NAME ='" + user_name + "';","phone");
            String qm = Database.getInstance().select1("select * from USERINFO where USER_NAME ='" + user_name + "';","qm");
//            String phone = cursor.getString(cursor.getColumnIndex("phone"));
//            String qm = cursor.getString(cursor.getColumnIndex("qm"));
            qm1 = (TextView) findViewById(R.id.qm);
            qm1.setText(qm);
            phone1 = (TextView) findViewById(R.id.phone);
            phone1.setText(phone);
            location1 = (TextView) findViewById(R.id.location);
            location1.setText(location);

        //} while (cursor.moveToNext());
        mymain= findViewById(R.id.mymain2);
        mymain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("我的主页被点击了");
                Intent mIntent = new Intent();
                mIntent.setClass(MySelf.this, MyMain.class);
                mIntent.putExtra("name", user_name.trim());
                startActivity(mIntent);
                MySelf.this.finish();
            }
        });
        friendslist=findViewById(R.id.friendslist2);
        friendslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("我的列表被点击了");
                Intent mIntent = new Intent();
                mIntent.setClass(MySelf.this, Main2Activity.class);
                mIntent.putExtra("name", user_name.trim());
                startActivity(mIntent);
                MySelf.this.finish();
            }
        });

    }
//    @Override
//    public void onClick(View view) {
//
//    }
}

