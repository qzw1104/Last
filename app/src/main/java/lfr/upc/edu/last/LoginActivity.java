package lfr.upc.edu.last;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText loginId,loginKey;
    Button loginBtn;
    TextView loginZhuCe;
    DBOpenHelper dbOpenHelper;
    private EditText et_name;
    public static final String TAG = LoginActivity.class.getName();
    private ImageView iv_showCode;
    private EditText et_phoneCode;
    //产生的验证码
    private String realCode;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_phoneCode = (EditText) findViewById(R.id.et_phoneCodes);
        //Button but_toSetCode = (Button) findViewById(R.id.but_forgetpass_toSetCodes);
        // but_toSetCode.setOnClickListener(this);
        iv_showCode = (ImageView) findViewById(R.id.iv_showCode);
        //将验证码用图片的形式显示出来
        iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
        iv_showCode.setOnClickListener(this);
        init();
    }

    //初始化
    private void init() {
        loginId = findViewById(R.id.loginId);
        loginKey = findViewById(R.id.loginKey);
        loginBtn = findViewById(R.id.loginBtn);
        loginZhuCe = findViewById(R.id.loginZhuCe);
        loginBtn.setOnClickListener(this);
        loginZhuCe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:         //提取账号密码并调用数据库进行比对
                //提取输入的用户名及密码
                Boolean success = false;
                String userLoginName = String.valueOf(loginId.getText());
                String userLoginKey = String.valueOf(loginKey.getText());
                String userName;
                String userKey;
//                dbOpenHelper = new DBOpenHelper(this, "LIBRARY_DB.db", null, 1);
//                System.out.println("创立新的helper");
//                SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//                System.out.println("获取数据库成功");
//                Cursor cursor = db.query("USERINFO", null, null, null, null, null, null);
//                System.out.println("获取结果cursor成功");
                String select_sql="select * from USERINFO where USER_NAME='"+userLoginName+"'";
                boolean pan=Database.getInstance().select(select_sql);
                if(pan==false){
                    Intent intent2 = new Intent(LoginActivity.this,ZhuCeActivity.class);
                    startActivity(intent2);
                }else{
                    //cursor.moveToFirst();
                    System.out.println("查询成功");
                    userName=userLoginName;
                    userKey=Database.getInstance().select1(select_sql);
//                    do {
                        String phoneCode = et_phoneCode.getText().toString().toLowerCase();
////                        String msg = "生成的验证码："+realCode+"输入的验证码:"+phoneCode;
////                        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
//                        userName = cursor.getString(cursor.getColumnIndex("USER_NAME"));
//                        userKey = cursor.getString(cursor.getColumnIndex("USER_KEY"));
//                        System.out.println(userName + "    " + userKey);
                        if (userLoginKey.equals(userKey) &&phoneCode.equals(realCode)) {
                            //验证通过，弹窗跳转
                            success = true;
                            System.out.println("用户登录成功    :" + userName + "   " + userKey);
                            break;
                        }
                    }
                    if (success) {
                        System.out.println("登陆成功");
                        //   Toast.makeText(LoginActivity.this, "欢迎回来 " + userLoginName, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this,Main2Activity.class);
                        intent.putExtra("name", userLoginName.trim());
                        startActivity(intent);
                        finish();
                    } else {
                        System.out.println("登录失败 ");
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                        loginKey.setText("");
                    }
                break;
            case R.id.loginZhuCe:       //跳转到注册Acitivity
                Intent intent = new Intent(LoginActivity.this,ZhuCeActivity.class);

                startActivity(intent);
                break;
            case R.id.iv_showCode:
                iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                Log.v(TAG,"realCode"+realCode);
                break;
        }
    }
}

