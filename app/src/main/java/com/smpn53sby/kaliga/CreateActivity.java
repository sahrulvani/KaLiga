package com.smpn53sby.kaliga;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;


public class CreateActivity extends AppCompatActivity {

    String Storage_Path = "All_Image/";
    public static final String Database_Path = "Data_KaLiga";

    private ImageView SelectImage;
    private EditText edtjudul, edtpengarang, edtdeskripsi, edtklasifikasi, edttahun, edtisbn;
    private Button btnimage, btnsave;

    Uri FilePathUri;
    ProgressDialog progressDialog;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    private Toolbar toolbar;

    public static final int REQUEST_CODE_CAMERA = 001;
    public static final int REQUEST_CODE_GALLERY = 002;
    int Image_Request_Code = 7777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        toolbar = findViewById(R.id.toolbar_create);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        btnimage = findViewById(R.id.btn_image);
        btnsave = findViewById(R.id.btn_save);

        edtjudul = findViewById(R.id.edt_judul);
        edtpengarang = findViewById(R.id.edt_pengarang);
        edtdeskripsi = findViewById(R.id.edt_deskripsi);
        edtklasifikasi = findViewById(R.id.edt_klasifikasi);
        edttahun = findViewById(R.id.edt_tahun);
        edtisbn = findViewById(R.id.edt_isbn);

        SelectImage = findViewById(R.id.image);

        progressDialog = new ProgressDialog(CreateActivity.this);

        btnimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setRequestImage();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), REQUEST_CODE_GALLERY);
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImageToFirebaseStorage();
            }
        });
    }

    /*private void setRequestImage() {
        CharSequence[] item = {"Kamera", "Galeri"};
        AlertDialog.Builder request = new AlertDialog.Builder(this)
                .setTitle("Add Image")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        //request.create();
        request.show();
    }*/

    /*public void choosePhotoFromGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Please Select Image"), REQUEST_CODE_GALLERY);
    }

    public void takePhotoFromCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }*/

    /*protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {

            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                FilePathUri = data.getData();
                switch (type) {
                    case REQUEST_CODE_CAMERA:
                        Glide.with(CreateActivity.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(SelectImage);
                        break;

                    case REQUEST_CODE_GALLERY:
                        Glide.with(CreateActivity.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(SelectImage);
                        break;
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null){
            FilePathUri = data.getData();
            Glide.with(this).load(FilePathUri).into(SelectImage);

            /*try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                SelectImage.setImageBitmap(bitmap);
                btnimage.setText("Image Selected");

            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

    }

    public String GetFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void UploadImageToFirebaseStorage(){
        if(FilePathUri != null){
            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference1 = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference1.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String Sjudul = edtjudul.getText().toString().trim();
                            String Spengarang = edtpengarang.getText().toString().trim();
                            String Sdeskripsi = edtdeskripsi.getText().toString().trim();
                            String Sklasifikasi = edtklasifikasi.getText().toString().trim();
                            String Stahun = edttahun.getText().toString().trim();
                            String Sisbn = edtisbn.getText().toString().trim();

                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "Buku Telah di Tambah", Toast.LENGTH_LONG).show();

                            String ImageUploadId = databaseReference.push().getKey();
                            DataModel imageUploadInfo = new DataModel(taskSnapshot.getDownloadUrl().toString()
                                    , Sjudul, Spengarang, ImageUploadId, Sdeskripsi, Sklasifikasi, Stahun, Sisbn);

                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);

                            progressDialog.dismiss();
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(CreateActivity.this, "error", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(CreateActivity.this, "Please Select Image or Add Field", Toast.LENGTH_LONG).show();
        }
    }
}
