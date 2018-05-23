package cn.lt.android.main.appdetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lt.android.GlobalConfig;
import cn.lt.android.base.BaseAppCompatActivity;
import cn.lt.android.entity.Photo;
import cn.lt.android.util.ImageloaderUtil;
import cn.lt.android.widget.photoview.HackyViewPager;
import cn.lt.android.widget.photoview.PhotoView;
import cn.lt.appstore.R;

@SuppressLint("UseSparseArrays")
public class ImageViewPagerActivity extends BaseAppCompatActivity implements OnPageChangeListener, OnLongClickListener {

    private static final String ISLOCKED_ARG = "isLocked";

    private Map<Integer, PhotoView> mImages = new HashMap<>();

    private ViewPager mViewPager;

    private List<Photo> mPhotos;

    private int mCurrentPosition = 0;

    public static final String POSITION = "position";

    public static final String PHOTOS = "photos";

    private boolean isFirst = true;

    private TextView mIndexNumberTV;

/*    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == SavePohtoDialog.SAVE_SUCCESS) {
                File file = (File) msg.obj;
                ToastUtils.showToast(ImageViewPagerActivity.this, "已经成功保存到" + (file).getPath());
                notifySystemScanPhoto(file);
            } else if (msg.what == SavePohtoDialog.SAVE_FAILED) {
                ToastUtils.showToast(ImageViewPagerActivity.this, "保存图片失败");
            }
        }
    };*/

    private void notifySystemScanPhoto(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        this.sendBroadcast(intent);
    }

    @Override
    public void setPageAlias() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_pager);
//        setStatusBar();


        mIndexNumberTV = (TextView) findViewById(R.id.tv_index_number);
        getIntentData();
        initViewPager();

    }

    private void initViewPager() {
        if (mPhotos != null && mPhotos.size() > 0) {
            mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
            mViewPager.setOnPageChangeListener(this);
            mViewPager.setAdapter(new SamplePagerAdapter());
            mViewPager.setCurrentItem(mCurrentPosition);
            setIndexText(mCurrentPosition, mPhotos.size());
        } else {
            error4PhotoEye();
        }
    }

    private void setIndexText(int postion, int total) {
        if (total > 0) {
            if (postion < 0) {
                postion = 0;
            }
            mIndexNumberTV.setText(++postion + "/" + total);
        }

    }

    private void getIntentData() {
        try {
            mCurrentPosition = getIntent().getIntExtra(POSITION, 0);
            Object o = getIntent().getSerializableExtra(PHOTOS);
            if (o != null && o instanceof ImageUrl) {
                mPhotos = ((ImageUrl) o).photos;
            } else {
                error4PhotoEye();
            }
            if (mCurrentPosition < 0) {
                mCurrentPosition = 0;
            } else if (mPhotos != null && mPhotos.size() > 0 && mCurrentPosition >= mPhotos.size()) {
                mCurrentPosition = 0;
            } else if (mPhotos != null && mPhotos.size() <= 0) {
                error4PhotoEye();
            }
        } catch (Exception e) {
            e.printStackTrace();
            error4PhotoEye();
        }
    }

    private void error4PhotoEye() {
        this.finish();
        Toast.makeText(this, "图片浏览错误！", Toast.LENGTH_SHORT).show();
    }

    private boolean isViewPagerActive() {
        return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        if (!isFirst) {
            mImages.get(arg0).resetMatrix();
        }
        isFirst = false;
        mCurrentPosition = arg0;
        if (mPhotos != null && mPhotos.size() > 0) {
            setIndexText(mCurrentPosition, mPhotos.size());
        }
    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPhotos.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            View v = LayoutInflater.from(ImageViewPagerActivity.this).inflate(R.layout.photo_eye, null);
            final PhotoView photoView = (PhotoView) v.findViewById(R.id.pv_image);
            photoView.setOnLongClickListener(ImageViewPagerActivity.this);
            final ProgressBar bar = (ProgressBar) v.findViewById(R.id.loading_progress_bar2);
            String imgUri = mPhotos.get(position).original;
            if (!imgUri.contains("file")) {
                imgUri = GlobalConfig.combineImageUrl(mPhotos.get(position).original);
            }
            ImageloaderUtil.loadImageCallBack(ImageViewPagerActivity.this, imgUri, new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    if (photoView != null) {
                        photoView.setImageDrawable(resource);
                        photoView.setHasLoadedPhoto(true);
                        if (resource.getIntrinsicWidth() > resource.getIntrinsicHeight()) {
                            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            }
                            photoView.setRotationBy(90);
                        }
                        photoView.setScaleType(ImageView.ScaleType.FIT_XY);
                        Glide.clear(photoView);
                    }
                    if (bar != null) {
                        bar.setVisibility(View.GONE);
                    }
                }
            });
            mImages.put(position, photoView);
            container.addView(v, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mImages.remove(position);
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.image_slide_in_back, R.anim.image_slide_exit_back);
    }

    public static class ImageUrl implements Serializable {

        private static final long serialVersionUID = 1L;
        List<Photo> photos;

        public ImageUrl(List<Photo> list) {
            this.photos = list;
        }
    }


    @Override
    public boolean onLongClick(View v) {

/*        try {
            String url = mPhotos.get(mCurrentPosition).original;
            Bitmap map = ImageLoader.getInstance().loadImageSync(url, 0);
            new SavePohtoDialog(this, mHandler).showDialog(map, url.hashCode() + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // 开启状态栏上色开关
       /* try {
            getTintManager().setStatusBarTintEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
