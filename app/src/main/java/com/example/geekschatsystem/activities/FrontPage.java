package com.example.geekschatsystem.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.geekschatsystem.MainUserPage2;
import com.example.geekschatsystem.R;
import com.example.geekschatsystem.databinding.ActivityFrontPageBinding;
import com.example.geekschatsystem.utilities.Constants;
import com.example.geekschatsystem.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrontPage extends AppCompatActivity {
  //declaring
    private ActivityFrontPageBinding binding;
    private PreferenceManager preferenceManager;


    private TextView loginText, registerText;

    private MeowBottomNavigation meowBottomNavigation;

    private LinearLayout linearLayout1, linearLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        //getting info from xml
        binding = ActivityFrontPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initializing the session
        preferenceManager = new PreferenceManager(getApplicationContext());
       setListerners();

       //checking if the users is registered,if registered returns boolean
        if (preferenceManager.getBoolean(Constants.KEY_IS_REGISTER)) {
            startActivity(new Intent(getApplicationContext(), MainUserPage2.class));
            finish();
        }

        //getting layouts ,logintext to navigates
        linearLayout1 = findViewById(R.id.FrameLayout3);
        linearLayout2 = findViewById(R.id.FrameLayout4);
        loginText = findViewById(R.id.LoginText);

        //set layout to be visible
        linearLayout1.setVisibility(View.VISIBLE);

        // Set OnClickListener for the loginText TextView
        loginText.setOnClickListener(v -> {
            if (linearLayout1 != null) {
                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
            }
        });

        registerText = findViewById(R.id.RegisterText);

        // Set OnClickListener for the registerText TextView
        registerText.setOnClickListener(v -> {
            if (linearLayout2 != null) {
                linearLayout2.setVisibility(View.VISIBLE);
                linearLayout1.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void setListerners() {

        //when a user click a button , should perfom the action

        binding.LoginButton.setOnClickListener(v -> {
            if(isValidLogInDetails()) //validates
                 {
                LOGIN(); //true
            }
        });
        binding.Lbutton.setOnClickListener(v -> {
            if (isValidRegisterDetails()) {
                REGISTER();
            }
        });
    }

    private void LOGIN(){
     loading(true);
     FirebaseFirestore database = FirebaseFirestore.getInstance(); // getting instance of the database
     database.collection(Constants.KEY_COLLECTION_USERS)
             .whereEqualTo(Constants.KEY_EMAIL,binding.Username.getText().toString())
             .whereEqualTo(Constants.KEY_CONFIRM_PASSWORD,binding.Password.getText().toString())
             .get()
             .addOnCompleteListener(task -> {
                 if(task.isSuccessful() && task.getResult() !=null
                 && task.getResult().getDocuments().size()>0){
                     DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);//represent user documents
                     preferenceManager.putBoolean(Constants.KEY_IS_REGISTER,true);
                     preferenceManager.putString(Constants.KEY_REGISTER,documentSnapshot.getId());
                     preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
                     Intent intent = new Intent(getApplicationContext(), MainUserPage2.class);//telling which page to open
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(intent);//start
                 }
                 else{
                     loading(false);
                     showToast("Unable to log in");
                 }
             });
    }

    private void REGISTER() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, binding.RName.getText().toString());
        user.put(Constants.KEY_SURNAME, binding.RSurname.getText().toString());
        user.put(Constants.KEY_EMAIL, binding.REmail.getText().toString());
        user.put(Constants.KEY_PASSWORD, binding.RPassword.getText().toString());
        user.put(Constants.KEY_CONFIRM_PASSWORD, binding.RConfirmPassword.getText().toString());
        database.collection(Constants.KEY_COLLECTION_USERS).add(user)
                .addOnSuccessListener(documentReference -> {
                    loading(false);
                    preferenceManager.putBoolean(Constants.KEY_IS_REGISTER, true);
                    preferenceManager.putString(Constants.KEY_REGISTER, documentReference.getId());
                    preferenceManager.putString(Constants.KEY_NAME, binding.RName.getText().toString());
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
                    linearLayout2.setVisibility(View.INVISIBLE);
                    linearLayout1.setVisibility(View.VISIBLE);


                }).addOnFailureListener(exeption -> {
                    loading(false);
                    showToast(exeption.getMessage());

                });
    }

    private void loading(boolean isLoading) {
        if (isLoading) {
            binding.LoginButton.setVisibility(View.INVISIBLE);
            binding.progressBar4.setVisibility(View.VISIBLE);
            binding.Lbutton.setVisibility(View.INVISIBLE);
            binding.progressBar3.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar4.setVisibility(View.INVISIBLE);
            binding.LoginButton.setVisibility(View.VISIBLE);
            binding.progressBar3.setVisibility(View.INVISIBLE);
            binding.Lbutton.setVisibility(View.VISIBLE);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


        private Boolean isValidLogInDetails() {
            if (binding.Username.getText().toString().trim().isEmpty()) {
                showToast("Enter Email");
                return false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.Username.getText().toString()).matches()) {
                showToast("Enter valid Email");
                return false;
            } else if (binding.Password.getText().toString().trim().isEmpty()) {
                showToast("Enter password");
                return false;
            } else {
                return true;
            }
        }

        private Boolean isValidRegisterDetails() {
            if (binding.RName.getText().toString().trim().isEmpty()) {
                showToast("Enter name");
                return false;
            } else if (binding.RSurname.getText().toString().trim().isEmpty()) {
                showToast("Enter surname");
                return false;
            } else if (binding.REmail.getText().toString().trim().isEmpty()) {
                showToast("Enter email");
                return false;
//        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.REmail.getText().toString()).matches()){
//            showToast("Enter valid Email");
//            return false;

        }else if(binding.RPassword.getText().toString().trim().isEmpty()){
            showToast("Enter password");
            return  false;

        }else if(binding.RConfirmPassword.getText().toString().trim().isEmpty()){
            showToast("confirm your password");
            return  false;
        }else if(!binding.RPassword.getText().toString().equals(binding.RConfirmPassword.getText().toString())){
            showToast("password and confirm password must be the same");
            return  false;

            }else if(!validateEmail(binding.REmail.getText().toString())){
                showToast("Email valid email");
                return  false;
            }else{
            return  true;
        }
        }
    public boolean validateEmail(String email) {
        // Define a regular expression pattern for the email format you want
        String emailPattern = "^[A-Za-z0-9+_.-]+@geeks4learning\\.com$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(emailPattern);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(email);

        // Check if the email matches the pattern
        return matcher.matches();
    }


    }

