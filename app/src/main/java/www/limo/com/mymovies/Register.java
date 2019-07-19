package www.limo.com.mymovies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    EditText createEmailField,createPasswordField,Confirmpassword;
    TextView warningText;
    Button registerBtn;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createEmailField=findViewById(R.id.createEmail);
        createPasswordField=findViewById(R.id.createPassword);
        Confirmpassword=findViewById(R.id.confirmpassword);
        warningText=findViewById(R.id.warningText);
        registerBtn=findViewById(R.id.RegisterBtn);
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningText.setText("");
                if (createPasswordField.getText().toString().equals("")){
                    warningText.setText("Enter email Address");
                }else if(createPasswordField.getText().toString().equals("")){

                    warningText.setText("Enter  Password");
                }else if(createPasswordField.getText().toString().length()<5){
                    warningText.setText("Enter a password with atleast 6 characters");
                }else if(Confirmpassword.getText().toString().equals("")){
                    warningText.setText("Confirm Password");
                }else if(!Confirmpassword.getText().toString().equals(createPasswordField.getText().toString())){
                    warningText.setText("Passwords do not Match");
                }else{
                    progressDialog.show();
                  if(currentUser!=null){
                      startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                  }
                  createUser(createEmailField.getText().toString(),createPasswordField.getText().toString());
                    progressDialog.dismiss();
                }

            }
        });


    }

    public void createUser(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {

                            warningText.setText("Creating User Failed");
                        }

                        // ...
                    }
                });

    }
}
