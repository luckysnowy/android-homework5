package com.example.dell.mygym;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends AppCompatActivity {

    private Button bt;
    private Button lrg;
    private Button dq;
    EditText username, password;
    private static final String TAG = "loginActivity";
    private static final String APP_ID = "1108043938";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bmob.initialize(this, "d35fe3de7bf3fd8716c88e949e59dc97");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,loginActivity.this.getApplicationContext());
        bt=(Button) findViewById(R.id.log);
        username= (EditText)findViewById(R.id.usname);
        password = (EditText)findViewById(R.id.pass);
        bt.setOnClickListener(new OnClickListener(){
            @Override
            public  void onClick(View v) {
                if (checkEdit()) {
                    if(isConnectInternet()) {
                        BmobUser user = new BmobUser();
                        user.setUsername(username.getText().toString());
                        user.setPassword(password.getText().toString());
                        user.login(new SaveListener<BmobUser>() {
                            @Override
                            public void done(BmobUser bmobUser, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(loginActivity.this, bmobUser.getUsername() + "登录成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    // Log.e("登录失败", "原因: ", e);
                                    Toast.makeText(loginActivity.this, "登录失败,原因：" + e, Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                    }
                    else{
                        UserService userService = new UserService(loginActivity.this);

                        boolean flag = userService.Login(username.getText().toString(),password.getText().toString());
                        if (flag) {
                            Toast.makeText(loginActivity.this,username.getText().toString()+"登录成功！",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loginActivity.this, MainActivity.class);
                            // intent.putExtra("name",username.getText().toString());//将用户名从loginActicity传给MainActivity
                            startActivity(intent);
                        } else {
                            Toast.makeText(loginActivity.this, "登录失败！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        lrg=(Button)findViewById(R.id.regl);
        lrg.setOnClickListener(new OnClickListener(){
            @Override
            public  void onClick(View v2){
                Intent intent=new Intent(loginActivity.this,regActivity.class);
                startActivity(intent);
            }
        });

        dq=(Button)findViewById(R.id.qql);
        dq.setOnClickListener(new OnClickListener(){
            @Override
            public  void onClick(View v3){
                if(isConnectInternet()==true){//联网状态
                    mIUiListener = new BaseUiListener();
                    //all表示获取所有权限
                    mTencent.login(loginActivity.this,"all", mIUiListener);

                }else{
                    Toast.makeText(loginActivity.this, "网络连接不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //检查注册信息
    public boolean checkEdit(){
        if(username.getText().toString().equals("")){
            Toast.makeText(loginActivity.this, "账户不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.getText().toString().equals("")){
            Toast.makeText(loginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(loginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        String name1=null;
                        try {
                            JSONObject jo = (JSONObject) response;
                            name1 = jo.getString("nickname");
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(loginActivity.this, name1 + "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(loginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(loginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(loginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public boolean isConnectInternet() {

        ConnectivityManager conManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE );

        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        if (networkInfo != null ){ // 注意，这个判断一定要的哦，要不然会出错

            return networkInfo.isAvailable();


        }
        return false ;
    }

    private long exitTime = 0;


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(loginActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }

    }
}
