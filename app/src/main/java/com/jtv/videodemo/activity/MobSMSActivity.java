package com.jtv.videodemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jtv.videodemo.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MobSMSActivity extends AppCompatActivity {

    private EditText et_phone;
    private Button btn_send_sms;
    private Button btn_send_voice;
    private EditText et_verification_code;
    private Button btn_submit;

    private static final String TAG = "MobSMSActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mob_sms);
        et_phone = findViewById(R.id.et_phone);
        btn_send_sms = findViewById(R.id.btn_send_sms);
        btn_send_voice = findViewById(R.id.btn_send_voice);
        et_verification_code = findViewById(R.id.et_verification_code);
        btn_submit = findViewById(R.id.btn_submit);

        //获取验证码
        btn_send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 短信验证码");
                String phoneNum = et_phone.getText().toString().trim();
                sendCode("86", phoneNum, false);
            }
        });

        btn_send_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = et_phone.getText().toString().trim();
                sendCode("86", phoneNum, true);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 提交？");
                String phoneNum = et_phone.getText().toString().trim();
                String verificationCode = et_verification_code.getText().toString().trim();

                submitCode("86", phoneNum, verificationCode);
            }
        });
    }


    /**
     * 以可视化界面完成操作
     */
  /*  public void sendCode() {
        RegisterPage page = new RegisterPage();
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country"); // 国家代码，如“86”
                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                    // TODO 利用国家代码和手机号码进行后续的操作
                    Log.i(TAG, "afterEvent: ");

                } else{
                    // TODO 处理错误的结果
                }
            }
        });
        page.show(this);
    }*/

    /**
     * 无页面的
     * // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
     */
    public void sendCode(String country, String phone, boolean getVoice) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(eventHandler);
        // 触发操作
        if (getVoice) {
            SMSSDK.getVoiceVerifyCode(country, phone);
        } else {
            SMSSDK.getVerificationCode(country, phone);
        }
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {

        SMSSDK.registerEventHandler(eventHandler);
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    // 注册一个事件回调，用于处理提交验证码操作的结果
    private EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            Log.d(TAG, "afterEvent: event = " + event);
            Log.d(TAG, "afterEvent: result = " + result);
            if (data != null)
                Log.d(TAG, "afterEvent: data = " + data.toString());

            if (result == SMSSDK.RESULT_COMPLETE) {
                // TODO 处理验证(发送)成功的结果
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//验证码验证
                    showToast("验证成功");
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取短信
                    showToast("发送短信验证码成功");
                } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                    //获取语音
                    showToast("请注意接听语音验证码");
                }

            } else {
                // TODO 处理错误的结果
                if (data != null && TextUtils.isEmpty(data.toString()))
                    return;
                String[] split = data.toString().split("\\{");
                if (split[1] != null) {
                    try {
                        JSONObject jsonObject = new JSONObject("{" + split[1]);
                        Log.i(TAG, "afterEvent: " + jsonObject.get("status") + ", " + jsonObject.get("detail"));
                        showToast(jsonObject.get("detail").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    };

    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }


    private void showToast(final String content) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MobSMSActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
