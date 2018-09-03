package piktoclean.com.pik_to_clean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;

public class LoginActivity extends AppCompatActivity {
    private static EditText num;
    private static EditText otp;
    private static Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        num= findViewById(R.id.number);
        otp= findViewById(R.id.OTP);
        b1= findViewById(R.id.log);

    }
}
