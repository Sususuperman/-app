/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package me.iwf.photopicker;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.utils.ImageLoaderUtils;
import me.iwf.photopicker.widget.GestureImageView;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class ImagePagerActivity extends FragmentActivity implements OnClickListener {

    private static final String STATE_POSITION = "STATE_POSITION";
    private ViewPager pager;
    private int pagerPosition;
    private TextView toast;
    private List<String> imageUrls;
    private RelativeLayout titleLayout;
    private RelativeLayout goback_layout;

    public static void startShowImages(Context context, ArrayList<String> urls, int position) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra("position", position);
        intent.putStringArrayListExtra("urls", urls);
        context.startActivity(intent);
    }

    public static void startShowImages(Context context, String url) {
        ArrayList<String> list = new ArrayList<>();
        list.add(url);
        startShowImages(context, list, 0);
    }

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.__picker_activity_image_pager);
        init(savedInstanceState);

    }

    @SuppressWarnings("unchecked")
    private void init(Bundle savedInstanceState) {
        this.imageUrls = getIntent().getStringArrayListExtra("urls");
        this.pagerPosition = getIntent().getIntExtra("position", 0);

        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        this.titleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        this.goback_layout = (RelativeLayout) findViewById(R.id.goback_layout);
        this.toast = (TextView) findViewById(R.id.toast);
        this.pager = (ViewPager) findViewById(R.id.pager);

        this.pager.setAdapter(new ImagePagerAdapter(imageUrls));
        this.pager.setCurrentItem(pagerPosition);
        this.toast.setText(pagerPosition + 1 + "/" + imageUrls.size());
        this.pager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                toast.setText(arg0 + 1 + "/" + imageUrls.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        goback_layout.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, pager.getCurrentItem());
    }

    private class ImagePagerAdapter extends PagerAdapter {
        private List<String> images;
        private LayoutInflater inflater;

        ImagePagerAdapter(List<String> images) {
            this.images = images;
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public void finishUpdate(View container) {
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.__picker_item_pager_image, view, false);
            final GestureImageView imageView = (GestureImageView) imageLayout.findViewById(R.id.page_image);
            imageView.setClickable(true);
            imageView.setOnClickListener(ImagePagerActivity.this);
            String path = "";
            if (imageUrls != null && imageUrls.get(position) != null)
                path = imageUrls.get(position);

            File file = new File(path);
            if (file.exists()) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(path));
            } else {
                final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
                imageView.setVisibility(View.GONE);
                ImageLoaderUtils.display(view.getContext(), path, new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                        imageView.setImageDrawable(resource);
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        imageView.setVisibility(View.GONE);
                        spinner.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        imageView.setVisibility(View.VISIBLE);
                    }
                });
            }
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View container) {
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.goback_layout) {
            finishThis();
        } else if (v.getId() == R.id.image) {
            showOrHideTitle();
        }

    }

    private void finishThis() {
        finish();
    }

    private void showOrHideTitle() {
        if (titleLayout.getVisibility() == View.GONE)
            showTitleAnimation();
        else
            hideTitleAnimation();
    }

    private void showTitleAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (titleLayout.getVisibility() == View.GONE)
                    titleLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        titleLayout.startAnimation(alphaAnimation);
    }

    private void hideTitleAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (titleLayout.getVisibility() == View.VISIBLE)
                    titleLayout.setVisibility(View.GONE);
            }
        });
        titleLayout.startAnimation(alphaAnimation);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishThis();
    }
}
