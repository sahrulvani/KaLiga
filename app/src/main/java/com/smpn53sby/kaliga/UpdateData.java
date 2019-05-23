package com.smpn53sby.kaliga;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static android.text.TextUtils.isEmpty;

public class UpdateData extends AppCompatActivity {

    String Storage_Path = "All_Image/";
    public static final String Database_Path = "Data_KaLiga";

    public static final int REQUEST_CODE_GALLERY = 001;

    private Uri FilePathUri;
    private ProgressDialog progressDialog;

    private EditText newjudul, newpengarang;
    private Button btnupdate,btnimage;
    private ImageView SelectImage;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String cekjudul, cekpengarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        getSupportActionBar().setTitle("Update Data");

        /*newjudul = (EditText) findViewById(R.id.new_judul);
        newpengarang = (EditText) findViewById(R.id.new_pengarang);
        btnupdate = (Button) findViewById(R.id.btn_update);
        btnimage = (Button) findViewById(R.id.btn_updt_img);
        SelectImage = (ImageView) findViewById(R.id.new_image);

        getData();

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        btnimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImageToFirebaseStorage();
            }
        });
    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    private void getData(){
        final String getJudul = getIntent().getExtras().getString("dataJudul");
        final String getPengarang = getIntent().getExtras().getString("dataPengarang");
        final String getImage = getIntent().getExtras().getString("dataImage");

        newjudul.setText(getJudul);
        newpengarang.setText(getPengarang);
        Glide.with(this).load(getImage).fitCenter().centerCrop().into(SelectImage);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                SelectImage.setImageBitmap(bitmap);
                btnimage.setText("Image Selected");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getFileExtension(Uri uri){
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    public void UploadImageToFirebaseStorage(){
        if(FilePathUri != null && SelectImage != null){
            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference1 = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + getFileExtension(FilePathUri) + SelectImage);
            storageReference1.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String Sjudul = newjudul.getText().toString().trim();
                            String Spengarang = newpengarang.getText().toString().trim();

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "Image Sukses di Upload", Toast.LENGTH_LONG).show();

                            String ImageUploadId = databaseReference.push().getKey();
                            DataModel imageUploadInfo = new DataModel(taskSnapshot.getDownloadUrl().toString(), Sjudul, Spengarang, ImageUploadId);

                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateData.this, "error", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(UpdateData.this, "Please Select Image or Add Field", Toast.LENGTH_LONG).show();
        }*/
    }

}
