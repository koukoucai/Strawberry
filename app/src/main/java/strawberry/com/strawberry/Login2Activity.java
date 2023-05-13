package strawberry.com.strawberry;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import strawberry.com.strawberry.views.KeyBoardView;

public class Login2Activity extends BaseActivity {

    @BindView(R.id.login2_edt)
    EditText editText;

    @BindView(R.id.keyboardview)
    KeyBoardView keyBoardView;


    boolean isSubmit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_tip2);
        ButterKnife.bind(this);



//        editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
//            }
//        });
//        editText.setSelected(false);
//        editText.setLongClickable(false);



        keyBoardView.setOnResult(new KeyBoardView.OnResult() {
            @Override
            public void result(String result) {
                editText.setText(result);
                editText.setSelection(result.length());
            }
        }).setOnResultSubmit(new KeyBoardView.OnResultSubmit() {
            @Override
            public void submit(String result) {
                //TODO: 提交密码
                if (!TextUtils.isEmpty(result)){
                    editText.setFocusable(false);
                    Toast.makeText(Login2Activity.this,"这是测试提示，密码是"+result,Toast.LENGTH_LONG).show();
                    int pwd = Integer.valueOf(result);
                    String pwdii = Integer.toHexString(pwd);
                    Log.e(TAG, "submit: pwd: "+(UtilsKey.PAGE_CODE_PWD+pwdii));
                    switch (Utils.passwordJudgement(pwd)){
                        case 0:
                            break;
                        case 1:
                            pwdii = "0"+pwdii;
                            break;
                        case 2:
                            pwdii = "00"+pwdii;
                            break;
                        case 3:
                            pwdii = "000"+pwdii;
                            break;
                        case 4:
                            pwdii = "0"+pwdii;
                            break;
                    }
                    byte[] bytes;
                    if (Utils.passwordJudgement(pwd) == 4){
                        bytes = Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDII+pwdii);

                    }else{
                        bytes = Utils.hexString2Bytes(UtilsKey.PAGE_CODE_PWD+pwdii);
                    }
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,bytes);
//                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_PWD+"00 00"));
                    isSubmit = true;

                }

//                Integer.toHexString(xxx)  //十进制转十六进制

                //TODO: 失败
//                startActivity(new Intent(Login2Activity.this,LoginFailActivity.class));

            }
        });

    }

    @Override
    public AvailableInerface getAvailableInerface() {
        return new AvailableInerface() {
            @Override
            public void OnResult(String data) {
                if (data.contains(UtilsKey.PAGE_CODE)){
//                    if (isSubmit){
//                        //提交过密码后提交页面 测试提交详细页面页码
//                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_ANSWER+"00 05"));
//                    }else{
                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_ANSWER+"00 01"));

//                    }
                }
                if (data.contains(UtilsKey.PAGE_CODE_LOGIN_FAIL)||data.contains("03 00 04")){
                    //登录失败
                    startActivity(new Intent(Login2Activity.this,LoginFailActivity.class));

                    //对密码清零
//                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_PWD+"00 00"));
                }

                if (data.contains(UtilsKey.PAGE_CODE_LOGIN_DETAIL)||data.contains("03 00 05")){
                    //登录成功 跳转详情
                    startActivity(new Intent(Login2Activity.this,DetailActivity.class));

                    //对密码清零
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_PWD+"00 00"));
                }
            }
        };
    }
}
