package strawberry.com.strawberry.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import strawberry.com.strawberry.R;

/**
 * Created by huanggang on 2018/3/26.
 */

public class KeyBoardView extends LinearLayout implements View.OnClickListener {

//    @BindView(R.id.key_one) Button bt1;
//    @BindView(R.id.key_two) Button bt2;
//    @BindView(R.id.key_three) Button bt3;
//    @BindView(R.id.key_four) Button bt4;
//    @BindView(R.id.key_five) Button bt5;
//    @BindView(R.id.key_six) Button bt6;
//    @BindView(R.id.key_seven) Button bt7;
//    @BindView(R.id.key_eight) Button bt8;
//    @BindView(R.id.key_nine) Button bt9;
//    @BindView(R.id.key_zero) Button bt0;
//    @BindView(R.id.key_ok) Button bt_ok;
//    @BindView(R.id.key_back_space) Button bt_back_space;
//    @BindView(R.id.key_cancel) Button bt_cancel;

    public static final String TAG = "KeyBoardView";

    Button bt1;
    Button bt2;
    Button bt3;
    Button bt4;
    Button bt5;
    Button bt6;
    Button bt7;
    Button bt8;
    Button bt9;
    Button bt0;
    Button bt_ok;
    Button bt_back_space;
    Button bt_cancel;

    String result = "";
    OnResult onResult;
    OnResultSubmit onResultSubmit;
    OnCancel onCancel;


    public KeyBoardView(Context context) {
        super(context);
        init(context);
    }

    public KeyBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KeyBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public KeyBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.keyboard_layout3, null);
//        ButterKnife.bind(context,view);
        bt1 = view.findViewById(R.id.key_one);
        bt2 = view.findViewById(R.id.key_two);
        bt3 = view.findViewById(R.id.key_three);
        bt4 = view.findViewById(R.id.key_four);
        bt5 = view.findViewById(R.id.key_five);
        bt6 = view.findViewById(R.id.key_six);
        bt7 = view.findViewById(R.id.key_seven);
        bt8 = view.findViewById(R.id.key_eight);
        bt9 = view.findViewById(R.id.key_nine);
        bt0 = view.findViewById(R.id.key_zero);
        bt_ok = view.findViewById(R.id.key_ok);
        bt_back_space = view.findViewById(R.id.key_back_space);
        bt_cancel = view.findViewById(R.id.key_cancel);


        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        bt0.setOnClickListener(this);
        bt_ok.setOnClickListener(this);
        bt_back_space.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);

        addView(view);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.key_one:
                if (result.length()<5){
                    result = result+bt1.getText().toString();
                }
                break;
            case R.id.key_two:
                if (result.length()<5) {
                    result = result + bt2.getText().toString();
                }
                break;
            case R.id.key_three:
                if (result.length()<5) {
                    result = result + bt3.getText().toString();
                }
                break;
            case R.id.key_four:
                if (result.length()<5) {
                    result = result + bt4.getText().toString();
                }
                break;
            case R.id.key_five:
                if (result.length()<5) {
                    result = result + bt5.getText().toString();
                }
                break;
            case R.id.key_six:
                if (result.length()<5) {
                    result = result + bt6.getText().toString();
                }
                break;
            case R.id.key_seven:
                if (result.length()<5) {
                    result = result + bt7.getText().toString();
                }
                break;
            case R.id.key_eight:
                if (result.length()<5) {
                    result = result + bt8.getText().toString();
                }
                break;
            case R.id.key_nine:
                if (result.length()<5) {
                    result = result + bt9.getText().toString();
                }
                break;
            case R.id.key_zero:
                if (result.length()<5) {
                    result = result + bt0.getText().toString();
                }
                break;
            case R.id.key_ok:
                //提交代码
                if (onResultSubmit != null&&!TextUtils.isEmpty(result)){
//                    if (result.length()<5){
//                        Toast.makeText(getContext(),"密码必须是五位", Toast.LENGTH_SHORT).show();
//                    }else{
                        Log.e(TAG, "onClickkey_ok  : "+result);
                        onResultSubmit.submit(result);
//                    }
//                    result = "";
                }else{
                    Toast.makeText(getContext(),"密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.key_back_space:
                if (!TextUtils.isEmpty(result)){
                    result = result.substring(0,result.length()-1);
                }
                break;
            case R.id.key_cancel:
                if (getContext() instanceof Activity){
                    if (onCancel != null ){
                        onCancel.cancel();
                    }
                    ((Activity)getContext()).finish();
                }
                break;
        }
        if (onResult != null) {
            onResult.result(result);
        }
    }

    public interface OnResult {
        void result(String result);
    }

    public KeyBoardView setOnResult(OnResult onResult) {
        this.onResult = onResult;
        return this;
    }

    public interface OnResultSubmit {
        void submit(String result);
    }

    public KeyBoardView setOnResultSubmit(OnResultSubmit onResultSubmit) {
        this.onResultSubmit = onResultSubmit;
        return this;
    }

    public interface OnCancel{
        void cancel();
    }

    public KeyBoardView setOnCancel(OnCancel onCancel) {
        this.onCancel = onCancel;
        return this;
    }

    public void clearInputPwd(){
        result = "";
    }


    public Button getBt_cancel(){
        return bt_cancel;
    }

    public Button getBt_ok(){
        return bt_ok;
    }
}
