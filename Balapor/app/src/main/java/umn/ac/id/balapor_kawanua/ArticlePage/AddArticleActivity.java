package umn.ac.id.balapor_kawanua.ArticlePage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import umn.ac.id.balapor_kawanua.HomePage.HomePage;
import umn.ac.id.balapor_kawanua.MyProfile.AllArtikelActivity;
import umn.ac.id.balapor_kawanua.R;
import umn.ac.id.balapor_kawanua.model.Article;

public class AddArticleActivity extends AppCompatActivity implements LocationListener {

    ImageView ivHomePage, ivAddArticle, ivMyProfile;

//    Firebase Realtime Database
    FirebaseDatabase dbRootNode;
    DatabaseReference dbReference;

    Uri imageUri;
    String myUri = "";
    StorageTask uploadTask;
    StorageReference storageReference;

    TextView tvPost;
    ImageView ivAddImage, ivAddLocation;
    EditText etDescription, etLocation;
    ProgressBar progressBar;

    // Location Manager Class
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        tvPost = findViewById(R.id.tvPost);
        ivAddImage = findViewById(R.id.ivAddImage);
        ivAddLocation = findViewById(R.id.ivAddLocation);
        etDescription = findViewById(R.id.etDescription);
        etLocation = findViewById(R.id.etLocation);
        progressBar = findViewById(R.id.progressBar);

        ivHomePage = findViewById(R.id.ivHomePage);
        ivAddArticle = findViewById(R.id.ivAddArticle);
        ivMyProfile = findViewById(R.id.ivMyProfile);

        ivHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(AddArticleActivity.this, HomePage.class);
                startActivity(homePage);
            }
        });

        /*
        Move To
        Other Activity
        */
        ivAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addArticle = new Intent(AddArticleActivity.this, AddArticleActivity.class);
                startActivity(addArticle);
            }
        });

        ivMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myProfile = new Intent(AddArticleActivity.this, AllArtikelActivity.class);
                startActivity(myProfile);
            }
        });

        /*Declare Firebase Storage Reference*/
        storageReference = FirebaseStorage.getInstance().getReference("artikel");

        /*
        When Image clicked
        */
        ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .start(AddArticleActivity.this);
            }
        });

        /*
        When Location Clicked
        */
        ivAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLocation();
            }
        });

    }


    public void back(View view) {
        Intent back = new Intent(this, HomePage.class);
        startActivity(back);
    }

    public void post(View view) {
        if (!ValidateDescription() | !ValidateLocation()){
            return;
        } else{
            uploadImage();
        }
    }

    /*
    Validate Input for Description and Location
    */

    private boolean ValidateLocation() {
        String valLocation = etLocation.getText().toString();

        if (valLocation.isEmpty()){
            Toast.makeText(this, "Location Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean ValidateDescription() {
        String valDescription = etDescription.getText().toString();

        if (valDescription.isEmpty()){
            Toast.makeText(this, "Deskripsi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /*
    Start Explicit Intent
    Check If Request Code Is Crop Image Code
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            ivAddImage.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomePage.class));
            finish();
        }
    }
    /*
    End Explicit Intent
    Check If Request Code Is Crop Image Code
    */

    /*
    Start Get File Extension From Image
    */
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    /*
    End Get File Extension From Image
    */

    /*
    Start Method For Upload Image
    */
    private void uploadImage() {
        progressBar.setVisibility(View.VISIBLE);

        if (imageUri != null){
//            Call Storage Reference, storage reference already declare at line 104
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            /*Get the file name ex: 123456.extension*/

//            Upload To Firebase Storage
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    /*If Task Success, get the link*/
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        /*After get the link, Take that link as result*/
                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();

                        dbRootNode = FirebaseDatabase.getInstance();

                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        String uid = mAuth.getCurrentUser().getUid().toString();

                        String postId = UUID.randomUUID().toString();

                        dbReference = dbRootNode.getReference("artikel").child(postId);

//                        Get Date Time When Post
                        Date todayTime = Calendar.getInstance().getTime();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
//                        Change timezone to Jakarta WIB
                        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
                        dateFormat.setTimeZone(tz);
                        String formattedDate = dateFormat.format(todayTime);

                        String description = etDescription.getText().toString();
                        String location = etLocation.getText().toString();
                        String process = "0";

                        Article mArtikel = new Article(postId, description, location, myUri, uid, formattedDate, process);
                        dbReference.setValue(mArtikel);

                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(AddArticleActivity.this, "Berhasil Di Post", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);

                        Intent success = new Intent(AddArticleActivity.this, HomePage.class);
                        startActivity(success);
                        finish();
                    } else {
                        Toast.makeText(AddArticleActivity.this, "Failed To Post", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddArticleActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    /*
    End Method For Upload Image
    */

    /*
    Method To Get Current Location
    */
    @SuppressLint("MissingPermission")
    private void GetLocation() {
//        Check Permission To Use Location
        CheckPermission();

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, AddArticleActivity.this);
        } catch (Exception e){

        }

    }

//    Method To check Permission To Use Location
    private void CheckPermission() {
        if (ContextCompat.checkSelfPermission(AddArticleActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddArticleActivity.this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        /*
        Get Latitude and Logtitude
        */
        try {
            Geocoder geocoder = new Geocoder(AddArticleActivity.this, Locale.getDefault());
            /*
            Address Class helps to fetch street address, locality, city, country etc
            */
            List<Address> mAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = mAddress.get(0).getAddressLine(0);

            etLocation.setText(address);
            Toast.makeText(this, "Success Get Current Location", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

}