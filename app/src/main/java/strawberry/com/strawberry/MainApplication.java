package strawberry.com.strawberry;

import android.app.Application;
import android.bluetooth.BluetoothGattCharacteristic;

import com.example.androidbluetooch.BluetoothLeService;

/**
 * Created by huanggang on 2018/3/26.
 */

public class MainApplication extends Application {

    // 蓝牙service,负责后台的蓝牙服务
    private static BluetoothLeService mBluetoothLeService;

    public static String name;
    public static String address;

    //水量按钮
    public static int volume = 0;
    //水量广播
    public static final String volumeAction = "volumeAction";


    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static BluetoothLeService getmBluetoothLeService() {
        return mBluetoothLeService;
    }

    public static void setmBluetoothLeService(BluetoothLeService mBluetoothLeService) {
        MainApplication.mBluetoothLeService = mBluetoothLeService;
    }


//    public static BluetoothGattCharacteristic target_chara;
//
//    public static BluetoothGattCharacteristic getTarget_chara() {
//        return target_chara;
//    }
//
//    public static void setTarget_chara(BluetoothGattCharacteristic target_chara) {
//        MainApplication.target_chara = target_chara;
//    }
}
