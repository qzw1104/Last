package lfr.upc.edu.last;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener
{
    //按钮图片的ID
    private int [] mRes={R.id.menubtn,R.id.flushbtn,R.id.sharebtn,R.id.addbookbtn,R.id.myself,R.id.mymain};
    private ArrayList<Button>mimageViews=new   ArrayList<>();
    private boolean mFlag = true;
    float width;
    float distance;
    private ListView booklist;
    static ArrayList<String> bookArrayList;
    //String[] arr=new String[10000];
    EditText searchet;
    String user_name;
    String more_name;
    String l,q,p;
    private Bitmap bit;
    private Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        //String name=intent.getStringExtra("name");
        user_name=intent.getStringExtra("name");

        Toast.makeText(Main2Activity.this, "欢迎回来 " + user_name, Toast.LENGTH_LONG).show();
        //bookArrayList = new ArrayList<Book>();
        DisplayMetrics dm = new DisplayMetrics();
        width = dm.widthPixels;
        int sum=mRes.length;
        for (int i = 0; i < sum; i++) {
            Button view = findViewById(mRes[i]);
            view.setOnClickListener(this);
            mimageViews.add(view);
        }
        initView();
    }

    private void initView() {
        //配置EditText
        searchet = findViewById(R.id.searchedit);
        searchet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //当输入文本超过1时调整listview
                if(s.length()>=1) {
                    //DBOpenHelper dbOpenHelper = new DBOpenHelper(Main2Activity.this, "LIBRARY_DB.db", null, 1);
                    //SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
                   // String search = searchet.getText().toString();
                    //Database.getInstance().select1()
                    String excl = "select FRIENDS_ID from '"+user_name+"' where pd=1;";
                    boolean pan=Database.getInstance().select(excl);
                    bookArrayList=Database.getInstance().select_list(excl);
                    //bookArrayList= (ArrayList<String>) Arrays.asList(arr);
//                    for(int i=0;i<arr.length;i++){
//                        System.out.println(arr[i]);
//                    }
                    //String exc1="select * from USERINFO where USER_NAME ='"+user_name+"';";
//                    Cursor cursor = db.rawQuery(excl, null);
//                    if (cursor.getCount() != 0) {
//                        bookArrayList.clear();
//                        cursor.moveToFirst();
//                        do {
//
//                            String friends= cursor.getString(cursor.getColumnIndex("FRIENDS_ID"));
//                            Book book = new Book(friends);
//                            bookArrayList.add(book);
//                            booklist.setAdapter(new MyAdapter());
//                        } while (cursor.moveToNext());
//                    }
                }else if(s.length()==0){
                    readbookdb();
                    booklist.setAdapter(new MyAdapter());
                }
            }
        });
        readbookdb();
        booklist = findViewById(R.id.booklist);
        //配置listview
        MyAdapter myAdapter = new MyAdapter();
        booklist.setAdapter(myAdapter);
        booklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                more_name=bookArrayList.get(position);
               // DBOpenHelper dbOpenHelper = new DBOpenHelper(Main2Activity.this,"LIBRARY_DB.db",null,1);
                //SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                //c = db.query("USERINFO", new String[]{"USER_NAME","uimgae"}, "USER_NAME=?", new String[]{more_name}, null, null, null);
                //String excl = "select * from USERINFO where USER_NAME ='"+more_name+"';";
                //Cursor cursor = db.rawQuery(excl, null);
                //if(cursor.getCount()!=0){
                   // cursor.moveToFirst();
                   // do{
                        q=Database.getInstance().select1("select qm from USERINFO where USER_NAME ='"+more_name+"';");
                        l=Database.getInstance().select1("select location from USERINFO where USER_NAME ='"+more_name+"';");
                        p=Database.getInstance().select1("select phone from USERINFO where USER_NAME ='"+more_name+"';");
                    //}while (cursor.moveToNext());
                //}else{
                    // Toast.makeText(this,"未找到用户信息",Toast.LENGTH_LONG).show();
               // }
                showDialog2(position);
            }
        });

    }


