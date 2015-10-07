package com.metoo.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.metoo.MainActivity;
import com.metoo.R;
import com.metoo.adapter.Welcome_ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class Welcome extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {


    //引导图片资源
    private static final int[] pics = {
            R.mipmap.welcome_page1,
            R.mipmap.welcome_page2,
            R.mipmap.welcome_page3,
            R.mipmap.welcome_page4,
    };
    //底部小图片
    private ImageView[] dots;

    //记录当前选中位置
    private int currentIndex;

    private Button mWelcome_btn;
    private List<View> mViews;
    private ViewPager mViewPager;
    private Welcome_ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //记录应用第一次被加载，写入sharepreferences文件里面
        SharedPreferences preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstIn", false);
        editor.commit();

        //加载ViewPager
        mViews = (new ArrayList<View>());

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mViews.add(iv);
        }
        mWelcome_btn = (Button) findViewById(R.id.welcome_btn);
        mViewPager = ((ViewPager) findViewById(R.id.welcome_viewpager));

        //初始化Adapter
        mViewPagerAdapter = new Welcome_ViewPagerAdapter(mViews);
        mViewPager.setAdapter(mViewPagerAdapter);

        //绑定回调
        mViewPager.addOnPageChangeListener(this);

        //初始化底部小点
        initDots();
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.welcome_linearlayout);
        dots = new ImageView[pics.length];

        //循环获取小点图片
        for (int i = 0; i < pics.length; i++) {
            dots[i] = ((ImageView) ll.getChildAt(i));
            dots[i].setEnabled(true);//初始都设为不可点状态
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置为选中状态
    }

    /**
     * 设置当前的引导页
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }

        mViewPager.setCurrentItem(position);
    }

    /**
     * 设置当前引导小点选中
     *
     * @param position
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length - 1 || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    //当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int position) {
        setCurDot(position);
        btnIsVisible(position);
    }

    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        int position = ((Integer) v.getTag());
        setCurDot(position);
        setCurView(position);
        btnIsVisible(position);
    }

    //跳转按钮
    public void welcome_btn(View view) {
        finish();
        startActivity(new Intent(Welcome.this, MainActivity.class));
    }

    public void btnIsVisible(int position) {
        if (position == pics.length - 1) {
            mWelcome_btn.setVisibility(View.VISIBLE);
//            int width = mWelcome_btn.getWidth();
//            float x = ViewCompat.getX(mWelcome_btn);
            ViewCompat.setAlpha(mWelcome_btn, 0);
//            ViewCompat.setX(mWelcome_btn, x + width + 10);
            ViewCompat.animate(mWelcome_btn).
                    setDuration(2000).
                    alphaBy(1.0f).
//                    translationXBy(x).
                    start();
        } else {
//            int width = mViewPager.getWidth();
//            ViewCompat.setX(mWelcome_btn,width-10-mWelcome_btn.getWidth());
            mWelcome_btn.setVisibility(View.GONE);
        }
    }

}
