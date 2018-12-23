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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class regActivity extends AppCompatActivity {
    private Button rl;
    private Button regs;
    EditText username, password, cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "d35fe3de7bf3fd8716c88e949e59dc97");
        setContentView(R.layout.reg);
        rl = (Button) findViewById(R.id.retu);
        password = (EditText) findViewById(R.id.pass0);
        cpassword = (EditText) findViewById(R.id.pass1);
        username = (EditText) findViewById(R.id.account1);
        rl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent intent = new Intent(regActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        regs = (Button) findViewById(R.id.rega);
        regs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v2) {
                if(isConnectInternet()==true){//判断是否联网
                    //Toast.makeText(regActivity.this, "网络连接上了", Toast.LENGTH_SHORT).show();
                    if(checkEdit()==true) {//判断输入是否合法
                        BmobUser user =new BmobUser();

                        user.setUsername(username.getText().toString());
                        user.setPassword(password.getText().toString());

                        user.signUp(new SaveListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                if(e==null)
                                {
                                    SLUser suser=new SLUser();
                                    suser.setUsername(username.getText().toString());
                                    suser.setPassword(password.getText().toString());
                                    UserService userService = new UserService(getBaseContext());
                                    boolean flag = userService.Register(suser);
                                    Toast.makeText(regActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                }else
                                {
                                    // Log.e("注册失败", "原因: ",e );
                                    Toast.makeText(regActivity.this,"注册失败，原因"+e,Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                    }
                }
                else{
                    Toast.makeText(regActivity.this, "网络连接不可用，请检查网络设置！", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    //检查注册信息
    public boolean checkEdit() {
        if (username.getText().toString().equals("")) {
            Toast.makeText(regActivity.this, "账户不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().equals("")) {
            Toast.makeText(regActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!cpassword.getText().toString().equals(password.getText().toString())) {
            Toast.makeText(regActivity.this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
            Toast.makeText(regActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }

    }
    public boolean isConnectInternet() {

        ConnectivityManager conManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE );

        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        if (networkInfo != null ){ // 注意，这个判断一定要的哦，要不然会出错

            return networkInfo.isAvailable();


        }
        return false ;
    }
}
