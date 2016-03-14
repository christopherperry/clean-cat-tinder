package com.github.cleancattinder.catswiping;

import com.github.cleancattinder.R;
import com.github.cleancattinder.catswiping.view.CatCardInfo;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class CatSwipeAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final Picasso picasso;
    private final List<CatCardInfo> catCardInfos = new ArrayList<>();

    CatSwipeAdapter(LayoutInflater inflater, Picasso picasso) {
        this.inflater = inflater;
        this.picasso = picasso;
    }

    public void addCatCards(List<CatCardInfo> catCardInfos) {
        this.catCardInfos.addAll(catCardInfos);
        if (catCardInfos.size() > 0) {
            notifyDataSetChanged();
        }
    }

    public void removeFirstCatCard() {
        if (getCount() > 0) {
            this.catCardInfos.remove(0);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return catCardInfos.size();
    }

    @Override
    public CatCardInfo getItem(int position) {
        return catCardInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.view_cat_card, parent, false);
        }

        CatCardInfo catCardInfo = getItem(position);

        int size = (int) dipsToPixels(view.getContext(), 290f);
        picasso.load(catCardInfo.imageUrl)
               .resize(size, size)
               .centerCrop()
               .noFade()
               .into((ImageView) view.findViewById(R.id.image));

        ((TextView) view.findViewById(R.id.snippet)).setText(catCardInfo.title);

        return view;
    }

    float dipsToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
