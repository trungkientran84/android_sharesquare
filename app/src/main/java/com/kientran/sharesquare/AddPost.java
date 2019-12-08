package com.kientran.sharesquare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddPost extends AppCompatActivity {

    TextView tv_postInformation;
    EditText et_title;
    EditText et_subtitle;
    EditText et_description;
    ImageView imageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String thumbnailPath;
    Bitmap thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        tv_postInformation = findViewById(R.id.tv_postinformation);
        et_title = findViewById(R.id.et_title);
        et_subtitle = findViewById(R.id.et_subtitle);
        et_description = findViewById(R.id.et_description);
        imageView = findViewById(R.id.img_post);
    }

    public void createPost(View view) {
        //1 grasp all the information then creat a post object and sent a Json request to server

    }


    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            thumbnail = data.getParcelableExtra("data");
            imageView.setImageBitmap(thumbnail);
        }
    }


    public void capturePicture(View view) {
        capturePhoto();
        String title = et_title.getText().toString();
        String subTitle = et_subtitle.getText().toString();
        String description = et_description.getText().toString();

    }
}
