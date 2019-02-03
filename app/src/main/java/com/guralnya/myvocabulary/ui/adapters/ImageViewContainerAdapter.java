package com.guralnya.myvocabulary.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.guralnya.myvocabulary.R;
import com.guralnya.myvocabulary.model.dto.cover_words_objects.Photo;

import java.util.List;

import javax.annotation.Nonnull;

public class ImageViewContainerAdapter extends PagerAdapter {

    private List<Photo> mImageList;
    private Context mContext;

    /**
     * Адаптер реализации карусели картинок
     *
     * @param context
     * @param imageList - список картинок (String)
     */
    public ImageViewContainerAdapter(Context context, List<Photo> imageList) {
        this.mContext = context;
        this.mImageList = imageList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.container_image_view, container, false);

        final ImageView imageView = itemView.findViewById(R.id.containerImageView);
        String imageURL = mImageList.get(position).getUrlM();

        final ProgressBar progressBar = itemView.findViewById(R.id.progressBar);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.placeholder(R.drawable.ic_nothing_show)
                .error(R.drawable.ic_nothing_show)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide
                .with(mContext)
                .load(imageURL)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
        container.addView(itemView, 0);

        return itemView;
    }

    public List<Photo> getImageList() {
        return mImageList;
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }

    @Override
    public void destroyItem(@Nonnull ViewGroup collection, int position, @Nonnull Object view) {
        collection.removeView((View) view);
    }
}
