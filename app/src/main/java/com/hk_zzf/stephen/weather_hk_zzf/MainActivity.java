package com.mh.piety.mwe;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mh.piety.mwe.Bean.LocalInfoBean;
import com.mh.piety.mwe.Bean.WeatherBean;
import com.mh.piety.mwe.Utils.WeatherUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String position="";
    private Context context = this;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Menu menu;
    private Toolbar toolbar;
    private TextView text_position,textv_Temporary,textv_Feel,textv_TXT,textv_dir,textv_sc,textv_Drippy,textv_View,textv_day1Cond,textv_day2Cond,textv_day3Cond,textv_day1Temporary,textv_day2Temporary,textv_day3Temporary;
    private TextView textv_cere,textv_aqi,textv_pm25,textv_dbrf,textv_dTXT,textv_sbrf,textv_sTXT,textv_tbrf,textv_tTXT,textv_update;
    private ImageView image_delete,imagev_icon,imagev_day1Icon,imagev_day2Icon,imagev_day3Icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},3);
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_activity);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        text_position= (TextView) findViewById(R.id.text_position);
        image_delete= (ImageView) findViewById(R.id.image_delete);

        //天气信息相关控件
        textv_Temporary= (TextView) findViewById(R.id.textv_Temporary);
        textv_Feel= (TextView) findViewById(R.id.textv_Feel);
        textv_TXT= (TextView) findViewById(R.id.textv_TXT);
        textv_dir= (TextView) findViewById(R.id.textv_dir);
        textv_sc= (TextView) findViewById(R.id.textv_sc);
        textv_Drippy= (TextView) findViewById(R.id.textv_Drippy);
        textv_View= (TextView) findViewById(R.id.textv_View);
        imagev_icon= (ImageView) findViewById(R.id.imagev_icon);
        textv_day1Cond= (TextView) findViewById(R.id.textv_day1Cond);
        textv_day2Cond= (TextView) findViewById(R.id.textv_day2Cond);
        textv_day3Cond= (TextView) findViewById(R.id.textv_day3Cond);
        textv_day1Temporary= (TextView) findViewById(R.id.textv_day1Temporary);
        textv_day2Temporary= (TextView) findViewById(R.id.textv_day2Temporary);
        textv_day3Temporary= (TextView) findViewById(R.id.textv_day3Temporary);
        imagev_day1Icon= (ImageView) findViewById(R.id.imagev_day1Icon);
        imagev_day2Icon= (ImageView) findViewById(R.id.imagev_day2Icon);
        imagev_day3Icon= (ImageView) findViewById(R.id.imagev_day3Icon);
        textv_cere= (TextView) findViewById(R.id.textv_cere);
        textv_aqi= (TextView) findViewById(R.id.textv_aqi);
        textv_pm25= (TextView) findViewById(R.id.textv_pm25);
        textv_dbrf= (TextView) findViewById(R.id.textv_dbrf);
        textv_dTXT= (TextView) findViewById(R.id.textv_dTXT);
        textv_sbrf= (TextView) findViewById(R.id.textv_sbrf);
        textv_sTXT= (TextView) findViewById(R.id.textv_sTXT);
        textv_tbrf= (TextView) findViewById(R.id.textv_tbrf);
        textv_tTXT= (TextView) findViewById(R.id.textv_tTXT);
        textv_update= (TextView) findViewById(R.id.textv_update);

        //获取用户偏好地址

        final ArrayList<LocalInfoBean> list = new WeatherUtil().getPrePosition(context);
        if(!checkNet()) {
            Toast.makeText(context, "当前网络连接不可用", Toast.LENGTH_SHORT).show();
            if(list.size()>0){
                updateMenu();
                text_position.setText(menu.getItem(0).getTitle());
                WeatherUtil wt = new WeatherUtil();
                wt.jsondata = list.get(0).we_info;
                text_position.setText(list.get(0).position);
                updateUI(setWeatherBean(wt, 1));
            }
        } else {
            if(list.size()>0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WeatherUtil wt = new WeatherUtil(list.get(0).position.split("-")[1]);
                    final WeatherBean we = setWeatherBean(wt, 0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text_position.setText(list.get(0).position);
                            updateMenu();
                            updateUI(we);
                        }
                    });
                }
            }).start();}
            else {
                Intent intent = new Intent();
                intent.setClass(context,PositionActivity.class);
                startActivity(intent);
            }
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        //浮动button添加地址
        FloatingActionButton feltoat_btn = (FloatingActionButton) findViewById(R.id.feltoat_btn);
        feltoat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,PositionActivity.class);
                startActivity(intent);
            }
        });

        //侧边栏监听item点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String str_position = item.getTitle().toString();
            text_position.setText(str_position);
            update(str_position);
            drawerLayout.closeDrawer(navigationView);
            return true;
            }
        });

        image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new WeatherUtil().getPrePosition(context).size()>0){
                new AlertDialog.Builder(context)
                        .setTitle("提示")
                        .setMessage("确认不看 "+text_position.getText().toString().split("-")[1]+" 的天气信息吗？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new WeatherUtil().deleteInfo(context,text_position.getText().toString());
                                updateMenu();
                                if(menu.size()>0){
                                    String str_position = menu.getItem(0).getTitle().toString();
                                    text_position.setText(str_position);
                                    update(str_position);
                                }
                                else{
                                    Toast.makeText(context,"添加地址！",Toast.LENGTH_SHORT).show();
                                    text_position.setText("");
                                    textv_Temporary.setText("...°");
                                    textv_Feel.setText("体感温度：...°");
                                    textv_TXT.setText("...");
                                    textv_dir.setText("...");
                                    textv_sc.setText("...");
                                    textv_Drippy.setText("...%");
                                    textv_View.setText("...km");
                                    imagev_icon.setImageResource(R.drawable.none);
                                    imagev_day1Icon.setImageResource(R.drawable.none);
                                    imagev_day2Icon.setImageResource(R.drawable.none);
                                    imagev_day3Icon.setImageResource(R.drawable.none);
                                    textv_day1Cond.setText("...");
                                    textv_day2Cond.setText("...");
                                    textv_day3Cond.setText("...");
                                    textv_day1Temporary.setText("...°/...°");
                                    textv_day2Temporary.setText("...°/...°");
                                    textv_day3Temporary.setText("...°/...°");
                                    textv_cere.setText("...");
                                    textv_aqi.setText("...");
                                    textv_pm25.setText("...");
                                    textv_dbrf.setText("...");
                                    textv_dTXT.setText("......");
                                    textv_sbrf.setText("...");
                                    textv_sTXT.setText("......");
                                    textv_tbrf.setText("...");
                                    textv_tTXT.setText("......");
                                    textv_update.setText("最后更新时间：");
                                    Intent intent = new Intent();
                                    intent.setClass(context,PositionActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (position!="") {
            final boolean isexist = (new WeatherUtil().queryInfo(context,position)!=null);
            text_position.setText(position);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String location=position.split("-")[1];
                        WeatherUtil wt = new WeatherUtil(location);
                        if(isexist){new WeatherUtil().updateInfo(context,position,wt.jsondata);}
                        else{new WeatherUtil().storeWeatherInfo(context,position,wt.jsondata);}
                        final WeatherBean we = setWeatherBean(wt,0);
                        MainActivity.position = "";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateMenu();
                                updateUI(we);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,"未找到相关天气信息!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
    }
    private boolean checkNet(){
        try {
            ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo net = conn.getActiveNetworkInfo();
            if(net.getState()== NetworkInfo.State.CONNECTED){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private void updateMenu(){
        menu=navigationView.getMenu();
        menu.clear();
        ArrayList<LocalInfoBean> list = new WeatherUtil().getPrePosition(context);
        int i=0;
        for(LocalInfoBean l:list){
            menu.add(Menu.NONE,Menu.FIRST+i,Menu.NONE,l.position);
            i++;
        }
    }
    private WeatherBean setWeatherBean(WeatherUtil wt,int type){;
        WeatherBean we = new WeatherBean();
        we.now = wt.getNowWeathre();
        we.day1 = wt.getDailyWeather(0);
        we.day2 = wt.getDailyWeather(1);
        we.day3 = wt.getDailyWeather(2);
        if(type==0){
        we.now_icon = wt.getIcon(we.now.condition.code);
        we.day1_icon = wt.getIcon(we.day1.condition.code_di);
        we.day2_icon = wt.getIcon(we.day2.condition.code_di);
        we.day3_icon = wt.getIcon(we.day3.condition.code_di);}
        else{
            we.now_icon = wt.getIconForLoc(we.now.condition.code);
            we.day1_icon = wt.getIconForLoc(we.day1.condition.code_di);
            we.day2_icon = wt.getIconForLoc(we.day2.condition.code_di);
            we.day3_icon = wt.getIconForLoc(we.day3.condition.code_di);
        }
        we.aqi=wt.getAQI();
        we.drsg=wt.getSuggestion("drsg");
        we.sport=wt.getSuggestion("sport");
        we.trav=wt.getSuggestion("trav");
        we.time=wt.getUpdateTime();
        return we;
    }

    //选择地址后从网络获取数据更新界面
    private void updateUI(WeatherBean we){
        textv_Temporary.setText(we.now.Temporary + "°");
        textv_Feel.setText("体感温度：" + we.now.felt + "°");
        textv_TXT.setText(we.now.condition.txt);
        textv_dir.setText(we.now.wind.dirr);
        textv_sc.setText(we.now.wind.scc.indexOf("风") != -1 ? we.now.wind.scc : we.now.wind.scc + "级");
        textv_Drippy.setText(we.now.damp + "%");
        textv_View.setText(we.now.view + "km");
        imagev_icon.setImageBitmap(we.now_icon);
        imagev_day1Icon.setImageBitmap(we.day1_icon);
        imagev_day2Icon.setImageBitmap(we.day2_icon);
        imagev_day3Icon.setImageBitmap(we.day3_icon);
        textv_day1Cond.setText(we.day1.condition.txt_di.equals(we.day1.condition.txt_n) ? we.day1.condition.txt_di : we.day1.condition.txt_di + "转" + we.day1.condition.txt_n);
        textv_day2Cond.setText(we.day2.condition.txt_di.equals(we.day2.condition.txt_n) ? we.day2.condition.txt_di : we.day2.condition.txt_di + "转" + we.day2.condition.txt_n);
        textv_day3Cond.setText(we.day3.condition.txt_di.equals(we.day3.condition.txt_n) ? we.day3.condition.txt_di : we.day3.condition.txt_di + "转" + we.day3.condition.txt_n);
        textv_day1Temporary.setText(we.day1.Temporary.min + "°/" + we.day1.Temporary.max + "°");
        textv_day2Temporary.setText(we.day2.Temporary.min + "°/" + we.day2.Temporary.max + "°");
        textv_day3Temporary.setText(we.day3.Temporary.min + "°/" + we.day3.Temporary.max + "°");
        if(we.aqi==null){
            textv_cere.setText("无");
            textv_aqi.setText("无");
            textv_pm25.setText("无");
        }
        else{
            textv_cere.setText(we.aqi.qlty);
            textv_aqi.setText(we.aqi.aqi);
            textv_pm25.setText(we.aqi.pm25);
        }
        textv_dbrf.setText(we.drsg.brf);
        textv_dTXT.setText(we.drsg.txt);
        textv_sbrf.setText(we.sport.brf);
        textv_sTXT.setText(we.sport.txt);
        textv_tbrf.setText(we.trav.brf);
        textv_tTXT.setText(we.trav.txt);
        textv_update.setText("最后更新时间：" + we.time);
    }
    //偏好地址的天气信息，如果有网络 从网络获取数据更新，否则从本地获取缓存的天气数据
    public void update(String position){
        final String str_position=position;
        if(checkNet()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                    WeatherUtil wt = new WeatherUtil(str_position.split("-")[1]);
                    final WeatherBean we = setWeatherBean(wt,0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUI(we);
                        }
                    });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        else {
            WeatherUtil wt = new WeatherUtil();
            wt.jsondata=wt.queryInfo(context,str_position).we_info;
            WeatherBean we = setWeatherBean(wt,1);
            updateUI(we);
        }
    }
}
