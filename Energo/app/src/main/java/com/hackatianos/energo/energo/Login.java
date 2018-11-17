package com.hackatianos.energo.energo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    @Override
    public void onStart(){
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra("email",currentUser.getEmail().replaceAll("@","").replaceAll("\\.",""));
            startActivity(intent);
        }
    }

    public void login(View view){
        TextView txtemail = findViewById(R.id.txtemail);
        String email = txtemail.getText().toString();
        TextView txtpassword = findViewById(R.id.txtpassword);
        String password = txtpassword.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        if(password.isEmpty() || email.isEmpty()){
            Toast.makeText(Login.this, "No se ha introducido alguno de los datos.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DEBUG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Bienvenid@",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            String preuser = user.getEmail().replaceAll("@","");
                            intent.putExtra("email",user.getEmail().replaceAll("@","").replaceAll("\\.",""));
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("DEBUG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void register(View view){
        Intent intent = new Intent(getBaseContext(), Register.class);
        startActivity(intent);
    }

}
