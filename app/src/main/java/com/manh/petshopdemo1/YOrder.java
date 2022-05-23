package com.manh.petshopdemo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manh.petshopdemo1.adapter.PurchaseAdapter;
import com.manh.petshopdemo1.db.DBHelper;
import com.manh.petshopdemo1.interf.IOnClickPurchase;
import com.manh.petshopdemo1.model.Order;
import com.manh.petshopdemo1.model.Purchase;

import java.util.ArrayList;
import java.util.List;

public class YOrder extends AppCompatActivity {
    private ImageView imgback;
    private RecyclerView lvOrder;
    private DBHelper helper;
    private FirebaseUser user;
    private List<Purchase> purchases;
    private PurchaseAdapter purchaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yorder);
        initUI();
        getPurchaseHistory();
        initListener();
    }

    private void initUI() {
        helper = new DBHelper(this, "petshop.db", null, 1);
        imgback = findViewById(R.id.imgback);
        lvOrder = findViewById(R.id.lvorder);
        user = FirebaseAuth.getInstance().getCurrentUser();
        purchases = new ArrayList<>();
    }

    private void getPurchaseHistory() {
        Cursor purchaseHistory = helper.getData("SELECT * FROM orderr");
        purchaseHistory.moveToFirst();
        while (!purchaseHistory.isAfterLast()) {
            if (user.getEmail().equals(purchaseHistory.getString(1))) {
                int id = purchaseHistory.getInt(0);
                long total = purchaseHistory.getLong(6);
                Cursor purchasesDetails = helper.getData("SELECT * FROM orderdetail");
                purchasesDetails.moveToFirst();
                boolean check = true;
                int quantityAllP = 0;
                Purchase purchase = new Purchase();
                while (!purchasesDetails.isAfterLast()) {
                    int idPurchases = purchasesDetails.getInt(6);

                    if (idPurchases == id && check) {
                        String namep = purchasesDetails.getString(1);
                        String imagep = purchasesDetails.getString(2);
                        String sizep = purchasesDetails.getString(4);
                        int quantityp = purchasesDetails.getInt(3);
                        long pricep = purchasesDetails.getLong(5);
                        quantityAllP += quantityp;
                        check = false;
                        purchase.setId(id);
                        purchase.setName(namep);
                        purchase.setImage(imagep);
                        purchase.setSize(sizep);
                        purchase.setPrice(pricep);
                        purchase.setQuantity(quantityp);
                        purchase.setTotal(total);
                    } else if (idPurchases == id) {
                        quantityAllP += purchasesDetails.getInt(3);
                    }
                    purchasesDetails.moveToNext();
                }
                purchase.setQuantityAllItem(quantityAllP);
                purchases.add(purchase);
            }
            purchaseHistory.moveToNext();
        }
    }

    private void initListener() {
        imgback.setOnClickListener(view -> finish());
        List<Purchase> purchaseList=new ArrayList<>();
        for(int i=purchases.size()-1;i>=0;i--){
            purchaseList.add(purchases.get(i));
        }
        purchaseAdapter = new PurchaseAdapter(purchaseList, purchase -> {
            Intent intent=new Intent(YOrder.this,OrderDetails.class);
            intent.putExtra("idOrder",purchase.getId());
            startActivity(intent);
            return null;
        });
        lvOrder.setAdapter(purchaseAdapter);
        lvOrder.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }


}