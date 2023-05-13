package strawberry.com.strawberry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetSuccessActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.relogin_bt)
    Button bt1;

    @BindView(R.id.back_bt)
    Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_success);
        ButterKnife.bind(this);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

//        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_PWD+"00 00"));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.relogin_bt){
           //返回登录
            startActivity(new Intent(ResetSuccessActivity.this,LoginActivity.class));
            finish();
        }else if(id == R.id.back_bt){
            //返回主页
            startActivity(new Intent(ResetSuccessActivity.this,DetailActivity.class));
            finish();
        }
    }

    @Override
    protected AvailableInerface getAvailableInerface() {
        return new AvailableInerface() {
            @Override
            public void OnResult(String data) {
                if (data.contains(UtilsKey.PAGE_CODE)){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_ANSWER+"00 0B"));
                }
                if (data.contains(UtilsKey.PAGE_CODE_RESET_PWDI_CONFIRM)){//询问00 10 密码
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDI+"00 00"));
                }
                if (data.contains(UtilsKey.PAGE_CODE_RESET_PWDII_CONFIRM)){//询问00 20 密码
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDII+"00 00"));
                }
            }
        };
    }
}
