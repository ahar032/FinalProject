package com.mahar.busxhacktiv.auth;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mahar.busxhacktiv.R;

public class SignUpActivity extends AppCompatActivity {
    Button btnsignup;
    ProgressBar pb;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;

    ActivityResultLauncher<Intent> activityResultLauncherForGoogleSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        registerActivityForGoogleSignIn();

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        btnsignup=findViewById(R.id.btnSignUp);
        pb=findViewById(R.id.pb);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });
    }
    public void signInGoogle(){
        GoogleSignInOptions gso=new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,gso);
        signin();
    }
    public void signin(){
        Intent signIn=googleSignInClient.getSignInIntent();
        activityResultLauncherForGoogleSignIn.launch(signIn);
    }
    public void registerActivityForGoogleSignIn(){
        activityResultLauncherForGoogleSignIn=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
              int resultCode=result.getResultCode();
              Intent data=result.getData();

              if(resultCode==RESULT_OK && data!=null){
                  pb.setVisibility(View.VISIBLE);
                  Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
                  firebaseSigninWithGoogle(task);

              }
            }
        });
    }
    private void firebaseSigninWithGoogle(Task<GoogleSignInAccount> task){
        try {
            GoogleSignInAccount account=task.getResult(ApiException.class);
            Intent i=new Intent(SignUpActivity.this,AddPhoneActivity.class);
            startActivity(i);
            finish();
            firebaseGoogleAccount(account);
        }catch (ApiException e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    }
    private  void firebaseGoogleAccount(GoogleSignInAccount account){
        AuthCredential authCredential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            pb.setVisibility(View.VISIBLE);
                        }else{
                            pb.setVisibility(View.GONE);
                        }
                    }
                });
    }
}