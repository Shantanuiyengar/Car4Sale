package com.shantanu.car4sale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class Main extends AppCompatActivity {
    public static Car c;
    Dialog myDialog;
    TableLayout tableLayout;
    TableRow tr_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myDialog = new Dialog(this);
        tableLayout = findViewById(R.id.MainTable);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(this,"CarsDb.sqlite",null,1);
        try{
            sqLiteHelper.queryData(getResources().getString(R.string.queryTableDummyData));
            CreateDummyData(sqLiteHelper);
        }
        catch (Exception e){
//            System.out.println("Table already Exists");
        }
        CreateTable();
    }

    private void CreateDummyData(SQLiteHelper sqLiteHelper) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.img1);
        bmp = Bitmap.createScaledBitmap(bmp,400, 275, false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        sqLiteHelper.insertData("Ferrari","2019","15230600",byteArray);
        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(),
                R.drawable.img2);
        bmp1 = Bitmap.createScaledBitmap(bmp1,400, 275, false);
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        bmp1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] byteArray1 = stream1.toByteArray();
        sqLiteHelper.insertData("Corvette","2019","23500600",byteArray1);
    }

    @SuppressLint("SetTextI18n")
    public void CreateTable() {
        SQLiteHelper sqLiteHelper1 = new SQLiteHelper(ContextResolverApp.getAppContext(), "CarsDb.sqlite", null, 1);
        sqLiteHelper1.queryData(getResources().getString(R.string.queryTable));
        final Cursor cursor = sqLiteHelper1.getData("SELECT * FROM CARS");
        while (cursor.moveToNext()) {
            tr_head = new TableRow(this);
           int id = cursor.getInt(0);
            tr_head.setId(id);
            tr_head.setBackground(getResources().getDrawable(R.drawable.row_border));
            tr_head.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            //NAME,MODEL,PRICE
            TextView label_name = new TextView(this);
            label_name.setId(cursor.getInt(0));
            label_name.setText("Id:"+cursor.getInt(0)+"\nName:" + cursor.getString(1) + "\nModel:" + cursor.getString(2)
                    + "\nPrice:" + cursor.getString(3));
            label_name.setTextSize(getResources().getDimension(R.dimen.textsize));
            label_name.setTextColor(Color.BLACK);
            label_name.setPadding(5, 5, 5, 5);
            tr_head.addView(label_name);
            tableLayout.addView(tr_head, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,                    //part4
                    TableLayout.LayoutParams.WRAP_CONTENT));
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(BitmapFactory
                    .decodeByteArray(cursor.getBlob(4), 0, cursor.getBlob(4).length));
            iv.setMaxWidth((int) getResources().getDimension(R.dimen.img));
            iv.setMaxHeight((int) getResources().getDimension(R.dimen.img));
            iv.setAdjustViewBounds(true);
            iv.setPadding(100,100,0,50);
            tr_head.addView(iv);
            tr_head.setClickable(true);
            RowonClick(tr_head,id);
        }
    }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.home_menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){
            switch (item.getItemId()) {
                case R.id.createAd: {
                    startActivity(new Intent(this, CreateAd.class));
                    break;
                }
                case R.id.Logout: {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }

            }
            return super.onOptionsItemSelected(item);
        }

        public static Car getDatafromSqlite ( int id){

            SQLiteHelper sqLiteHelper1 = new SQLiteHelper(ContextResolverApp.getAppContext(), "CarsDb.sqlite", null, 1);
            Cursor cursor = sqLiteHelper1.getData("SELECT * FROM CARS where id=" + id);
            while (cursor.moveToNext()) {
                c = new Car(id, cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), BitmapFactory
                        .decodeByteArray(cursor.getBlob(4), 0, cursor.getBlob(4).length));
            }
            return c;
        }
        public void showPopUpView (final int id){
            myDialog.setContentView(R.layout.fragment_display_single_ad);
            Car c = getDatafromSqlite(id);
            TextView t1 = myDialog.findViewById(R.id.ShowCarName);
            TextView t2 = myDialog.findViewById(R.id.ShowCarModel);
            TextView t3 = myDialog.findViewById(R.id.ShowCarPrice);
            ImageView imageView = myDialog.findViewById(R.id.imageView2);
            myDialog.findViewById(R.id.SendRequest).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendMessage(v,id);
                }
            });
            t1.setText(c.name);
            t2.setText(c.model);
            t3.setText(c.price);
            imageView.setImageBitmap(c.image);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }

    public void RowonClick(TableRow tr, final int id) {
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpView(id);
            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void SendMessage (View v, final int id){
        myDialog.setContentView(R.layout.fragment_display_send_request);
        TextView t1 = myDialog.findViewById(R.id.SRid);
        final EditText e1,e2;
        e1 = myDialog.findViewById(R.id.SRgetName);
        e2 = myDialog.findViewById(R.id.SRgetDesc);
        t1.setText("ID: "+id);
        myDialog.findViewById(R.id.SRsendRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHelperRequests sqLiteHelperRequests = new SQLiteHelperRequests(ContextResolverApp.getAppContext(), "CarsDbReq.sqlite", null, 1);
                sqLiteHelperRequests.queryData(getResources().getString(R.string.queryReqTable));
                sqLiteHelperRequests.insertData(String.valueOf(e1.getText()),String.valueOf(e2.getText()),id);
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}