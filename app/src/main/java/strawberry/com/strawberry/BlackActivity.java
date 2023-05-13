package strawberry.com.strawberry;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbluetooch.BluetoothLeService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 黑屏
 * Created by huanggang on 2018/3/28.
 */

public class BlackActivity extends BaseActivity{
    String TAG = "BlackActivity";

    // 蓝牙service,负责后台的蓝牙服务
//    private static BluetoothLeService mBluetoothLeService;

    @BindView(R.id.black_lin)
    LinearLayout linearLayout;

    @BindView(R.id.detail_txt)
    TextView detailTxt;

    private int volumeCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_layout);
        ButterKnife.bind(this);

        /* 启动蓝牙service */
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(BlackActivity.this,LoginActivity.class));
                startActivity(new Intent(BlackActivity.this,DetailActivity.class));
            }
        });

        findViewById(R.id.closebluetooth_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothLeService bluetoothLeService = MainApplication.getmBluetoothLeService();
                if (bluetoothLeService != null){
                    bluetoothLeService.disconnect();
                }
            }
        });

        String name = MainApplication.name;
        String address = MainApplication.address;
        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(address)){
            detailTxt.setText("名字："+name+"\n蓝牙地址："+address+"\n状态：连接");
        }

        detailTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BlackActivity.this,LoginActivity.class));
            }
        });


    }

    @Override
    protected AvailableInerface getAvailableInerface() {
        return new AvailableInerface() {
            @Override
            public void OnResult(String data) {
                if (data.contains(UtilsKey.PAGE_CODE)){
                    Log.e(TAG, "OnResult: has code" );
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_ANSWER+"00 00"));
                }
                if (data.contains(UtilsKey.PAGE_CODE_1)||data.contains("83 00 65 01")){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_1_ANSWER+"00 00"));
                }
                if (data.contains(UtilsKey.PAGE_CODE_2)||data.contains("83 00 75 01")){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_2_ANSWER+"00 00"));
                }

                if (data.contains(UtilsKey.PAGE_CODE_3)||data.contains("83 00 55 01")){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_3_ANSWER+"00 00"));
                }

                if (data.contains(UtilsKey.WATER_DISPLAY)||data.contains(UtilsKey.WATER_DISPLAYx)){
                    Toast.makeText(BlackActivity.this,"水量按钮 "+data,Toast.LENGTH_SHORT).show();
                    try{
                        MainApplication.volume = Utils.dataToHexToIntStringII(data,UtilsKey.WATER_DISPLAYx);
                        if (volumeCount == 0 ){
                            //保证只发一次广播
                            sendBroadcast(new Intent(MainApplication.volumeAction));
                            volumeCount++;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        };
    }

    @Override
    public DiscountInterface getDiscountInterface() {
        return new DiscountInterface() {
            @Override
            public void OnDiscount() {
                setResult(10001);
                finish();
            }
        };
    }


    /* BluetoothLeService绑定的回调函数 */
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();

            MainApplication.setmBluetoothLeService(mBluetoothLeService);

            if (!mBluetoothLeService.initialize()) {
                Log.e("MainActivity.this", "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up
            // initialization.
            // 根据蓝牙地址，连接设备
            // 每次连接之前关闭上一次连接，这样在第二次连接蓝牙的时候速度快
            // 博客参考http://bbs.eeworld.com.cn/thread-438571-1-1.html
//            mBluetoothLeService.close();
            mBluetoothLeService.connect(MainApplication.address);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();


        mBluetoothLeService = null;

        unbindService(mServiceConnection);
    }
}
