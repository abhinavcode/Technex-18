package com.iitbhu.technex18.adapter;

/**
 * Created by abhinav on 10/10/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.iitbhu.technex18.FullScreenViewActivity;
import com.iitbhu.technex18.R;
import com.iitbhu.technex18.utils1.galleryImage;

import java.util.ArrayList;
import java.util.Random;

public class GridViewImageAdapter extends RecyclerView.Adapter<GridViewImageAdapter.ViewHolder> {

    private Activity _activity;
    private ArrayList<String> _filePaths = new ArrayList<String>();
    private int imageWidth;
    int[] img={};
    String[] desc={};

    public GridViewImageAdapter(Activity activity,
                                int imageWidth) {
        this._activity = activity;
        this.img = galleryImage.img;
        this.desc=galleryImage.desc;
        this.imageWidth = imageWidth;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View galView = inflater.inflate(R.layout.layout_gallery_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(galView);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Set item views based on your views and data model
        TextView textView = holder.desc;
        textView.setText(desc[position]);
        ImageView imageView=holder.pic;
        imageView.setBackgroundResource(img[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        BitmapDrawable bd=(BitmapDrawable) _activity.getResources().getDrawable(img[position]);
        FrameLayout frameLayout=holder.frameLayout;
        Random random=new Random();
        frameLayout.requestLayout();
        int w=frameLayout.getLayoutParams().width;
        int width=bd.getBitmap().getWidth();
        int height=bd.getBitmap().getHeight();

        System.out.println("width"+w+"\n"+"scren"+imageWidth+"imagewidth"+ bd.getBitmap().getWidth() +"imageheight"+bd.getBitmap().getHeight());
        frameLayout.getLayoutParams().height=(imageWidth*height)/width;
//        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(imageWidth+random.nextInt()%imageWidth,imageWidth+random.nextInt()%imageWidth));
//        frameLayout.setLayoutParams(new GridView.LayoutParams(imageWidth,
//                imageWidth));

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater mInflater = (LayoutInflater) _activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//        if (convertView == null) {
//            convertView = mInflater.inflate(R.layout.layout_gallery_item, null);
//
//            TextView desc = (TextView) convertView.findViewById(R.id.desc);
//            ImageView imageView = (ImageView) convertView.findViewById(R.id.pic);
//            convertView.setTag(holder);
//             } else {
//            imageView = (ImageView) convertView;
//            frameLayout=(FrameLayout) convertView;
//        }
//
//        // get screen dimensions
//        Bitmap image = decodeFile(_filePaths.get(position), imageWidth,
//                imageWidth);
//
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
//                imageWidth));
//        imageView.setImageBitmap(image);
//
//        // image view click listener
//        imageView.setOnClickListener(new OnImageClickListener(position));
//
//        return imageView;
//    }
//
//    class OnImageClickListener implements OnClickListener {
//
//        int _postion;
//
//        // constructor
//        public OnImageClickListener(int position) {
//            this._postion = position;
//        }
//
//        @Override
//        public void onClick(View v) {
//            // on selecting grid view image
//            // launch full screen activity
//            Intent i = new Intent(_activity, FullScreenViewActivity.class);
//            i.putExtra("position", _postion);
//            _activity.startActivity(i);
//        }
//
//    }
//
//    /*
//     * Resizing image size
//     */
//    public static Bitmap decodeFile(String filePath, int WIDTH, int HIGHT) {
//        try {
//
//            File f = new File(filePath);
//
//            BitmapFactory.Options o = new BitmapFactory.Options();
//            o.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
//
//            final int REQUIRED_WIDTH = WIDTH;
//            final int REQUIRED_HIGHT = HIGHT;
//            int scale = 1;
//            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
//                    && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
//                scale *= 2;
//
//            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inSampleSize = scale;
//            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView desc;
        public ImageView pic;
        public FrameLayout frameLayout;
        public ViewHolder(final View itemView){
            super(itemView);
            frameLayout=(FrameLayout)itemView.findViewById(R.id.frame);
            desc=(TextView)itemView.findViewById(R.id.desc);
            pic=(ImageView)itemView.findViewById(R.id.pic);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Intent i = new Intent(itemView.getContext(), FullScreenViewActivity.class);
                    i.putExtra("position", position);
                    itemView.getContext().startActivity(i);
                }
            });
        }


    }
}