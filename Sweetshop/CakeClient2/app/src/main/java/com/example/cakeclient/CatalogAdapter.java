package com.example.cakeclient;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CatalogAdapter extends BaseAdapter {
    private Context mContext;
    private List<CatalogApi> mCatalogList;

    public CatalogAdapter(Context mContext,List<CatalogApi> mCatalogList) {
       this.mContext=mContext;
       this.mCatalogList=mCatalogList;
    }

    @Override
    public int getCount() {
        return mCatalogList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCatalogList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mCatalogList.get(i).Id;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_catalog, null);
        ImageView imageView = v.findViewById(R.id.imageCatalogObject);
        TextView textView = v.findViewById(R.id.textCatalogTitle);
        TextView textView2 = v.findViewById(R.id.textCatalogCost);


        CatalogApi catalogApi = mCatalogList.get(i);

        if(catalogApi.getBitmapSource()==null){
            imageView.setImageResource(R.drawable.disable_picture);
        }
        else{
            imageView.setImageBitmap(catalogApi.getBitmapSource());
        }
        textView.setText(catalogApi.Title);
        textView2.setText(String.valueOf(catalogApi.Cost) + " руб/кг");
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailActivity.class);
                intent.putExtra("catalog",catalogApi);
                mContext.startActivity(intent);
            }
        });


        return v;
    }
}
