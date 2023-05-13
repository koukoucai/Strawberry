package strawberry.com.strawberry;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbluetooch.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 详细界面
 * Created by huanggang on 2018/3/26.
 */

public class DetailActivity extends BaseActivity implements View.OnClickListener{

    //状态
    @BindView(R.id.status_edt)
    EditText statusEdt;

    //电量
    @BindView(R.id.power_img)
    ImageView powerImg;

    //水量
    @BindView(R.id.wd_img)
    ImageView wdImg;

    @BindView(R.id.time1Txt)
    TextView time1Txt;

    @BindView(R.id.time2Txt)
    TextView time2Txt;

    //设置密码
    @BindView(R.id.reset_pwd_bt)
    Button resetPwdBt;

    //清零
    @BindView(R.id.setting_bt)
    Button settingBt;

    //吸水电机
    @BindView(R.id.waterBt)
    Button waterBt;

    //吸水
    @BindView(R.id.waterImg)
    ImageView waterImg;

    //吸水电流
    @BindView(R.id.electric1Txt)
    TextView waterTxt;


    //刷盘电机
    @BindView(R.id.brushBt)
    Button brushBt;

    //刷盘
    @BindView(R.id.brushImg)
    ImageView brushImg;

    //刷盘电流
    @BindView(R.id.electric2Txt)
    TextView brushTxt;

    //水量
    @BindView(R.id.wb_bt)
    Button wbBt;

    @BindView(R.id.bs_bt)
    Button bsBt;

    @BindView(R.id.close_bt)
    Button closeBt;

    /**
     * 是否处于卸刷
     */
    boolean isClickBsBt = false;

    /**
     * 刷子是否在运行
     */
    boolean brushIsRunning = false;

    /**
     * 是否在进行卸刷
     */
    boolean unloadBrushIsRunning = false;

// if (data.contains(UtilsKey.UNLOAD_BRUSH_CODE_6+"00 00")&&data.contains(UtilsKey.UNLOAD_BRUSH_CODE_5+"00 02")){

    private boolean is00980000 = false;  //是否弹起 1.摁下 2.弹起
    private boolean is00990002 = false;  //按钮颜色 1.蓝色 2.灰色

    private boolean is0075 = false;

    private int bsBtAction = 0; // 0 未按下  1是按下 2 是松开

    private int statusCount = 0;//状态栏计数，每100次重新设置


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        ButterKnife.bind(this);
        resetPwdBt.setOnClickListener(this);
        settingBt.setOnClickListener(this);
        waterBt.setOnClickListener(this);
        brushBt.setOnClickListener(this);
        wbBt.setOnClickListener(this);
        bsBt.setOnClickListener(this);
        closeBt.setOnClickListener(this);

        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_1+"00 01"));
        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_2+"00 02"));
        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_3+"00 00"));


        bsBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (brushIsRunning) {
                        Toast.makeText(DetailActivity.this, "刷盘正在运行，请停止刷盘后再卸刷", Toast.LENGTH_SHORT).show();
                    } else {

                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(), target_chara, Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_2 + "00 01"));
                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(), target_chara, Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_3 + "00 00"));
                        bsBt.setBackgroundResource(R.drawable.bs1);
                        unloadBrushIsRunning = true;
                        Log.e(TAG, "onTouch:MotionEvent.ACTION_DOWN ");
                        isClickBsBt = true;
                        bsBtAction = 1;
                    }

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (brushIsRunning) {
                        Toast.makeText(DetailActivity.this, "刷盘正在运行，请停止刷盘后再卸刷", Toast.LENGTH_SHORT).show();
                    } else {
                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(), target_chara, Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_2 + "00 01"));
                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(), target_chara, Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_3 + "00 01"));
                        Log.e(TAG, "onTouch:MotionEvent.ACTION_UP ");
                        unloadBrushIsRunning = true;
                        isClickBsBt = true;
                        bsBtAction = 2;
                    }
                }

                return false;
            }
        });

        Toast.makeText(DetailActivity.this,"水量按钮 "+MainApplication.volume,Toast.LENGTH_SHORT).show();
        int imgId = Utils.getVolumeWaterImg(MainApplication.volume);
        wbBt.setBackgroundResource(imgId);

    }

