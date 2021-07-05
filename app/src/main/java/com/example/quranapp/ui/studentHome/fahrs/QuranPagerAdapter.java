package com.example.quranapp.ui.studentHome.fahrs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.quranapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class QuranPagerAdapter extends PagerAdapter {

    List<String> imagesNameList;
    Context context;

    public QuranPagerAdapter(List<String> imagesNameList, Context context) {
        this.imagesNameList = imagesNameList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return imagesNameList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.view_pager_item, container, false);
        ImageView quranImageView = (ImageView) itemView.findViewById(R.id.page_img);
        quranImageView.setRotationY(180); //rotate the viewpager

        // get input stream
        InputStream ims = null;
        try {
            ims = context.getAssets().open("quran_pages/" + imagesNameList.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load image as Drawable
        Drawable d = Drawable.createFromStream(ims, null);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
            quranImageView.setImageBitmap(bitmap);

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
