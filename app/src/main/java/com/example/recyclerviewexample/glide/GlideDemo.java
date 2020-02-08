package com.example.recyclerviewexample.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.activity.GlideActivity;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

/**
 * https://www.jianshu.com/p/a01f1c41f42f
 */
public class GlideDemo {

    private final ImageView imageView;
    private final Context context;

    public GlideDemo(ImageView imageView, Context context) {
        this.imageView = imageView;
        this.context = context;
    }

    public void loadNetworkStillPicture() {
        Glide.with(context)
                .load("http://a3.att.hudong.com/68/61/300000839764127060614318218_950.jpg")
                .placeholder(R.drawable.cat)  //显示占位图片
                .error(R.drawable.bird)  //显示错误图片
                .dontAnimate()  //关闭动画
                .override(80,60)
//        centerCrop：使原始的图片的宽高按同等比例放大到override所指定的大小，并裁剪到多余的部分，这时最终的图片资源的大小为(width, height)
//        fitCenter：使得原始图片的宽高放大到小于等于override所指定的宽高，因此，我们最终得到图片的大小有可能不为(width, height)，也就是说不会撑满整个ImageView。
//                .centerCrop()
//                .fitCenter()
                .skipMemoryCache(true)
                .into(imageView);
    }

    public void loadByteArray() {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.glide);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        bitmap.recycle();

        byte[] bytes = byteArrayOutputStream.toByteArray();
        try {
            bytes.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Glide.with(context)
                .load(bytes)
                .into(imageView);
    }

    public void cachingStrategy() {
        Glide.with(context)
                .load("http://a3.att.hudong.com/68/61/300000839764127060614318218_950.jpg")
//        默认情况下，Glide会将图片资源缓存到内存当中。而如果使用了skipMemoryCache(true)，Glide不会将这张图片缓存到内存当中
//        有一点需要注意：如果之前对某个指向url的图片使用了内存缓存，后面又用skipMemoryCache(true)声明想让同一个url不缓存到内存中，那么是不会生效的。
                .skipMemoryCache(true)

//        Glide默认情况下既会缓存原始的图片，也会缓存解析后的图片
//        DiskCacheStrategy.NONE：不缓存
//        DiskCacheStrategy.SOURCE：只缓存原始大小的图片，也就是1000 * 1000。
//        DiskCacheStrategy.RESULT：只缓存解析之后的图片，也就是上面500 * 500的图片，也就是说假如我们有两个不同大小的ImageView，用他们加载同一个url的图片，那么最终磁盘当中会有两份不同大小的图片资源。
//        DiskCacheStrategy.ALL：缓存所有版本的图片。
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 我们介绍了placeHolder，它可以指定一个本地资源，用来在网络资源加载完成之前进行展示，而thumbnails则可以认为是一个动态的placeHolder
     * ，与placeHolder不同，我们可以给它指定一个网络图片链接。如果这个缩略图请求在load请求之前返回那么
     * ，那么会先展示这个图片，等到load请求返回之后，这个图片就会消失。假如缩略图的请求在load请求之后返回，那么请求结果会被丢弃掉。
     */
    public void thumbnail() {
        RequestBuilder<Drawable> requestBuilder = Glide.with(context)
                .load("http://img4.imgtn.bdimg.com/it/u=3209370120,2008812818&fm=26&gp=0.jpg");

        Glide.with(context)
                .load("http://a3.att.hudong.com/68/61/3318218_950.jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .thumbnail(0.1f)
                .thumbnail(requestBuilder)
                .into(imageView);
    }

    class MySimpleTarget extends SimpleTarget<Drawable> {

        public MySimpleTarget(int width, int height) {
            super(width, height);
        }

        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            imageView.setImageDrawable(resource);
        }
    }

    public void customTarget() {
        MySimpleTarget mySimpleTarget = new MySimpleTarget(50, 50);
        Glide.with(context)
                .load("http://a3.att.hudong.com/68/61/300000839764127060614318218_950.jpg")
                .into(mySimpleTarget);
    }

    class MyViewTarget extends ViewTarget<ImageView, Drawable> {
        public MyViewTarget(@NonNull ImageView view) {
            super(view);
        }

        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            view.setImageDrawable(resource);
        }
    }

    class MyCustomeViewTarget extends CustomViewTarget<ImageView, Drawable> {

        public MyCustomeViewTarget(@NonNull ImageView view) {
            super(view);
        }

        @Override
        protected void onResourceCleared(@Nullable Drawable placeholder) {

        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {

        }

        @Override
        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            view.setImageDrawable(resource);
        }
    }

    public void viewTarget() {
        Glide.with(context)
                .load("http://a3.att.hudong.com/68/61/300000839764127060614318218_950.jpg")
                .into(new MyCustomeViewTarget(imageView));
    }


    class MyBitmapTransformation extends BitmapTransformation {
        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            return null;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }

    /**
     *求图片资源成功之后，希望先对它进行一些处理，例如缩放、旋转、蒙灰等，然后再把处理后的图片展示在控件上，
     * 这时候就可以用上Glide提供的transform()方法，简单来说，transform()的作用就是改变原始资源在客户端上最终的展现结果
     */
    public void customTransform() {
        Glide.with(context)
                .load("http://a3.att.hudong.com/68/61/300000839764127060614318218_950.jpg")
                .transform()
                .into(imageView);
    }
}