//具体内容
    private void showDialog2(int position){
        more_name=bookArrayList.get(position);//.friends;
        final View view = LayoutInflater.from(this).inflate(R.layout.friendsdialog,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        ImageView showIcon = (ImageView)view.findViewById(R.id.iv_showIcon1);
        while (c.moveToNext()) {
//                    String id = c.getString(c.getColumnIndex("id"));
//                    String name = c.getString(c.getColumnIndex("uname"));
            byte[] in = c.getBlob(c.getColumnIndex("uimgae"));
            bit = BitmapFactory.decodeByteArray(in, 0, in.length);
            showIcon.setImageBitmap(bit);
        }


        TextView qmm = view.findViewById(R.id.qm1);
        qmm.setText(q);
        TextView ppp = view.findViewById(R.id.phone1);
        ppp.setText(p);
        TextView lll = view.findViewById(R.id.location1);
        lll.setText(l);
        Button btn_cancel_high_opion = view.findViewById(R.id.addcancel);
        Button btn_agree_high_opion = view.findViewById(R.id.addgo);
        TextView nnn = view.findViewById(R.id.myname);
        nnn.setText(more_name);
        //System.out.println("我的列表被点击了");
        btn_agree_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent mIntent = new Intent();
            mIntent.setClass(Main2Activity.this, FriendsMain.class);
            mIntent.putExtra("name", more_name.trim());
            mIntent.putExtra("name1", user_name.trim());
            startActivity(mIntent);
            Main2Activity.this.finish();
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
        dialog.getWindow().setLayout((Main2Activity.ScreenUtils.getScreenWidth(this)/5*4),LinearLayout.LayoutParams.WRAP_CONTENT);
    }
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
                LayoutInflater inflater = Main2Activity.this.getLayoutInflater();
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
        //DBOpenHelper dbOpenHelper = new DBOpenHelper(this,"LIBRARY_DB.db",null,1);

        //SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        //String excl = "select FRIENDS_ID from '"+user_name+"' where pd='1';";
        //Cursor cursor = db.rawQuery(excl, null);
        String excl = "select FRIENDS_ID from '"+user_name+"' where pd=1;";
      //  boolean pan=Database.getInstance().select(excl);
      //  arr=Database.getInstance().select_list(excl);
        bookArrayList=Database.getInstance().select_list(excl);

        if(bookArrayList.size()==0){
            //cursor.moveToFirst();
               // String friends=cursor.getString(cursor.getColumnIndex("FRIENDS_ID"));
               // Book book = new Book(friends);
                //bookArrayList.add(book);
                //bookArrayList= (ArrayList<String>) Arrays.asList(arr);
            //}while (cursor.moveToNext());
        }else{
            Toast.makeText(this,"没有好友，请添加",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menubtn:
                if (mFlag) {
                    startAnim();
                } else {
                    closeAnim();
                }
                break;
            case R.id.flushbtn:
                readbookdb();
                booklist.setAdapter(new MyAdapter());
                Toast.makeText(this,"刷新成功",Toast.LENGTH_LONG).show();
                break;
            case R.id.sharebtn:
                System.out.println("好友申请列表被点击了");
                Intent intent = new Intent(Main2Activity.this,NewFriends.class);
                intent.putExtra("name", user_name.trim());
                startActivity(intent);
                finish();
                break;
            case R.id.addbookbtn:
                System.out.println("添加图书按钮被点击了");
                showDialog();
                break;
            case R.id.myself:
               System.out.println("我的列表被点击了");
                Intent intent1 = new Intent(Main2Activity.this,MySelf.class);
                intent1.putExtra("name", user_name.trim());
                startActivity(intent1);
                finish();
                break;
            case R.id.mymain:
                System.out.println("我的主页被点击了");
                Intent intent2 = new Intent(Main2Activity.this,MyMain.class);
                intent2.putExtra("name", user_name.trim());
                startActivity(intent2);
                finish();
                break;
        }

    }



    //添加好友申请
    private void showDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.more_dialog,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        Button btn_cancel_high_opion = view.findViewById(R.id.addcancel);
        Button btn_agree_high_opion = view.findViewById(R.id.addgo);
       // Button addbtn = view.findViewById(R.id.addbtn);
        final EditText addname = view.findViewById(R.id.addname);
        btn_cancel_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_agree_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // DBOpenHelper dbOpenHelper = new DBOpenHelper(Main2Activity.this,"LIBRARY_DB.db",null,1);
                //SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
                String excl = "select * from USERINFO where USER_NAME ='"+addname.getText().toString()+"';";
                boolean pan=Database.getInstance().select(excl);
                //Cursor cursor = db.rawQuery(excl, null);
                if(pan==true){
                    //cursor.moveToFirst();
                   // do{
                        String exc2 = "select * from '"+user_name+"' where FRIENDS_ID ='"+addname.getText().toString()+"';";
                       // Cursor cursor1 = db.rawQuery(exc2, null);
                       boolean pan1=Database.getInstance().select(exc2);
                        if(pan1==false){
                            Toast.makeText(Main2Activity.this,"已添加该联系人",Toast.LENGTH_LONG).show();
                        }else{
                            String exc3="insert into "+user_name+" (FRIENDS_ID,pd) values ("+addname.getText().toString()+",0)";
                            String exc4="insert into "+addname.getText().toString()+" (FRIENDS_ID,pd) values ("+user_name+",2)";
                            Database.getInstance().insert(exc3);
                            Database.getInstance().insert(exc4);
//                            ContentValues values = new ContentValues();
//                            values.put("FRIENDS_ID",addname.getText().toString());
//                            values.put("pd","0");
//                            db.insert("'"+user_name+"'",null,values);
//                            ContentValues values1 = new ContentValues();
//                            values1.put("FRIENDS_ID",user_name);
//                            values1.put("pd","2");
//                            db.insert("'"+addname.getText().toString()+"'",null,values1);
                            Toast.makeText(Main2Activity.this,"添加申请发送成功!",Toast.LENGTH_LONG).show();}
                   // }while (cursor.moveToNext());
                    readbookdb();
                    booklist.setAdapter(new MyAdapter());
                }else{
                    Toast.makeText(Main2Activity.this,"未找到用户信息!",Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
                //  lookformore.setEnabled(false);
                // SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

            }
        });

        dialog.show();
        //设置位置窗体大小
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)/5*4),LinearLayout.LayoutParams.WRAP_CONTENT);
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
