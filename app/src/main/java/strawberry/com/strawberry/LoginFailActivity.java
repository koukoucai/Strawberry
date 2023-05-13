package strawberry.com.strawberry;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFailActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.back_bt)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_tip3);
        ButterKnife.bind(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.back_bt){
            //对密码清零
            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_PWD+"00 00"));
           finish();
        }
    }

    @Override
    public AvailableInerface getAvailableInerface() {
        return new AvailableInerface() {
            @Override
            public void OnResult(String data) {
                if (data.contains(UtilsKey.PAGE_CODE)){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_ANSWER+"00 04"));
                }
            }
        };
    }
}
