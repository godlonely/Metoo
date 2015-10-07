package com.metoo.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by GodLonely
 * on 2015/9/30 0030
 * Email:yigeren631994205@hotmail.com
 */
public class Welcome_ViewPagerAdapter extends PagerAdapter {

    //界面列表
    private List<View> mViewList;

    public Welcome_ViewPagerAdapter(List<View> viewList) {
        mViewList = viewList;
    }

    //销毁当前item位置的界面
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        ((ViewPager) container).removeView(mViewList.get(position));
    }

    @Override
    public void finishUpdate(ViewGroup container) {
//        super.finishUpdate(container);
    }

    //获得当前界面数
    @Override
    public int getCount() {
        if(mViewList!=null){
            return mViewList.size();
        }
        return 0;
    }
    //初始化item位置的界面
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        ((ViewPager)container).addView(mViewList.get(position),0);
        return mViewList.get(position);
    }

    //判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }
}
