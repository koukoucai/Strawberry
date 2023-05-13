package strawberry.com.strawberry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import strawberry.com.strawberry.views.KeyBoardView;


/**
 * 设置密码和重置密码
 */
public class SetPwdActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.setpwd1_edt)
    EditText editText1;

    @BindView(R.id.setpwd2_edt)
    EditText editText2;

    @BindView(R.id.setpwd_keyboardview)
    KeyBoardView keyBoardView;


    @BindView(R.id.img01)
    ImageView img01;

    @BindView(R.id.img02)
    ImageView img02;

    String pwd1,pwd2;

    int count = 0;

    public static final int REQUESTCODE = 1;
    public static final String PWDEDT1 = "pwdedt1";
    public static final String PWDEDT2 = "pwdedt2";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_pwd_layout);
        ButterKnife.bind(this);

        showWhichImg(0);

        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDI+" 00 00"));
        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDII+" 00 00"));

        keyBoardView.getBt_cancel().setText("取消");
        keyBoardView.getBt_ok().setText("完成");
        keyBoardView.setOnResult(new KeyBoardView.OnResult() {
            @Override
            public void result(String result) {
//                if (count == 0){
//                    editText1.setText(result);
//                    editText1.setSelection(result.length());
//                    showWhichImg(1);
//                }else{
//                    editText2.setText(result);
//                    editText2.setSelection(result.length());
//                    showWhichImg(2);
//                }
            }
        }).setOnResultSubmit(new KeyBoardView.OnResultSubmit() {
            @Override
            public void submit(String result) {
                pwd1 = editText1.getText().toString();
                pwd2 = editText2.getText().toString();

                if (!TextUtils.isEmpty(pwd1)&&!TextUtils.isEmpty(pwd2)){
                    submitPwd(true);
                    submitPwd(false);

//                    int pwd11 = Integer.valueOf(pwd1);
//
//                    int pwd22 = Integer.valueOf(pwd2);
//
//                    String pwdi = Integer.toHexString(pwd11);
//                    switch (Utils.passwordJudgement(pwd11)){
//                        case 0:
//                            break;
//                        case 1:
//                            pwdi = "0"+pwdi;
//                            break;
//                        case 2:
//                            pwdi = "00"+pwdi;
//                            break;
//                        case 3:
//                            pwdi = "000"+pwdi;
//                            break;
//                        case 4:
//                            pwdi = "0"+pwdi;
//                            break;
//                    }
//
//
//                    String pwdii = Integer.toHexString(pwd22);
//
//                    switch (Utils.passwordJudgement(pwd22)){
//                        case 0:
//                            break;
//                        case 1:
//                            pwdii = "0"+pwdii;
//                            break;
//                        case 2:
//                            pwdii = "00"+pwdii;
//                            break;
//                        case 3:
//                            pwdii = "000"+pwdii;
//                            break;
//                        case 4:
//                            pwdii = "0"+pwdii;
//                            break;
//                    }
//
//                    Log.e(TAG, "reset : pwd: "+(UtilsKey.PAGE_CODE_RESET_PWDI+" "+pwdi)+ "    "+ (UtilsKey.PAGE_CODE_RESET_PWDII+" "+pwdii));
////                        Toast.makeText(SetPwdActivity.this,"这是测试提示，密码是"+result,Toast.LENGTH_LONG).show();
//                    if (Utils.passwordJudgement(pwd11) == 4){
//                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDIx+pwdi));
//                    }else{
//                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDI+pwdi));
//                    }
//
//                    if (Utils.passwordJudgement(pwd22) == 4){
//                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDIIx+pwdii));
//
//                    }else{
//                        Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDII+pwdii));
//                    }
                }else{
                    Toast.makeText(SetPwdActivity.this,"设置密码不能为空",Toast.LENGTH_SHORT).show();
                }

            }
        }).setOnCancel(new KeyBoardView.OnCancel() {
            @Override
            public void cancel() {
                //对密码清零
//                Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_PWD+"00 00"));
                Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDI+" 00 00"));
                Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDII+" 00 00"));
            }
        });

