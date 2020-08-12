package com.example.photo.ImageEvent;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.example.photo.R;
import com.example.photo.databinding.FragmentPhoto2Binding;


import java.io.File;
import java.io.IOException;

import static com.example.photo.MainActivity.al_images;

public class PhotoFragment extends Fragment {
    static FragmentPhoto2Binding binding;
    int int_position;
    int position;
    ImageView imShare, imDel,imRo, imMore;
    float rotate =0;
    public PhotoFragment(int int_position, int position) throws IOException {
        this.int_position = int_position;
        this.position = position;
    }
    public static PhotoFragment newInstance(int int_position, int position) throws IOException {
        PhotoFragment fragment = new PhotoFragment(int_position,position);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_photo2, container, false);
        Glide.with(getContext())
                .load(al_images.get(int_position).getAl_imagepath().get(position))
                .into(binding.im);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar_layout);
        actionBar.setDisplayHomeAsUpEnabled(true);
        imShare = actionBar.getCustomView().findViewById(R.id.imShare);
        imDel = actionBar.getCustomView().findViewById(R.id.imDel);
        imRo = actionBar.getCustomView().findViewById(R.id.imRo);
        imMore = actionBar.getCustomView().findViewById(R.id.imMore);
        imShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                Uri uri1 = Uri.parse(al_images.get(int_position).getAl_imagepath().get(position-1));
                share.putExtra(Intent.EXTRA_STREAM, uri1);
                share.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(share, "Gửi ảnh đến"));
            }
        });
        imDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final AlertDialog textDialog = new AlertDialog().
//                textDialog.setButton(DialogInterface.BUTTON_NEGATIVE, this.getString(R.string.cancel).toUpperCase(), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        textDialog.dismiss();
//                    }
//                });
//                textDialog.setButton(DialogInterface.BUTTON_POSITIVE, this.getString(R.string.delete).toUpperCase(), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        if (Security.isPasswordOnDelete()) {
//
//                            Security.authenticateUser(SingleMediaActivity.this, new Security.AuthCallBack() {
//                                @Override
//                                public void onAuthenticated() {
//                                    deleteCurrentMedia();
//                                }
//
//                                @Override
//                                public void onError() {
//                                    Toast.makeText(getApplicationContext(), R.string.wrong_password, Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } else
//                            deleteCurrentMedia();
//                    }
//                });
//                textDialog.show();
                final androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setMessage("Bạn có chắc chắn muốn xóa")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File fdelete = new File(String.valueOf(Uri.parse(al_images.get(int_position).getAl_imagepath().get(position))));
                                if (fdelete.exists()) {
                                    if (fdelete.delete()) {
                                        System.out.println("file Deleted :" + String.valueOf(Uri.parse(al_images.get(int_position).getAl_imagepath().get(position))));
                                    } else {
                                        System.out.println("file not Deleted :" + String.valueOf(Uri.parse(al_images.get(int_position).getAl_imagepath().get(position))));
                                    }
                                }
                                MediaScannerConnection.scanFile(getContext(), new String[] { Environment.getExternalStorageDirectory().toString() }, null, new MediaScannerConnection.OnScanCompletedListener() {
                                    /*
                                     *   (non-Javadoc)
                                     * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
                                     */
                                    public void onScanCompleted(String path, Uri uri)
                                    {
                                        Log.i("ExternalStorage", "Scanned " + path + ":");
                                        Log.i("ExternalStorage", "-> uri=" + uri);
                                    }
                                });
                            }
                        })
                        .setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
        imRo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.rotate_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.rotate_right_90:
                                rotate += 90;
                                break;
                            case R.id.rotate_left_90:
                                rotate -= 90;
                                break;

                            case R.id.rotate_180:
                                rotate += 180;
                                break;
                        }
                        roateImage(binding.im);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        return binding.getRoot();
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            Animation aniSlide = AnimationUtils.loadAnimation(getContext(),R.anim.zoom_in);

            binding.im.startAnimation(aniSlide);

            return true;
        }
    }

    private void roateImage(ImageView imageView) {
//        Matrix matrix = new Matrix();
//        imageView.setScaleType(ImageView.ScaleType.MATRIX); //required
//        matrix.postRotate((float) rotate, imageView.getDrawable().getBounds().width()/2,    imageView.getDrawable().getBounds().height()/2);
//        imageView.setImageMatrix(matrix);
        Matrix matrix = new Matrix();
        Bitmap bitmap  = BitmapFactory.decodeFile(al_images.get(int_position).getAl_imagepath().get(position-1));
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(al_images.get(int_position).getAl_imagepath().get(position-1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                break;
        }
        imageView.setImageBitmap(bitmap);
        bitmap.recycle();
    }
}