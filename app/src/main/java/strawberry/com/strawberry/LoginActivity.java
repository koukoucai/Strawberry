package strawberry.com.strawberry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.login_edt)
    EditText editText;

    @BindView(R.id.close_bt)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_tip);
        ButterKnife.bind(this);

        editText.setOnClickListener(this);
        button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.login_edt){
            startActivity(new Intent(LoginActivity.this,Login2Activity.class));
        }else if(id == R.id.close_bt){
            //TODO: 黑屏
//            startActivity(new Intent(LoginActivity.this,BlackActivity.class));
            finish();
        }
    }

    @Override
    public AvailableInerface getAvailableInerface() {
        return new AvailableInerface() {
            @Override
            public void OnResult(String data) {
                if (data.contains(UtilsKey.PAGE_CODE)){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_ANSWER+"00 01"));
                }

                if (data.contains(UtilsKey.PAGE_CODE_1)){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_1_ANSWER+"00 00"));
                }
                if (data.contains(UtilsKey.PAGE_CODE_2)){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_2_ANSWER+"00 00"));
                }

                if (data.contains(UtilsKey.PAGE_CODE_3)){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_3_ANSWER+"00 00"));
                }
            }
        };
    }
}