//    private int volume = 0;
    private int waterSwitch = 1;
    private int brushSwitch = 1;

    private VolumeBroadCast volumeBroadCast;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.reset_pwd_bt){
            startActivity(new Intent(DetailActivity.this,SetPwdActivity.class));
        }else if(id == R.id.setting_bt){
            startActivity(new Intent(DetailActivity.this,SettingActivity.class));
        }else if (id == R.id.waterBt){
            //TODO 运行吸水电机  停止吸水电机
            Toast.makeText(DetailActivity.this,"吸水命令： "+(UtilsKey.WATER_SWITCH+waterSwitch),Toast.LENGTH_SHORT).show();
            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.WATER_SWITCH+waterSwitch));

            if (waterSwitch == 1){
                waterSwitch = 0;
                waterBt.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                waterBt.setText("停止");
            }else{
                waterSwitch = 1;
                waterBt.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
                waterBt.setText("运行");
            }

        }else if(id == R.id.brushBt){

            if (unloadBrushIsRunning){
                Toast.makeText(DetailActivity.this,"卸刷正在进行，请卸刷完毕后再进行操作",Toast.LENGTH_SHORT).show();
                return;
            }
            isClickBsBt = false;

            //TODO 刷盘电机 运行 停止
            Toast.makeText(DetailActivity.this,"刷盘命令： "+(UtilsKey.BRUSH_SWITCH+brushSwitch),Toast.LENGTH_SHORT).show();
            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.BRUSH_SWITCH+brushSwitch));

            if (brushSwitch == 1){
                brushSwitch = 0;
                brushBt.setText("停止");
                brushBt.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                brushIsRunning = true;
            }else{
                brushSwitch = 1;
                brushBt.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
                brushBt.setText("运行");
                brushIsRunning = false;
            }

        }else if(id == R.id.wb_bt){
            //TODO 调节水量
            MainApplication.volume++;
            if (MainApplication.volume>= 4){
                MainApplication.volume = 0;
            }

            int imgId = Utils.getVolumeWaterImg(MainApplication.volume);
            wbBt.setBackgroundResource(imgId);
            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.WATER_VOLUME+MainApplication.volume));

        }else if(id == R.id.bs_bt){
            //TODO 卸刷功能



        }else if (id == R.id.close_bt){
            //TODO 回到黑屏
            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.CLOSE));
            Intent intentx = new Intent(DetailActivity.this, BlackActivity.class);
            startActivity(intentx);
            finish();
        }

    }


    @Override
    public AvailableInerface getAvailableInerface() {
        return new AvailableInerface() {
            @Override
            public void OnResult(String data) {
                //各种处理

                if (data.contains(UtilsKey.PAGE_CODE_PWD)){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_PWD+"00 00"));
                }

                if(data.contains(UtilsKey.PAGE_CODE)){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_ANSWER+"00 05"));
                }

                if(data.contains(UtilsKey.PAGE_CODE_ELETRIC_I)||data.contains(UtilsKey.PAGE_CODE_ELETRIC_Ix)){
                    try {
                        waterTxt.setText("电流： "+Utils.dataToHexToIntString(data,UtilsKey.PAGE_CODE_ELETRIC_Ix)+"A");
                    } catch (Exception e) {
                        e.printStackTrace();
//                        Toast.makeText(DetailActivity.this,"错误： "+e.getMessage()+" \n出错数据 "+data,Toast.LENGTH_LONG).show();
                    }
                }

                //吸水电流颜色
                if (data.contains(UtilsKey.PAGE_CODE_ELETRIC_I_BLACK)||data.contains("10 03 00 00")||data.contains("10 03 00")){
                    waterTxt.setTextColor(getResources().getColor(R.color.black));
                }
                if (data.contains(UtilsKey.PAGE_CODE_ELETRIC_I_RED)||data.contains("10 03 F8 00")||data.contains("10 03 F8")){
                    waterTxt.setTextColor(getResources().getColor(R.color.red));
                }

                if(data.contains(UtilsKey.PAGE_CODE_ELETRIC_II)||data.contains(UtilsKey.PAGE_CODE_ELETRIC_IIx)){
                    try {
                        brushTxt.setText("电流： "+Utils.dataToHexToIntString(data,UtilsKey.PAGE_CODE_ELETRIC_IIx)+"A");
                    } catch (Exception e) {
                        e.printStackTrace();
//                        Toast.makeText(DetailActivity.this,"错误： "+e.getMessage()+" \n出错数据 "+data,Toast.LENGTH_LONG).show();
                    }
                }

                //刷子电流颜色
                if (data.contains(UtilsKey.PAGE_CODE_ELETRIC_II_BLACK)||data.contains("10 53 00 00")||data.contains("10 53 00")){
//                    Toast.makeText(DetailActivity.this,"刷盘电流 黑色"+data,Toast.LENGTH_SHORT).show();
                    brushTxt.setTextColor(getResources().getColor(R.color.black));
                }
                if (data.contains(UtilsKey.PAGE_CODE_ELETRIC_II_RED)||data.contains("10 53 F8 00")||data.contains("10 53 F8")){
//                    Toast.makeText(DetailActivity.this,"刷盘电流 红色"+data,Toast.LENGTH_SHORT).show();
                    brushTxt.setTextColor(getResources().getColor(R.color.red));
                }

                if(data.contains(UtilsKey.PAGE_CODE_TIME_I)||data.contains(UtilsKey.PAGE_CODE_TIME_Ix)){
                    try {
                        time1Txt.setText("小计： "+Utils.dataToHexToIntString(data,UtilsKey.PAGE_CODE_TIME_Ix));
                    } catch (Exception e) {
                        e.printStackTrace();
//                        Toast.makeText(DetailActivity.this,"错误： "+e.getMessage()+" \n出错数据 "+data,Toast.LENGTH_LONG).show();
                    }
                }
                if(data.contains(UtilsKey.PAGE_CODE_TIME_II)||data.contains(UtilsKey.PAGE_CODE_TIME_IIx)){
                    try {
                        time2Txt.setText("累计： "+Utils.  dataToHexToIntStringEight(data,UtilsKey.PAGE_CODE_TIME_IIx));
                    } catch (Exception e) {
                        e.printStackTrace();
//                        Toast.makeText(DetailActivity.this,"错误： "+e.getMessage()+" \n出错数据 "+data,Toast.LENGTH_LONG).show();
                    }
                }

                //电源
                if (data.contains(UtilsKey.PAGE_CODE_POWER)||data.contains(UtilsKey.PAGE_CODE_POWERx)){
                    try {
                        int id = Utils.getPowerImg(Utils.dataToHexToIntStringII(data,UtilsKey.PAGE_CODE_POWERx));
                        powerImg.setImageResource(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //水量
                if (data.contains(UtilsKey.PAGE_CODE_WATERI)||data.contains(UtilsKey.PAGE_CODE_WATERIx)){
                    try {
                        int id = Utils.getWaterImg(Utils.dataToHexToIntStringII(data,UtilsKey.PAGE_CODE_WATERIx));
                        wdImg.setImageResource(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //水量按钮  在blackactivity做判断
//                if (data.contains(UtilsKey.WATER_DISPLAY)||data.contains(UtilsKey.WATER_DISPLAYx)){
//                    Toast.makeText(DetailActivity.this,"水量按钮 "+data,Toast.LENGTH_SHORT).show();
//                    try{
//                        volume = Utils.getWaterImg(Utils.dataToHexToIntStringII(data,UtilsKey.PAGE_CODE_WATERIx));
//                        int imgId = Utils.getVolumeWaterImg(volume);
//                        wbBt.setBackgroundResource(imgId);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }


                //询问当前水量按钮的值

                if (data.contains(UtilsKey.PAGE_CODE_3)||data.contains("04 83 00 55 01")){
//                    Toast.makeText(DetailActivity.this,"询问当前水量按钮 "+data,Toast.LENGTH_SHORT).show();
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.WATER_VOLUME+MainApplication.volume));

                }

                //吸水
                if (data.contains(UtilsKey.PAGE_CODE_WATERII)||data.contains(UtilsKey.PAGE_CODE_WATERIIx)){
                    try {

                        int water = Utils.dataToHexToIntStringII(data,UtilsKey.PAGE_CODE_WATERIIx);
                        int id = Utils.getWaterImgII(water);
                        waterImg.setBackgroundResource(id);
//                        Toast.makeText(DetailActivity.this,"吸水是否转动 "+(water == 1)+"   "+data,Toast.LENGTH_SHORT).show();
                        if (water == 1){
                            final Drawable drawable = waterImg.getBackground();
                            if(drawable instanceof AnimationDrawable){
                                waterImg.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (((AnimationDrawable)drawable).isRunning()){
                                            ((AnimationDrawable)drawable).stop();
                                        }
                                        ((AnimationDrawable)drawable).start();
                                    }
                                });
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //刷子
                if (data.contains(UtilsKey.PAGE_CODE_BRUSH)||data.contains(UtilsKey.PAGE_CODE_BRUSHx)){
                    try {
                        int brush = Utils.dataToHexToIntStringII(data,UtilsKey.PAGE_CODE_BRUSHx);
                        int id = Utils.getBrushImg(brush);
                        brushImg.setBackgroundResource(id);
//                        Toast.makeText(DetailActivity.this,"刷盘是否转动 "+(brush == 1)+"   "+data,Toast.LENGTH_SHORT).show();
                        if (brush == 1){
                            final Drawable drawable = brushImg.getBackground();
                            if(drawable instanceof AnimationDrawable){
                                brushImg.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (((AnimationDrawable)drawable).isRunning()){
                                            ((AnimationDrawable)drawable).stop();
                                        }
                                        ((AnimationDrawable)drawable).start();
                                    }
                                });
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



                //状态
                if (data.contains(UtilsKey.PAGE_CODE_STATUS_BLACK)||data.contains("11 03 00 00")||data.contains("11 03 00")){
//                    Toast.makeText(DetailActivity.this,"状态栏获取到黑色",Toast.LENGTH_SHORT).show();
                    statusEdt.setTextColor(getResources().getColor(R.color.black));
                }
                if (data.contains(UtilsKey.PAGE_CODE_STATUS_RED)||data.contains("11 03 F8 00")||data.contains("11 03 F8")){
//                    Toast.makeText(DetailActivity.this,"状态栏获取到红色",Toast.LENGTH_SHORT).show();
                    statusEdt.setTextColor(getResources().getColor(R.color.red));
                }

                //statusEdt ascii 码填充
                if (data.contains(UtilsKey.STATUS_CODEII)||data.contains(UtilsKey.STATUS_CODEIII)||data.contains(UtilsKey.STATUS_CODEIV)){

//                    if (statusCount % 2 == 0) {

//                    Toast.makeText(DetailActivity.this,"状态栏计数："+statusCount,Toast.LENGTH_SHORT).show();
                        try {
//                            82 00 95 00 00 00 00 5A A5 13 82 01 00 20 20 20 20 20 20 D4
//                            CB D0 D0 20 20 20 20 20 20 5A A5 05 82 11 03 00 00 5A A5 05
                            String data1 = data.replace(" ", "");
                            String code = UtilsKey.STATUS_CODEII.replace(" ", "");
                            String key = data1.substring(data1.indexOf(code) + code.length(), data1.length());
                            String value = "";
                            for (String k : UtilsKey.statusMapII.keySet()) {
                                if (k.contains(key)) {
                                    value = UtilsKey.statusMapII.get(k);
                                    break;
                                }
                            }
                            statusEdt.setText(value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                    }

                    statusCount++;

                    if (statusCount==100){
                        statusCount = 0 ;
                    }

                    Log.e(TAG, "OnResult:statusCount ==0  " + statusCount);

                }


                if (data.contains(UtilsKey.UNLOAD_BRUSH_CODE_6+"00 00")){
                    is00980000 = true;
                    Log.e(TAG, "OnResult is00980000: data "+data );
                }

                if (data.contains(UtilsKey.UNLOAD_BRUSH_CODE_5+"00 02")){
                    is00990002 = true;
                    Log.e(TAG, "OnResult is00990002: data "+data );
                }
                //返回  5A A5 05 82 00 98 00 00   和 5A A5 05 82 00 99 00 02 后
//                if (is00980000&&is00990002){
//                    //清零指令
//                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_2+"00 02"));
//                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_3+"00 00"));
//                    bsBt.setBackgroundResource(R.drawable.bs0);
//                    is00990002 = false;
//                    is00980000 = false;
//
//                }

                if (data.contains(UtilsKey.UNLOAD_BRUSH_CODE_2_RE)){
                    if (is00990002){
//                        Toast.makeText(DetailActivity.this, "收到了5A A5 05 82 00 99 00 02 且 来询问00 99 的值了", Toast.LENGTH_SHORT).show();
                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_2+"00 02"));
                        bsBt.setBackgroundResource(R.drawable.bs0);
                        is00990002 = false;
                        bsBtAction = 0;
                        unloadBrushIsRunning = false;
                    }else{
                        //根据按钮状态来 判断发送 0001 还是0002
                        if (bsBtAction == 1){
                            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_2+"00 01"));

                        } else if (bsBtAction == 2){

                            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_2+"00 01"));

                        }else {
                            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_2+"00 02"));

                        }
                    }
                }

                if  (data.contains(UtilsKey.UNLOAD_BRUSH_CODE_3_RE)){
                    if (is00980000){
//                        Toast.makeText(DetailActivity.this, "收到了5A A5 05 82 00 98 00 00 且 来询问00 98 的值了", Toast.LENGTH_SHORT).show();
                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_3+"00 00"));
                        is00980000 = false;
