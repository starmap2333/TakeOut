package com.example.take_out.component;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.take_out.R;
import com.example.take_out.databinding.ActivityUserDesignBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class UserDesignActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_PERMISSION = 2;
    String currentPhotoPath;
    /*
     *全局在这里
     */
    private View frag_view2;
    private ImageButton image_select;
    private ImageView imageView;
    private EditText edt_username;
    private EditText edt_introduction;
    private EditText edt_address;
    private EditText edt_phone;
    private Button btn_change;
    private ActivityUserDesignBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        binding = ActivityUserDesignBinding.inflate(getLayoutInflater());
        frag_view2 = binding.getRoot();
        setContentView(frag_view2);
        image_select = frag_view2.findViewById(R.id.imageButton);

        imageView = frag_view2.findViewById(R.id.imageView_setHeadImage);
        image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        edt_username = frag_view2.findViewById(R.id.editText);
        edt_introduction = frag_view2.findViewById(R.id.editText2);
        edt_address = frag_view2.findViewById(R.id.editText3);
        edt_phone = frag_view2.findViewById(R.id.editText4);

        btn_change = frag_view2.findViewById(R.id.button_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserDesignStruct user = new UserDesignStruct();
//                user.setUsername(edt_username.getText().toString());
//                user.setInfo(edt_introduction.getText().toString());
//                user.setAddress(edt_address.getText().toString());
//                user.setPhone(edt_phone.getText().toString());
//                String json_user = "JSON.toJSONString(user)";
                //Toast.makeText(getContext(),json_user,Toast.LENGTH_LONG);
//                Log.d("test", json_user);
//                edt_username.setText(user.getUsername());
//                edt_introduction.setText(user.getInfo());
//                edt_address.setText(user.getAddress());
//                edt_phone.setText(user.getPhone());
//                UserService userService = null;
//                userService.uploadUserFace(currentPhotoPath.,42);
            }
        });

    }

    public void showDialog() {
        final String[] ways = new String[]{"使用默认", "拍照选择"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("选择方式");
        builder.setItems(ways, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getContext(),ways[which],Toast.LENGTH_LONG).show();
                if (which == 1) {
                    //Toast.makeText(getContext(),ways[which],Toast.LENGTH_LONG).show();
                    cameraTake();
                }
            }

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void cameraTake() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA}, REQUEST_PERMISSION);
        } else {
            dispatchTakePictureIntent();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0) {
                    boolean permitted = true;
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            permitted = false;
                            break;
                        }
                    }
                    if (permitted) {
                        dispatchTakePictureIntent();
                    }
                }
            default:
                break;
        }
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            String dir = currentPhotoPath;
            //获取内部存储状态  
            String state = Environment.getExternalStorageState();
            //如果状态不是mounted，无法读写  
            if (!state.equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            Calendar now = new GregorianCalendar();
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            String fileName = simpleDate.format(now.getTime());

            try {
                File file = new File(dir + fileName + ".jpg");
                FileOutputStream out = new FileOutputStream(file);
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
