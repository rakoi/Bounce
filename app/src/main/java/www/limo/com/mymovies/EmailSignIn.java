package www.limo.com.mymovies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailSignIn extends AppCompatActivity {

    EditText emailField;
    EditText passwordField;
    Button loginBtn;
    TextView info;
    private FirebaseAuth mAuth;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sign_in);
        emailField=findViewById(R.id.emailField);
        passwordField=findViewById(R.id.passwordfield);
        loginBtn=findViewById(R.id.emailSigninBtn);
        info=findViewById(R.id.loginwarning);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(emailField.getText().toString().equals("")){
                   info.setText("Enter Email ");
                    return;
               }else if(passwordField.getText().toString().equals("")){
                   info.setText("Enter Password");
                   return;
               }else {
                   info.setText("");
                   signIn(emailField.getText().toString(), passwordField.getText().toString());
               }

            }
        });
    }
    public void signIn(String useremail,String userpassword){
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(useremail, userpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                                progressDialog.dismiss();
                             startActivity(new Intent(EmailSignIn.this,MainActivity.class));


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            info.setText("Wrong Credentials");

                        }

                        // ...
                    }
                });

    }
}
