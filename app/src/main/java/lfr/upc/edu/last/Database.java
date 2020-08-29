package lfr.upc.edu.last;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {
    private static Statement st;
    private static ResultSet rs;
    private Connection conn = null;
    private String currentDatabase = "sqlserver";
    private String dbURL = "";
    private Properties prop;
    private static Connection  a=null;
    private  static String ssql;
    private  static String qsql;
    private  static String qres;
    private  static boolean res;

    private static Database instance;


    public Database(){
        try{Class.forName("com.mysql.jdbc.Driver");
            System.out.println("1");
        }catch(Exception ex){}
    }
    public static Database getInstance(){
        if (instance ==null){
            instance = new Database();
        }
        return instance;
    }

    //向cat表中插入数据
    public boolean select(String sql) {
        qsql=sql;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ip = "rm-m5e20ur951d965h09uo.mysql.rds.aliyuncs.com"; //本机IP
                String dbName = "mydb";             //自己的数据库名
                String url = "jdbc:mysql://" + ip + ":3306/" + dbName ;
                String user = "zcs";
                String password = "420102zcs";
                try {
                    Connection conn1 = DriverManager.getConnection(url, user, password);
                    st=(Statement)conn1.createStatement();
                    rs=st.executeQuery(qsql);
                    res=rs.next();

                    conn1.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try{Thread.sleep(500);}catch (Exception e){}
        return this.res;
    }
    public String select1(String sql, final String name) {
        qsql=sql;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ip = "rm-m5e611j35o28muzu79o.mysql.rds.aliyuncs.com"; //本机IP
                String dbName = "gyh";             //自己的数据库名
                String url = "jdbc:mysql://" + ip + ":3306/" + dbName ;
                String user = "gyh";
                String password = "1707030316";
                try {
                    Connection conn1 = DriverManager.getConnection(url, user, password);
                    st=(Statement)conn1.createStatement();
                    rs=st.executeQuery(qsql);
                    //res=rs.next();
                    if(rs.next())  qres=rs.getString(name);
                    conn1.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try{Thread.sleep(2000);}catch (Exception e){}
        return this.qres;
    }
    public byte[] select2(String sql, final String name) {
        qsql=sql;
        final byte[] test1 = new byte[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ip = "rm-m5e611j35o28muzu79o.mysql.rds.aliyuncs.com"; //本机IP
                String dbName = "gyh";             //自己的数据库名
                String url = "jdbc:mysql://" + ip + ":3306/" + dbName ;
                String user = "gyh";
                String password = "1707030316";
                try {
                    Connection conn1 = DriverManager.getConnection(url, user, password);
                    st=(Statement)conn1.createStatement();
                    rs=st.executeQuery(qsql);
                    //res=rs.next();
                    if(rs.next()) {
                        test1[0] =rs.getByte(name);
                    }
                    conn1.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try{Thread.sleep(2000);}catch (Exception e){}
        return test1;
    }
    public void insert(String sql)
    {
        ssql=sql;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ip = "rm-m5e20ur951d965h09uo.mysql.rds.aliyuncs.com"; //本机IP
                String dbName = "mydb";             //自己的数据库名
                String url = "jdbc:mysql://" + ip + ":3306/" + dbName ;
                String user = "zcs";
                String password = "420102zcs";
                try {
                    Connection conn1 = DriverManager.getConnection(url, user, password);
                    st=(Statement)conn1.createStatement();
                    st.executeUpdate(ssql);
                    conn1.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    //private ArrayList<String> friends;
    public ArrayList<String> select_list(String sql, final String name) {
        qsql=sql;
        final ArrayList<String> list=new ArrayList<String>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ip = "rm-m5e611j35o28muzu79o.mysql.rds.aliyuncs.com"; //本机IP
                String dbName = "gyh";             //自己的数据库名
                String url = "jdbc:mysql://" + ip + ":3306/" + dbName ;
                String user = "gyh";
                String password = "1707030316";
                try {
                    Connection conn1 = DriverManager.getConnection(url, user, password);
                    st = (Statement) conn1.createStatement();
                    ResultSet rs = st.executeQuery(ssql);//得到结果集
                    conn.commit();//事务提交
                    conn.setAutoCommit(true);// 更改jdbc事务的默认提交方式
                   // List<String> list = new ArrayList<String>();//创建取结果的列表，之所以使用列表，不用数组，因为现在还不知道结果有多少，不能确定数组长度，所有先用list接收，然后转为数组
                    while (rs.next()) {//如果有数据，取第一列添加如list
                        list.add(rs.getString(name));
                    }
//                    if (list != null && list.size() > 0) {//如果list中存入了数据，转化为数组
//                        //arr = new String[list.size()];//创建一个和list长度一样的数组
//                        for (int i = 0; i < list.size(); i++) {
//                            arr[i] = list.get(i);//数组赋值了。
//                        }
//                        //输出数组
//                        for (int i = 0; i < arr.length; i++) {
//                            System.out.println(arr[i]);
//                        }
//                        //res=rs.next();
//                       // if (rs.next()) qres = rs.getString("INFOR");
//                        conn1.close();
//                    }
                    conn1.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try{Thread.sleep(2000);}catch (Exception e){}
        return list;
    }
    public static Connection getConnection(String name) {
        Connection conn = null;
        return conn;
    }

}


