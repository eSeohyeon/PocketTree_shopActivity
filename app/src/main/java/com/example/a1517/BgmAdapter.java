package com.example.a1517;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BgmAdapter extends RecyclerView.Adapter<BgmAdapter.ViewHolder>{
    private ArrayList<ProductBgm> itemList;
    private static final String TAG= "BgmAdapter";
    private Context context;
    private SelectListener listener;
    private BgmListener bgmListener;

    public BgmAdapter(Context context, ArrayList<ProductBgm> itemList, SelectListener listener, BgmListener bgmListener){
        this.context=context;
        this.itemList=itemList;
        this.listener=listener;
        this.bgmListener=bgmListener;
    }


    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viweType){
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.item_bgm, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, final int position){ // << 빨간 줄 뜨는데, 실행은 됨.
        ProductBgm item=itemList.get(position);
        holder.setItem(item);

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){ // 전체 레이아웃 클릭 >> 구매 창
           public void onClick(View v){
               listener.onItemClicked(itemList.get(position), holder.parentLayout, holder.txt_price);
           }
        });


        holder.btn_play.setOnClickListener(new View.OnClickListener(){ // 음악 재생 버튼 클릭
            public void onClick(View v){
                bgmListener.onPButtonClicked(itemList.get(position), true);
            }
        });

        holder.btn_pause.setOnClickListener(new View.OnClickListener(){ // 중지 버튼 클릭
            public void onClick(View v){
                bgmListener.onPButtonClicked(itemList.get(position), false);
            }
        });
    }
    public int getItemCount(){
        return itemList.size();
    }
    public void addItem(ProductBgm item){
        itemList.add(item);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_title, txt_price;
        ImageView btn_play, btn_pause;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            txt_title=itemView.findViewById(R.id.txt_title);
            txt_price=itemView.findViewById(R.id.txt_price);
            btn_play=itemView.findViewById(R.id.btn_play);
            btn_pause=itemView.findViewById(R.id.btn_pause);
            parentLayout=itemView.findViewById(R.id.layout_bgm);
        }
        public void setItem(ProductBgm item){
            txt_title.setText(item.getTitle());
            txt_price.setText("$ "+item.getPrice());
            btn_play.setImageResource(R.drawable.btn_play);
            btn_pause.setImageResource(R.drawable.btn_pause);
        }
    }
}
