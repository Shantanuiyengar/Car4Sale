package com.shantanu.car4sale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class CreateAd extends AppCompatActivity implements View.OnClickListener {
    EditText e1,e2,e3;
    Bitmap bitmap;
    public static SQLiteHelper sqLiteHelper;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);
        sqLiteHelper = new SQLiteHelper(this,"CarsDb.sqlite",null,1);
        sqLiteHelper.queryData(getResources().getString(R.string.queryTable));
        findViewById(R.id.SelectImage).setOnClickListener(this);
        findViewById(R.id.AddCar).setOnClickListener(this);
        findViewById(R.id.ClickPhoto).setOnClickListener(this);
        e1 = findViewById(R.id.CarName);
        e2 = findViewById(R.id.CarModel);
        e3 = findViewById(R.id.CarPrice);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SelectImage:{
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 111);
                break;
            }
            case R.id.ClickPhoto:{
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            }
            case R.id.AddCar:{
                bitmap = Bitmap.createScaledBitmap(bitmap,400, 275, false);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                sqLiteHelper.insertData(e1.getText().toString(),e2.getText().toString(),e3.getText().toString(),byteArray);
                startActivity(new Intent(this,Main.class));
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        }
    }

}
