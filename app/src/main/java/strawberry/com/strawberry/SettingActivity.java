package strawberry.com.strawberry;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.back_bt)
    Button backBt;

    @BindView(R.id.clear_bt)
    Button clearBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);

        backBt.setOnClickListener(this);
        clearBt.setOnClickListener(this);
        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_CLEAR+"00 00"));


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.back_bt){
           finish();
        }else if(id == R.id.clear_bt){
            //TODO: 清零
            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_CLEAR+"00 02"));
            clearBt.setText("累计清零中");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (clearBt !=null){
                        clearBt.setText("累计已清零");
                    }
                }
            },1000);
        }
    }


    @Override
    public AvailableInerface getAvailableInerface() {
        return new AvailableInerface() {
            @Override
            public void OnResult(String data) {

//                累计清零   5A A5 03 81 03 02
//                          5A A5 04 83 00 35 01
//                          5A A5 05 82 00 35 00

                Log.e(TAG, "OnResult: 累计清零 "+data );

                //各种处理
                if (data.contains(UtilsKey.PAGE_CODE)){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_ANSWER+"00 0C"));
                }
//                if(data.equals("01 ")){
                if (data.contains(UtilsKey.PAGE_CODE_CLEAR_RECEIVE+"00 01")||data.contains("00 35 00 01")){
//                    ||data.contains(UtilsKey.PAGE_CODE_CLEAR_RECEIVEx+"01")
                    Log.e(TAG, "OnResult: 累计已清零 "+data );
                    clearBt.setText("累计已清零");
                }
//                if(data.contains(UtilsKey.PAGE_CODE_CLEAR+"00 02")){
//                    clearBt.setText("累计清零中");
//                }
//                if(data.contains(UtilsKey.PAGE_CODE_CLEAR_RECEIVE+"00 00")||data.contains(UtilsKey.PAGE_CODE_CLEAR_RECEIVEx+"00")){
//                    clearBt.setText("累计清零");
//                }
            }
        };
    }

}
