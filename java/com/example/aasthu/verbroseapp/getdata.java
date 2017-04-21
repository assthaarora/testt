package com.example.aasthu.verbroseapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class getdata extends AppCompatActivity {
    TextView e1,t1,imgsize;
    AlertDialog dialog;
    ImageButton b;
    ImageView iv;
    String mediaPath;
    EditText title,description;
    TextInputLayout ttitle,tdescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getdata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        e1=(TextView)findViewById(R.id.imagepath);
        t1=(TextView)findViewById(R.id.attach) ;
        imgsize=(TextView)findViewById(R.id.imagesize) ;
        title=(EditText)findViewById(R.id.title);
        iv=(ImageView)findViewById(R.id.img) ;
        description=(EditText)findViewById(R.id.description);
       ttitle=(TextInputLayout)findViewById(R.id.ttitle);
        tdescription=(TextInputLayout)findViewById(R.id.tdescription);

b=(ImageButton)findViewById(R.id.cancle);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setVisibility(View.INVISIBLE);
                b.setVisibility(View.INVISIBLE);
                t1.setVisibility(View.INVISIBLE);
                iv.setVisibility(View.INVISIBLE);
                imgsize.setVisibility(View.INVISIBLE);
                //Toast.makeText(getdata.this,"dsg",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {

            Boolean isValid=true;
            if(title.getText().toString().isEmpty())
            {
              ttitle.setError("Fill the title");
                isValid=false;
            }
            else
            {
                ttitle.setErrorEnabled(false);
            }

            if(description.getText().toString().isEmpty())
            {
                tdescription.setError("Fill the title");
                isValid=false;
            }
            else
            {
               tdescription.setErrorEnabled(false);
            }
            if(isValid) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(this).setMessage("Submitting Data");
                final AlertDialog alert = dialog.create();
                alert.show();
                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (alert.isShowing()) {
                            alert.dismiss();
                        }
                    }
                };

                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        handler.removeCallbacks(runnable);
                    }
                });

                handler.postDelayed(runnable, 1000);
                title.setText("");
                description.setText("");
                e1.setVisibility(View.INVISIBLE);
                b.setVisibility(View.INVISIBLE);
                t1.setVisibility(View.INVISIBLE);
                iv.setVisibility(View.INVISIBLE);
                imgsize.setVisibility(View.INVISIBLE);

               // Toast.makeText(this, "Submitting data", Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.attach) {
            final AlertDialog.Builder mbuilder = new AlertDialog.Builder(getdata.this);
            final View mView= getLayoutInflater().inflate(R.layout.choiceimgvedio,null);
            ImageButton ved= (ImageButton) mView.findViewById(R.id.vedioo);
            ved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent galleryInput=new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryInput,1);
                   dialog.dismiss();

                }
            });
            ImageButton img= (ImageButton) mView.findViewById(R.id.imagee);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent galleryInput=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryInput,0);
                    dialog.dismiss();
                }
            });

            mbuilder.setView(mView);
            dialog=mbuilder.create();
            dialog.show();
           // dialog.getWindow().setLayout(250,250);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String s = cursor.getString(column_index);
                String filename=s.substring(s.lastIndexOf("/")+1);
                File file = new File(s);
                long length = file.length();
                e1.setText(filename);
                imgsize.setText(" " +length+"kb");

                e1.setVisibility(View.VISIBLE);
                b.setVisibility(View.VISIBLE);
                t1.setVisibility(View.VISIBLE);
                iv.setVisibility(View.VISIBLE);
                imgsize.setVisibility(View.VISIBLE);

            } else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                Uri selectedVedio=data.getData();
                String[] filePathColumn={MediaStore.Video.Media.DATA};
                Cursor cursor=getContentResolver().query(selectedVedio,filePathColumn,null,null,null);
                assert cursor!=null;
                cursor.moveToFirst();
                int column_index = cursor
                        .getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(column_index);
                String filename=mediaPath.substring(mediaPath.lastIndexOf("/")+1);
                File file = new File(mediaPath);
                long length = file.length();
                e1.setText(filename);
                imgsize.setText(""+ length+"kb");
                e1.setVisibility(View.VISIBLE);
                b.setVisibility(View.VISIBLE);
                t1.setVisibility(View.VISIBLE);
                iv.setVisibility(View.VISIBLE);
                imgsize.setVisibility(View.VISIBLE);

                cursor.close();}

            else {
                Toast.makeText(this, "You havenot picked any file", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }
            public String getPath(Uri uri) {
               String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cursor = managedQuery(uri, projection, null, null, null);
                if(cursor!=null) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                    cursor.moveToFirst();
                    return cursor.getString(column_index);
                }
                else return null;
            }



}
