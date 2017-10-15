package com.iitbhu.technex18.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iitbhu.technex18.R;
import com.iitbhu.technex18.utils1.galleryImage;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

import static com.iitbhu.technex18.utils1.galleryImage.img;

/**
 * Created by abhinav on 10/10/17.
 */
public class FullScreenImageAdapter extends PagerAdapter {

    private Activity _activity;
    private int[] _imagePaths;
    private LayoutInflater inflater;
    private String[] _desc;
    private int windowWidth;
    // constructor
    public FullScreenImageAdapter(Activity activity,int width) {
        this._activity = activity;
        this._imagePaths = img;
        this._desc= galleryImage.desc;
        this.windowWidth=width;
    }

    @Override
    public int getCount() {
        return this._imagePaths.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
//        TouchImageView imgDisplay = null;
        final ImageViewTouch imgDisplay;
        Button btnClose;
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

//        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);

        TextView desc=(TextView)viewLayout.findViewById(R.id.description);
        imgDisplay = (ImageViewTouch) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
        desc.setText(galleryImage.desc[position]);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                final Bitmap bitmap = BitmapFactory.decodeResource(container.getContext().getResources(),_imagePaths[position]);

                imgDisplay.setImageBitmap(bitmap);



        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}