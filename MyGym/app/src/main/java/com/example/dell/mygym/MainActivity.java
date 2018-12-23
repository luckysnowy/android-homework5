package com.example.dell.mygym;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private RadioGroup radioGroup;
    private Fragment[] mFragments;
    private int mIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        setRadioGroupListener();
    }

    private void initFragment() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        frameLayout = (FrameLayout) findViewById(R.id.fl_content);
        dashboardFragment h= new dashboardFragment();
        scheduleFragment s= new scheduleFragment();
        anounceFragment a = new anounceFragment();
        coachesFragment c= new coachesFragment();
         myFragment m = new myFragment();
        //添加到数组
        mFragments = new Fragment[]{h, s, a, c, m};
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //添加首页
        ft.add(R.id.fl_content, h).commit();
        //默认设置为第0个
        setIndexSelected(0);
    }

    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fw=fragmentManager.findFragmentById(R.id.fl_content);

        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if (!mFragments[index].isAdded()) {
            ft.remove(fw);
            ft.add(R.id.fl_content, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }
        ft.commit();
        //再次赋值
        mIndex = index;

    }

    private void setRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_home:

                        setIndexSelected(0);
                        break;
                    case R.id.rb_sc:
                        setIndexSelected(1);
                        break;
                    case R.id.rb_an:
                        setIndexSelected(2);
                        break;
                    case R.id.rb_co:
                        setIndexSelected(3);
                        break;
                    case R.id.rb_m:
                        setIndexSelected(4);
                        break;
                    default:
                        setIndexSelected(0);
                        break;
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //仅当activity为task根（即首个启动activity）时才生效,这个方法不会改变task中的activity状态，
            // 按下返回键的作用跟按下HOME效果一样；重新点击应用还是回到应用退出前的状态；
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }

    }
}