//                        bsBtAction = 0;
                    }else{
                        //根据按钮状态来 判断发送 0000还是0001
                        if (bsBtAction ==1 ){
                            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_3+"00 00"));

                        }else if (bsBtAction == 2){
                            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_3+"00 01"));

                        }else{
                            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.UNLOAD_BRUSH_CODE_3+"00 00"));

                        }

                    }
                }




//                if (data.contains(UtilsKey.PAGE_CODE_2)&&isClickBsBt){
//                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_2_ANSWER+"00 00"));
////                    is0075 = false;
//                }


                //卸载刷子 处理刷子
//                if(data.contains(UtilsKey.BRUSH_STATUS)){
////                    is0075 = true;
//                    if (isClickBsBt){
//                        //关刷子
//                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.BRUSH_SWITCH+"0"));
//
//                        brushSwitch = 1;
//                        brushBt.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
//                        brushBt.setText("运行");
//                    }
//
//                }
            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (volumeBroadCast !=null){
            unregisterReceiver(volumeBroadCast);
            volumeBroadCast = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_PWD+"00 00"));
        volumeBroadCast = new VolumeBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainApplication.volumeAction);
        registerReceiver(volumeBroadCast,intentFilter);
    }


    class VolumeBroadCast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MainApplication.volumeAction)){
                Toast.makeText(DetailActivity.this,"水量按钮收到 "+MainApplication.volume,Toast.LENGTH_SHORT).show();
                int imgId = Utils.getVolumeWaterImg(MainApplication.volume);
                wbBt.setBackgroundResource(imgId);
            }
        }
    }
}
