package lfr.upc.edu.last;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NewFriends extends AppCompatActivity {

    private ArrayList<Button> mimageViews=new   ArrayList<>();
    private boolean mFlag = true;
    float width;
    float distance;
    ListView booklist;
    static ArrayList<String> bookArrayList;
    EditText searchet;
    String user_name;
    String more_name1;
    String p,q,l;
    private Button button1;
    private Bitmap bit;
    private byte[] c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends);
        Intent intent = getIntent();
        user_name=intent.getStringExtra("name");
        bookArrayList = new ArrayList<String>();
        DisplayMetrics dm = new DisplayMetrics();
        width = dm.widthPixels;
        initView();
        button1 = (Button) findViewById(R.id.fanhui2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("我的列表被点击了");
                Intent mIntent = new Intent();
                mIntent.setClass(NewFriends.this, Main2Activity.class);
                mIntent.putExtra("name", user_name.trim());
                startActivity(mIntent);
                NewFriends.this.finish();
            }
        });
    }
    long time;
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis()-time>2000){
                Toast.makeText(NewFriends.this,"再次点击返回键，程序退出",Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            }else{
                NewFriends.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    private void initView() {
        //配置EditText
        searchet = findViewById(R.id.searchedit);
        //读取数据库中图书信息
        readbookdb();
        booklist = findViewById(R.id.booklist);
        //配置listview
        NewFriends.MyAdapter myAdapter = new NewFriends.MyAdapter();
        booklist.setAdapter(myAdapter);
        booklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                more_name1=bookArrayList.get(position);
//                DBOpenHelper dbOpenHelper = new DBOpenHelper(NewFriends.this,"LIBRARY_DB.db",null,1);
//                SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

               // c = db.query("USERINFO", new String[]{"USER_NAME","uimgae"}, "USER_NAME=?", new String[]{more_name1}, null, null, null);
                String excl = "select * from USERINFO where USER_NAME ='"+more_name1+"';";
                c=Database.getInstance().select2(excl,"uimage");
               // Cursor cursor = db.rawQuery(excl, null);
//                if(cursor.getCount()!=0){
//                cursor.moveToFirst();
//                do{
                    q=Database.getInstance().select1(excl,"qm");
                l=Database.getInstance().select1(excl,"location");
                p=Database.getInstance().select1(excl,"phone");
//            }while (cursor.moveToNext());
//        }else{
//          // Toast.makeText(this,"未找到用户信息",Toast.LENGTH_LONG).show();
//        }
                showDialog2(position);
            }
        });

    }
    //具体内容
    private void showDialog2(int position){
        final View view = LayoutInflater.from(this).inflate(R.layout.friendsdialog,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        ImageView showIcon = (ImageView)view.findViewById(R.id.iv_showIcon1);
       // while (c.moveToNext()) {
//                    String id = c.getString(c.getColumnIndex("id"));
//                    String name = c.getString(c.getColumnIndex("uname"));
            //byte[] in = c.getBlob(c.getColumnIndex("uimgae"));
            bit = BitmapFactory.decodeByteArray(c, 0, c.length);
            showIcon.setImageBitmap(bit);
        //}


        TextView qmm = view.findViewById(R.id.qm1);
        qmm.setText(q);
        TextView ppp = view.findViewById(R.id.phone1);
        ppp.setText(p);
        TextView lll = view.findViewById(R.id.location1);
        lll.setText(l);
        Button btn_cancel_high_opion = view.findViewById(R.id.addcancel);
        Button btn_agree_high_opion = view.findViewById(R.id.addgo);
        more_name1=bookArrayList.get(position);//.friends;
        TextView nnn = view.findViewById(R.id.myname);
        nnn.setText(more_name1);
        btn_agree_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBOpenHelper dbOpenHelper = new DBOpenHelper(NewFriends.this,"LIBRARY_DB.db",null,1);
                SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                String excl ="UPDATE '"+user_name+"' SET pd='1' WHERE FRIENDS_ID='"+more_name1+"';";
                db.execSQL(excl);
                String exc2 ="UPDATE '"+more_name1+"' SET pd='1' WHERE FRIENDS_ID='"+user_name+"';";
                db.execSQL(exc2);
                Toast.makeText(NewFriends.this,"添加成功",Toast.LENGTH_LONG).show();
            }
        });
        btn_cancel_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        //设置位置窗体大小
        dialog.getWindow().setLayout((NewFriends.ScreenUtils.getScreenWidth(this)/5*4),LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    //显示条目
    private class MyAdapter extends BaseAdapter {
        //要一共显示多少个条目
        @Override
        public int getCount() {
            return bookArrayList.size();
        }

        //
        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                Log.i("新建", position + "");
                //创建一个View
                /**
                 * LayoutInflater的作用：将一个xml布局文件转化为一个View集，即看成一个View对象
                 */
                LayoutInflater inflater = NewFriends.this.getLayoutInflater();
                view = inflater.inflate(R.layout.booklistlayout, null);//第二个参数是指定填充后的View集的父亲
            }
            if (convertView != null) {
                Log.i("缓存", position + "");
                view = convertView;
            }
            final TextView friendsname = view.findViewById(R.id.friendsname);
            friendsname.setText(bookArrayList.get(position));//.friends);
            friendsname.setTextSize(27);
            friendsname.setTextColor(R.color.colorAccent);
            return view;
        }

        //返回指定位置的object对象，不用去实现
        @Override
        public Object getItem(int position) {
            return null;
        }

        //返回指定位置的ID，不用去实现
        @Override
        public long getItemId(int position) {
            return 0;
        }


    }


    //读取数据库信息
    private void readbookdb() {
        //清空现有数据
        bookArrayList.clear();

        //读取新数据
//        DBOpenHelper dbOpenHelper = new DBOpenHelper(this,"LIBRARY_DB.db",null,1);
//        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String excl = "select * from '"+user_name+"' where pd='2';";
//        Cursor cursor = db.rawQuery(excl, null);
        bookArrayList=Database.getInstance().select_list(excl,"FRIENDS_ID");
        if(bookArrayList.size()!=0){
//            cursor.moveToFirst();
//            do{
//                String friends=cursor.getString(cursor.getColumnIndex("FRIENDS_ID"));
//                Book book = new Book(friends);
//                bookArrayList.add(book);
//            }while (cursor.moveToNext());
        }else{
            Toast.makeText(this,"没有好友申请，",Toast.LENGTH_LONG).show();
        }

    }

    public static class ScreenUtils {
        /**
         * 获取屏幕高度(px)
         */
        public static int getScreenHeight(Context context) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
        /**
         * 获取屏幕宽度(px)
         */
        public static int getScreenWidth(Context context) {
            return context.getResources().getDisplayMetrics().widthPixels;
        }

    }




    private void closeAnim() {
        //计算xy的距离  获取到参数后  传给动画需要的参数
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(mimageViews.get(0),
                "alpha", 0.5F, 1F);
        ObjectAnimator animator11 = ObjectAnimator.ofFloat(mimageViews.get(1),
                "TranslationY",0);

        ObjectAnimator animator22 = ObjectAnimator.ofFloat(mimageViews.get(2),
                "translationY", 0);

        ObjectAnimator animator33 = ObjectAnimator.ofFloat(mimageViews.get(3),
                "translationY", 0);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(200);
        set.setInterpolator(new BounceInterpolator());
        set.playTogether(animator0,animator11,animator22,animator33);
        set.start();
        mFlag = true;

    }


    private void startAnim() {
        //动画距离,屏幕宽度的50%
        distance = xyZ()*0.2f;
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(
                mimageViews.get(0),
                "alpha",
                1F,
                0.5F);
        ObjectAnimator animator11 = ObjectAnimator.ofFloat(
                mimageViews.get(1),
                "translationY",
                -distance/3);

        ObjectAnimator animator22 = ObjectAnimator.ofFloat(
                mimageViews.get(2),
                "translationY",
                -distance/3*2);
        ObjectAnimator animator33 = ObjectAnimator.ofFloat(
                mimageViews.get(3),
                "translationY",
                -distance);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.setInterpolator(new BounceInterpolator());
        set.playTogether(
                animator0,
                animator11,
                animator22,
                animator33);
        set.start();
        mFlag = false;
    }

    //获取屏幕宽度 高度 来返回xz轴  Y轴
    public float xyZ() {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightPixels = displayMetrics.heightPixels;
        int widthPixels = displayMetrics.widthPixels;
        return widthPixels>heightPixels?widthPixels:heightPixels;
    }
}


