package piktoclean.com.pik_to_clean;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class classification extends AppCompatActivity {
    private ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        im=findViewById(R.id.slide_image);
        Intent i=getIntent();

        byte[] bytes=i.getByteArrayExtra("pic");
        Bitmap picture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        im.setImageBitmap(picture);
    }
}
