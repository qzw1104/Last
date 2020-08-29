package lfr.upc.edu.last;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MyMain extends AppCompatActivity implements View.OnClickListener {
    private Button myself1,friendslist1;
    private CalendarView calendarView;
    private EditText scheduleInput;
    private Context context;
    private Button addSchedule, checkAdd;
    private String dateToday;//用于记录今天的日期
    private String time;//记录详细的时间
    private DBOpenHelper mySQLiteOpenHelper;
    private SQLiteDatabase myDatabase;
    private TextView mySchedule[] = new TextView[5];
    String user_name;
    String jia="/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);

        Intent intent = getIntent();            // 首先获取到意图对象
        //String name=intent.getStringExtra("name");
        user_name=intent.getStringExtra("name");
        initView();

        //这里不这样的话一进去就设置当天的日程会报错
        Calendar time = Calendar.getInstance();
        int year = time.get(Calendar.YEAR);
        int month = time.get(Calendar.MONTH) + 1;//注意要+1，0表示1月份
        int day = time.get(Calendar.DAY_OF_MONTH);
        dateToday = year + "-" + month + "-" + day;
        //还要直接查询当天的日程，这个要放在initView的后面，不然会出问题
        queryByDate(dateToday);
    }

    private void initView() {
        mySQLiteOpenHelper = new DBOpenHelper(MyMain.this, "LIBRARY_DB.db", null, 1);
        myDatabase = mySQLiteOpenHelper.getReadableDatabase();

        context = this;
        addSchedule = findViewById(R.id.addSchedule);
        addSchedule.setOnClickListener(this);       //添加日程
        checkAdd = findViewById(R.id.checkAdd);
        checkAdd.setOnClickListener(this);      //确认添加日程
        myself1= findViewById(R.id.myself1);
        myself1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("我的主页被点击了");
                Intent mIntent = new Intent();
                mIntent.setClass(MyMain.this, MySelf.class);
                mIntent.putExtra("name", user_name.trim());
                startActivity(mIntent);
                MyMain.this.finish();
            }
        });
        friendslist1=findViewById(R.id.friendslist1);
        friendslist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("我的列表被点击了");
                Intent mIntent = new Intent();
                mIntent.setClass(MyMain.this, Main2Activity.class);
                mIntent.putExtra("name", user_name.trim());
                startActivity(mIntent);
                MyMain.this.finish();
            }
        });
        calendarView = findViewById(R.id.calendar);
        scheduleInput = findViewById(R.id.scheduleDetailInput);

        calendarView.setOnDateChangeListener(mySelectDate);

        mySchedule[0] = findViewById(R.id.schedule1);
        mySchedule[0].setOnClickListener(this);
        mySchedule[1] = findViewById(R.id.schedule2);
        mySchedule[1].setOnClickListener(this);
        mySchedule[2] = findViewById(R.id.schedule3);
        mySchedule[2].setOnClickListener(this);
        mySchedule[3] = findViewById(R.id.schedule4);
        mySchedule[3].setOnClickListener(this);
        mySchedule[4] = findViewById(R.id.schedule5);
        mySchedule[4].setOnClickListener(this);
       /* for(TextView v:mySchedule){
        v.setOnClickListener(this);     //转入修改日程的界面
        }*/
    }

    private CalendarView.OnDateChangeListener mySelectDate = new CalendarView.OnDateChangeListener() {      //弹窗
        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            dateToday = year + "-" + (month + 1) + "-" + dayOfMonth;
            Toast.makeText(context, "你选择了:" + dateToday, Toast.LENGTH_SHORT).show();
            //得把用别的日期查出来的日程删除并将其隐藏
            for (TextView v : mySchedule) {
                v.setText("");
                v.setVisibility(View.GONE);
            }
            queryByDate(dateToday);
        }
    };

    //根据日期查询日程
    private void queryByDate(String date) {
        //columns为null 查询所有列
//        Cursor cursor1 = myDatabase.query("'"+jia+""+user_name+"'", new String[]{"scheduleDetail"}, "time=?", new String[]{date}, null, null, null);
//        Cursor cursor2 = myDatabase.query("'"+jia+""+user_name+"'", new String[]{"datetime"}, "time=?", new String[]{date}, null, null, null);
//        if (cursor1.moveToFirst()&&cursor2.moveToFirst()) {
//            int scheduleCount = 0;
//            do {
//                String aScheduleDetail = cursor1.getString(cursor1.getColumnIndex("scheduleDetail"));
//                String aScheduleDetail2 = cursor2.getString(cursor2.getColumnIndex("datetime"));
//                System.out.println("时间是"+aScheduleDetail2);
//                mySchedule[scheduleCount].setText("日程" + (scheduleCount + 1) + "：" + aScheduleDetail+"  "+aScheduleDetail2);
//                mySchedule[scheduleCount].setVisibility(View.VISIBLE);
//                scheduleCount++;
//                //一定要有这句 不然TextView不够多要数组溢出了
//                if (scheduleCount >= 5)
//                    break;
//            } while (cursor1.moveToNext()&&cursor2.moveToNext());
//        }
//        cursor1.close();
//        cursor2.close();
        ArrayList<String> scheduleDetail=new ArrayList<String>();
        scheduleDetail=Database.getInstance().select_list("select scheduleDetail from "+jia+""+user_name+"");
        ArrayList<String> datetime=new ArrayList<String>();
        datetime=Database.getInstance().select_list("select datetime from "+jia+""+user_name+"");
//        String create_sql1="create table '"+jia+""+zhuceName+"'(id Integer primary key autoincrement,scheduleDetail varchar(50),time varchar(30),events varchar(500),datetime varchar(20));";
//        Cursor cursor1 = myDatabase.query("'"+jia+""+more_name+"'", new String[]{"scheduleDetail"}, "time=?", new String[]{date}, null, null, null);
//        Cursor cursor2 = myDatabase.query("'"+jia+""+more_name+"'", new String[]{"datetime"}, "time=?", new String[]{date}, null, null, null);
        //      if (cursor1.moveToFirst()&&cursor2.moveToFirst()) {
        int scheduleCount = 0;
        //           do {
        for(int i=0;i<scheduleDetail.size();i++){
            String aScheduleDetail = scheduleDetail.get(i);
            String aScheduleDetail2 = datetime.get(i);
            System.out.println("时间是"+aScheduleDetail2);
            mySchedule[scheduleCount].setText("日程" + (scheduleCount + 1) + "：" + aScheduleDetail+"  "+aScheduleDetail2);
            mySchedule[scheduleCount].setVisibility(View.VISIBLE);
            scheduleCount++;
            if (scheduleCount >= 5)
                break;
        }
    }

    @Override
    public void onClick(View v) {       //点击按钮监听
        switch (v.getId()) {
            case R.id.addSchedule:
                addTime();      //增加具体时间
                addMySchedule();        //增加事件
                break;
            case R.id.checkAdd:
                checkAddSchedule();     //确认添加事件
                break;
            case R.id.schedule1:
            case R.id.schedule2:
            case R.id.schedule3:
            case R.id.schedule4:
            case R.id.schedule5:
                editSchedule(v);        //修改事件，计划修改成备忘录
                break;
        }
    }
