package strawberry.com.strawberry;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.androidbluetooch.BluetoothLeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huanggang on 2018/3/28.
 */

public class BaseActivity extends Activity {
    public static final String TAG = "BaseActivity";

    //两次蓝牙的特征值
    public String[] bluetoothStrs = new String[2];

    protected BluetoothLeService mBluetoothLeService;

    // 蓝牙特征值
    protected static BluetoothGattCharacteristic target_chara = null;

    // 蓝牙4.0的UUID,其中0000ffe1-0000-1000-8000-00805f9b34fb是广州汇承信息科技有限公司08蓝牙模块的UUID
    public static String HEART_RATE_MEASUREMENT = "0000ffe1-0000-1000-8000-00805f9b34fb";
    // HC-06蓝牙UUID
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    private Handler mhandler = new Handler();

    protected AvailableInerface availableInerface;

    protected AvailableInerface getAvailableInerface() {
        return availableInerface;
    }


    protected DiscountInterface discountInterface;

    public DiscountInterface getDiscountInterface() {
        return discountInterface;
    }


    /**
     * 广播接收器，负责接收BluetoothLeService类发送的数据
     */
    private BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.e(TAG, "onReceive: action  "+action);
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action))// Gatt连接成功
            {


            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED// Gatt连接失败
                    .equals(action)) {

                if (getDiscountInterface()!= null ){
                    getDiscountInterface().OnDiscount();
                }
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED// 发现GATT服务器
                    .equals(action)) {
                // Show all the supported services and characteristics on the
                // user interface.
                // 获取设备的所有蓝牙服务
                if(mBluetoothLeService != null){
                    displayGattServices(mBluetoothLeService.getSupportedGattServices());
                }
                System.out.println("BroadcastReceiver :" + "device SERVICES_DISCOVERED");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {// 有效数据
                // 处理发送过来的数据
                Log.e(TAG, "接受数据： "+intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
//                listview_msg_stringReceiver(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));

                //TODO:  处理对应的 页面跳转和数据显示
                if (getAvailableInerface()!= null){
//                    bluetoothStrs[0] = bluetoothStrs[1];
//                    bluetoothStrs[1] = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
//                    getAvailableInerface().OnResult(bluetoothStrs[0]+ bluetoothStrs[1]);
                    getAvailableInerface().OnResult(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                }
            }
        }
    };



    /* 意图过滤器 */
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mBluetoothLeService = MainApplication.getmBluetoothLeService();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


    @Override
    protected void onStart() {
        super.onStart();
        // 绑定广播接收器
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mGattUpdateReceiver);
    }

    /**
     * @Title: displayGattServices @Description: TODO(处理蓝牙服务) @param 无 @return
     *         void @throws
     */
    private void displayGattServices(List<BluetoothGattService> gattServices) {

        if (gattServices == null)
            return;
        String uuid = null;
        // 服务数据,可扩展下拉列表的第一级数据
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();

        // 特征数据（隶属于某一级服务下面的特征值集合）
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();

        // 部分层次，所有特征值集合
//        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {

            // 获取服务列表
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();

            // 查表，根据该uuid获取对应的服务名称。SampleGattAttributes这个表需要自定义。

            gattServiceData.add(currentServiceData);

            System.out.println("Service uuid:" + uuid);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();

            // 从当前循环所指向的服务中读取特征值列表
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();

            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            // 对于当前循环所指向的服务中的每一个特征值
            for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();

                if (gattCharacteristic.getUuid().toString().equals(HEART_RATE_MEASUREMENT)) {
                    // 测试读取当前Characteristic数据，会触发mOnDataAvailable.onCharacteristicRead()
                    mhandler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            mBluetoothLeService.readCharacteristic(gattCharacteristic);
                        }
                    }, 200);

                    // 接受Characteristic被写的通知,收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
                    mBluetoothLeService.setCharacteristicNotification(gattCharacteristic, true);
                    target_chara = gattCharacteristic;
                    // 设置数据内容
                    // 往蓝牙模块写入数据
                    // mBluetoothLeService.writeCharacteristic(gattCharacteristic);
                }
                List<BluetoothGattDescriptor> descriptors = gattCharacteristic.getDescriptors();
                for (BluetoothGattDescriptor descriptor : descriptors) {
                    System.out.println("---descriptor UUID:" + descriptor.getUuid());
                    // 获取特征值的描述
                    mBluetoothLeService.getCharacteristicDescriptor(descriptor);
                    // mBluetoothLeService.setCharacteristicNotification(gattCharacteristic,
                    // true);
                }

                gattCharacteristicGroupData.add(currentCharaData);
            }
            // 按先后顺序，分层次放入特征值集合中，只有特征值
//            mGattCharacteristics.add(charas);
            // 构件第二级扩展列表（服务下面的特征值）
            gattCharacteristicData.add(gattCharacteristicGroupData);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
//            return true;
//        }
         return super.onKeyDown(keyCode, event);
    }
}
