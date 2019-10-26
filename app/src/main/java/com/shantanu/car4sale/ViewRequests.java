package com.shantanu.car4sale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ViewRequests extends AppCompatActivity {

    TableLayout tableLayout;
    TableRow tr_head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);
        tableLayout = findViewById(R.id.ReqTable);
        CreateReqTable();
    }
    public void CreateReqTable() {
        SQLiteHelperRequests sqLiteHelper1 = new SQLiteHelperRequests(ContextResolverApp.getAppContext(), "CarsDbReq.sqlite", null, 1);
        sqLiteHelper1.queryData(getResources().getString(R.string.queryReqTable));
        final Cursor cursor = sqLiteHelper1.getData("SELECT * FROM CARREQUESTS");
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
            label_name.setText("Name:" + cursor.getString(1) + "\nDESC:"
                    + cursor.getString(2)+"\nRequest for: "+cursor.getInt(3));
            label_name.setTextSize(getResources().getDimension(R.dimen.textsize));
            label_name.setTextColor(Color.BLACK);
            label_name.setPadding(5, 5, 5, 5);
            tr_head.addView(label_name);
            tableLayout.addView(tr_head, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_req_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        switch (item.getItemId()) {
            case R.id.Logout: {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

}
