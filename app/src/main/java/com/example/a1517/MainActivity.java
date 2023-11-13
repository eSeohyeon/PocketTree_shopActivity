package com.example.a1517;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  SelectListener, SelectListener2, BgmListener{

    private RecyclerView recycle_tree;
    private RecyclerView recycle_bgm;
    private ImageView btn_back;
    ArrayList<ProductBgm> itemList=new ArrayList<>();
    ArrayList<ProductTree> itemList2=new ArrayList<>();

    private TextView txt_money;
    private TextView txt_currentBgm;

    private int balance=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycle_tree=(RecyclerView) findViewById(R.id.recycle_tree);
        recycle_bgm=(RecyclerView) findViewById(R.id.recycle_bgm);
        txt_currentBgm=(TextView)findViewById(R.id.txt_currentBgm);
        btn_back=findViewById(R.id.btn_back);
        txt_money = findViewById(R.id.txt_money);
        txt_money.setText(" "+balance);

        // 리사이클러뷰 세팅
        LinearLayoutManager treeManager=new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recycle_tree.setLayoutManager(treeManager);
        LinearLayoutManager bgmManager=new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recycle_bgm.setLayoutManager(bgmManager);
        BgmAdapter bgmAdapter=new BgmAdapter(this, itemList, this, this::onPButtonClicked);
        TreeAdapter treeAdapter=new TreeAdapter(this, itemList2, this::onItemClicked);

        for(int i=0; i<4; i++){ // 테스트용 더미데이터 (리사이클러뷰에 들어가는 데이터 값 생성 ; 가격 등등)
            treeAdapter.addItem(new ProductTree(R.drawable.tree_1, i*100));
            bgmAdapter.addItem(new ProductBgm("Title "+i, i*100));
        }

        recycle_tree.setAdapter(treeAdapter);
        recycle_bgm.setAdapter(bgmAdapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void updateBalanceText(int balance){
        txt_money.setText(" "+balance);
    }

    @Override
    public void onItemClicked(ProductBgm productBgm, LinearLayout layout, TextView txtPrice) { // 브금 아이템 클릭 시
        Animation btnScale=AnimationUtils.loadAnimation(this, R.anim.anim_btn_scale);

        if(productBgm.getIsPurchased()==false){ // 보유 중이지 않은 아이템인 경우
            layout.startAnimation(btnScale);
            showPurchaseConfirmationDialog(productBgm, txtPrice, layout);
        }
        else{ // 보유 중인 아이템인 경우
            layout.startAnimation(btnScale);
            showChangeBgmDialog(productBgm);
        }
    }

    public void showPurchaseConfirmationDialog(ProductBgm productBgm, TextView txtPrice, LinearLayout layout){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        Animation noMoney=AnimationUtils.loadAnimation(this, R.anim.anim_no_money);


        builder.setTitle("Would you like to buy?");
        builder.setMessage(" Title: "+ productBgm.getTitle());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // yes
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(balance>=productBgm.getPrice()){
                    productBgm.setIsPurchased(Boolean.TRUE);
                    balance=balance-productBgm.getPrice();
                    updateBalanceText(balance);
                    txtPrice.setText("In Stock");
                    layout.setBackgroundResource(R.drawable.area_shop_bgm_selected);
                }
                else{
                    Toast.makeText(MainActivity.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                    layout.startAnimation(noMoney);
                }

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() { // no
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Canceled to purchase", Toast.LENGTH_SHORT).show();
                // 구매를 취소하거나 다른 작업을 수행할 수 있습니다.
            }
        });
        builder.show();
    }

    public void showChangeBgmDialog(ProductBgm productBgm){ // 이미 보유 중인 브금 아이템 클릭 시, 현재 브금 변경 가능
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Would you like to set the BGM?");
        builder.setMessage(" Title: "+ productBgm.getTitle());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 브금 바ㅜㄲ기
                txt_currentBgm.setText(" : "+productBgm.getTitle()); // 텍스트로 현재 설정된 브금 표시.

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Canceled to change", Toast.LENGTH_SHORT).show();
                // 구매를 취소하거나 다른 작업을 수행할 수 있습니다.
            }
        });
        builder.show();
    }



    @Override
    public void onItemClicked(ProductTree productTree, LinearLayout layout, TextView txtPrice) { // 나무 아이템 클릭 시
        Animation btnScale=AnimationUtils.loadAnimation(this, R.anim.anim_btn_scale);

        if(productTree.getIsPurchased()==false){
            layout.startAnimation(btnScale);
            showPurchaseConfirmationDialog2(productTree, txtPrice, layout);
        }
        else{
            layout.startAnimation(btnScale);
        }

    }
    public void showPurchaseConfirmationDialog2(ProductTree productTree, TextView txtPrice, LinearLayout layout){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        Animation noMoney=AnimationUtils.loadAnimation(this, R.anim.anim_no_money);

        builder.setTitle("Would you like to buy?");
        builder.setMessage("Tree");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(balance>=productTree.getPrice()){
                    productTree.setIsPurchased(Boolean.TRUE);
                    balance=balance-productTree.getPrice();
                    updateBalanceText(balance);
                    txtPrice.setText("In Stock");
                    layout.setBackgroundResource(R.drawable.area_shop_tree_selected);
                }
                else{
                    Toast.makeText(MainActivity.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                    layout.startAnimation(noMoney);

                }

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Canceled to purchase", Toast.LENGTH_SHORT).show();
                // 구매를 취소하거나 다른 작업을 수행할 수 있습니다.
            }
        });
        builder.show();
    }

    @Override
    public void onPButtonClicked(ProductBgm productBgm, Boolean isPlay) {
        if(isPlay==true){
            Toast.makeText(MainActivity.this, "Play", Toast.LENGTH_SHORT).show(); // 테스트용
        }
        else{
            Toast.makeText(MainActivity.this, "Pause", Toast.LENGTH_SHORT).show();
        }
    }
}