////////////////////////////////////////buzhidao
    private void editSchedule(View v) {
        Intent intent = new Intent(MyMain.this, EditScheduleActivity2.class);
        String sch = ((TextView) v).getText().toString().split("：")[1];
        sch = sch.replaceAll("([1-9]+[0-9]*|0)(\\.[\\d]+)?", "");
        sch = sch.replaceAll(":","");
        sch = sch.replaceAll(" ","");
        //String sch = ((TextView) v).getText().toString().split("：")[1];
        Cursor cursor = myDatabase.query("'"+jia+""+user_name+"'", new String[]{"events"}, "scheduleDetail=" + "'" + sch + "'", null, null, null, null);
        //System.out.println("瓜瓜哥哥哥哥");
        if (cursor.moveToFirst()) {
            String aScheduleDetail2 = cursor.getString(cursor.getColumnIndex("events"));
            System.out.println("这就是传说中的备忘录" + aScheduleDetail2);
            intent.putExtra("events", aScheduleDetail2);
        }
        intent.putExtra("name", user_name);
        intent.putExtra("schedule", sch);
        startActivity(intent);
    }

    private void checkAddSchedule() {
        ContentValues values = new ContentValues();
        //第一个参数是表中的列名
        values.put("scheduleDetail", scheduleInput.getText().toString());    //把框里的内容传到数据库
        values.put("time", dateToday);
        values.put("datetime", time);
        myDatabase.insert("'"+jia+""+user_name+"'", null, values);
        scheduleInput.setVisibility(View.GONE);
        checkAdd.setVisibility(View.GONE);
        queryByDate(dateToday);
        //添加完以后把scheduleInput中的内容清除
        scheduleInput.setText("");
    }

    private void addMySchedule() {
        scheduleInput.setVisibility(View.VISIBLE);
        checkAdd.setVisibility(View.VISIBLE);
    }

    private void addTime() {        //添加具体时间的函数////////////////////////////
        Calendar calendar = Calendar.getInstance();

        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);    //得到小时
        int minute = calendar.get(Calendar.MINUTE);            //得到分钟

        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                //  这个方法是得到选择后的 小时、分钟，分别对应着三个参数 —   hourOfDay、minute

                //addSchedule.setText("" + hourOfDay + ":" + minute);\

                time = "00:00";
                if(hourOfDay>=10&&minute>=10) {
                    time = hourOfDay + ":" + minute;
                }else if(hourOfDay<10&&minute>=10){
                    time = "0"+hourOfDay+ ":" + minute;
                }else if(hourOfDay>=10&&minute<10){
                    time = hourOfDay + ":0"+ minute;
                }else if(hourOfDay<10&minute<10){
                    time = "0"+hourOfDay+ ":0" + minute;
                }
            }
        }, hourOfDay, minute, true).show();
    }

}