//        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    keyBoardView.clearInputPwd();
//                    count = 0;
//                    showWhichImg(1);
//                }
//            }
//        });
//
//        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    keyBoardView.clearInputPwd();
//                    count++;
//                    showWhichImg(2);
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.setpwd1_edt||id == R.id.setpwd2_edt){
            startActivityForResult(new Intent(SetPwdActivity.this,SetPwdIIActivity.class),REQUESTCODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE){
            if (resultCode == Activity.RESULT_OK){
                String pwdedt1 = data.getStringExtra(PWDEDT1);
                if (!TextUtils.isEmpty(pwdedt1)){
                    editText1.setText(pwdedt1);
                }
                String pwdedt2 = data.getStringExtra(PWDEDT2);
                if (!TextUtils.isEmpty(pwdedt2)){
                    editText2.setText(pwdedt2);
                }
            }else if(resultCode == Activity.RESULT_CANCELED){

            }
        }

    }

    @Override
    protected AvailableInerface getAvailableInerface() {
        return new AvailableInerface() {
            @Override
            public void OnResult(String data) {
                if (data.contains(UtilsKey.PAGE_CODE)){
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_ANSWER+"00 07"));
//                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_ANSWER+"00 08"));
                }
                if(data.contains(UtilsKey.PAGE_CODE_RESET_SUCCESS)||data.contains("03 00 0B")){
                    startActivity(new Intent(SetPwdActivity.this,ResetSuccessActivity.class));
                    //对密码清零
//                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_PWD+"00 00"));
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDI+" 00 00"));
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDII+" 00 00"));
                    finish();
                }if(data.contains(UtilsKey.PAGE_CODE_RESET_FAIL)||data.contains("03 00 0A")){
                    startActivity(new Intent(SetPwdActivity.this,ResetFailActivity.class));
                    //对密码清零
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDI+" 00 00"));
                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDII+" 00 00"));
//                    Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_PWD+"00 00"));
                    finish();
                }
                if (data.contains(UtilsKey.PAGE_CODE_RESET_PWDI_CONFIRM)||data.contains("83 00 10 01")){//询问00 10 密码
                    submitPwd(true);
                    Toast.makeText(SetPwdActivity.this,"这是第一行密码框输入  "+editText1.getText().toString(),Toast.LENGTH_LONG).show();
                }
                if (data.contains(UtilsKey.PAGE_CODE_RESET_PWDII_CONFIRM)||data.contains("83 00 20 01")){//询问00 20 密码
                    submitPwd(false);
                    Toast.makeText(SetPwdActivity.this,"这是第二行密码框输入  "+editText1.getText().toString(),Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private void showWhichImg(int flag){
        if (flag == 1){
            img01.setVisibility(View.VISIBLE);
            img02.setVisibility(View.INVISIBLE);
        }else if (flag == 2){
            img01.setVisibility(View.INVISIBLE);
            img02.setVisibility(View.VISIBLE);
        }else{
            img01.setVisibility(View.INVISIBLE);
            img02.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 提交密码
     */
    private void submitPwd(boolean isFirst){

        pwd1 = editText1.getText().toString();
        pwd2 = editText2.getText().toString();

        if (isFirst&&!TextUtils.isEmpty((pwd1))){
            int pwd11 = Integer.valueOf(pwd1);
            String pwdi = Integer.toHexString(pwd11);
            switch (Utils.passwordJudgement(pwd11)){
                case 0:
                    break;
                case 1:
                    pwdi = "0"+pwdi;
                    break;
                case 2:
                    pwdi = "00"+pwdi;
                    break;
                case 3:
                    pwdi = "000"+pwdi;
                    break;
                case 4:
                    pwdi = "0"+pwdi;
                    break;
            }
            if (Utils.passwordJudgement(pwd11) == 4){
                Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDIx+pwdi));
            }else{
                Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDI+pwdi));
            }
        }else if (isFirst&&TextUtils.isEmpty((pwd1))){
            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDI+"00 00"));
        }
        if (!isFirst&&!TextUtils.isEmpty(pwd2)){
            int pwd22 = Integer.valueOf(pwd2);

            String pwdii = Integer.toHexString(pwd22);

            switch (Utils.passwordJudgement(pwd22)){
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

            if (Utils.passwordJudgement(pwd22) == 4){
                Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDIIx+pwdii));

            }else{
                Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDII+pwdii));
            }
        }else if (!isFirst && TextUtils.isEmpty(pwd2)){
            Utils.writeCharacteristic(MainApplication.getmBluetoothLeService(),target_chara,Utils.hexString2Bytes(UtilsKey.PAGE_CODE_RESET_PWDII+"00 00"));
        }
    }
}
