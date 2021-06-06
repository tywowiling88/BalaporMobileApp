package umn.ac.id.balapor_kawanua.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import umn.ac.id.balapor_kawanua.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    ImageView ivClose;
    EditText etEmail;
    TextView tvErrorEmail;
    Button btnResetPassword;

    //    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    //    Firebase Authentication
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ivClose = findViewById(R.id.ivClose);
        etEmail = findViewById(R.id.etEmail);
        tvErrorEmail = findViewById(R.id.tvErrorEmail);
        btnResetPassword = findViewById(R.id.btnResetPassword);

    }

/*
Back To Login page
*/
    public void close(View view) {
        Intent closeA = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
        startActivity(closeA);
    }

    /*
    Start Input Validation
    */
    private Boolean ValidateEmail() {
        String valEmail = etEmail.getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (valEmail.isEmpty() || valEmail.length() >= 50) {
            tvErrorEmail.setVisibility(View.VISIBLE);
            tvErrorEmail.setText("Masukan Alamat Email Yang Benar");
            return false;
        }
        else if (!valEmail.matches(emailPattern)){
            tvErrorEmail.setVisibility(View.VISIBLE);
            tvErrorEmail.setText("Masukan Alamat Email Yang Benar");
            return false;
        }
        else {
            tvErrorEmail.setVisibility(View.INVISIBLE);
            return true;
        }
    }
    /*
    End Input Validation
    */

    public void resetPassword(View view) {
        if (!ValidateEmail()) {
            return;
        } else {
            mAuth = FirebaseAuth.getInstance();
            String email = etEmail.getText().toString().trim();

//            Method sendPasswordResetEmail sudah disediakan dari Firebase
            mAuth.sendPasswordResetEmail(email);
            Toast.makeText(this, "Link For Reset Password Has Been Sent To Your Email Account, Check Your Email", Toast.LENGTH_LONG).show();
    }

    }

}