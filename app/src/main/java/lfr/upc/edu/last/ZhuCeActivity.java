package lfr.upc.edu.last;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class ZhuCeActivity extends AppCompatActivity implements View.OnClickListener{

    EditText zhuceId,zhuceKey,zhuceKey2,zcqm,zcphone,zclocation;
    Button zhuceBtn;
    TextView zhuceLogin;
    Random r = new Random(1);
    private ImageView selectIcon;
    // private Button btnSave, btnQuery;
    private Bitmap bit;
    String addr;
    Button loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        selectIcon = (ImageView) findViewById(R.id.iv_icon);
        //addr="hai";
        Intent intent = getIntent();
       addr=intent.getStringExtra("addr");
        TextView text=(TextView)findViewById(R.id.location);
        text.setText(addr);
        loc=findViewById(R.id.loc);
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("我的列表被点击了");
                Intent mIntent = new Intent();
                mIntent.setClass(ZhuCeActivity.this, Local.class);
              //  mIntent.putExtra("addr", address.trim());
                startActivity(mIntent);
                ZhuCeActivity.this.finish();
            }
        });
        init();
        selectIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, 0x1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            Uri u = data.getData();
            ContentResolver cr = ZhuCeActivity.this.getContentResolver();
            try {
                bit = BitmapFactory.decodeStream(cr.openInputStream(u));
                selectIcon.setImageBitmap(bit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void init() {
        zcqm=findViewById(R.id.qm);
        zcphone=findViewById(R.id.phone);
        //zclocation=findViewById(R.id.location);
        zhuceId = findViewById(R.id.zhuceId);
        zhuceKey = findViewById(R.id.zhuceKey);
        zhuceKey2 = findViewById(R.id.zhuceKey2);
        zhuceBtn = findViewById(R.id.zhuceBtn);
        zhuceLogin = findViewById(R.id.zhuceLogin);

        zhuceBtn.setOnClickListener(this);
        zhuceLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zhuceBtn:         //比对两次输入的密码一致并不存在该用户后写入数据库
                if(String.valueOf(zhuceKey.getText()).equals(String.valueOf(zhuceKey2.getText()))){
                    System.out.println("两次密码一致");
                    String userName = " ";
                    Boolean exits = true;
                    String zhuceName = String.valueOf(zhuceId.getText());
                    //DBOpenHelper dbOpenHelper = new DBOpenHelper(this,"LIBRARY_DB.db",null,1);
                    //System.out.println("创立了新的helper");
                    //SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                    //System.out.println("成功获取数据库");
                    //Cursor cursor = db.query("USERINFO",null,null,null,null,null,null);
                    //System.out.println(cursor.getCount());

                   // if(cursor.getCount()!=0){
                       // cursor.moveToFirst();
                        /*if(cursor.moveToFirst()) {*/
                        //System.out.println("cursor游标已移到第一位");
                       // do {
                            //userName = cursor.getString(cursor.getColumnIndex("USER_NAME"));
                            //System.out.println(userName);
                            String sql="select * from USERINFO where USER_NAME='"+zhuceName+"'";
                            boolean pan=Database.getInstance().select(sql);
                            //判断该用户是否已存在
                            if(pan==true){
                                System.out.println("该用户已存在");
                                exits = false;
                                break;
                            }
                //} while (cursor.moveToNext());
                        if(exits){
                            //用户不存在，两次密码一致，写入数据库
                           // String idd= String.valueOf(r.nextInt(100));
//                            ContentValues values = new ContentValues();
                            final ByteArrayOutputStream os = new ByteArrayOutputStream();
//                            bit.compress(Bitmap.CompressFormat.PNG, 100, os);
//                            values.put("USER_NAME",zhuceName);
//                            values.put("USER_KEY", String.valueOf(zhuceKey.getText()));
//                            values.put("qm",String.valueOf(zcqm.getText()));
//                            values.put("phone",String.valueOf(zcphone.getText()));
//                            values.put("location",addr);
//                            values.put("uimgae", os.toByteArray());
//                            db.insert("USERINFO",null,values);
//                            ContentValues values1 = new ContentValues();
//                            values1.put("FRIENDS_ID","zzz");
//                            ContentValues values2 = new ContentValues();
//                            values2.put("FRIENDS_ID","aaa");
//                            db.insert(""+zhuceName+"",null,values1);
//                            db.insert(""+zhuceName+"",null,values2);
                            String sql_insert="INSERT INTO USER_INFO  (USER_NAME,USER_KEY,qm,phone,location,uimgae)  VALUES  ("+zhuceName+","+String.valueOf(zhuceKey.getText())+","+String.valueOf(zcqm.getText())+","+String.valueOf(zcphone.getText())+","+addr+","+os.toByteArray()+");";
                            Database.getInstance().insert(sql_insert);
                            String create_sql = "create table '"+zhuceName+"' (FRIENDS_ID varchar(15),pd varchar(4));";
                            String jia="/";
                            String create_sql1="create table '"+jia+""+zhuceName+"'(id Integer primary key autoincrement,scheduleDetail varchar(50),time varchar(30),events varchar(500),datetime varchar(20));";
                            Database.getInstance().insert(create_sql);
                            Database.getInstance().insert(create_sql1);
                            Toast.makeText(ZhuCeActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ZhuCeActivity.this,"该用户已注册",Toast.LENGTH_LONG).show();
                        }
                        /*}*/
                    }
                break;
            case R.id.zhuceLogin:       //跳转到登录界面
                Intent intent = new Intent(ZhuCeActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
