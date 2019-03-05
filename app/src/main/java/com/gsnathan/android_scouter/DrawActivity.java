package com.gsnathan.android_scouter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;

import com.raed.drawingview.BrushView;
import com.raed.drawingview.DrawingView;
import com.raed.drawingview.brushes.Brush;
import com.raed.drawingview.brushes.BrushSettings;
import com.raed.drawingview.brushes.Brushes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class DrawActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_IMPORT_IMAGE = 10;

    private DrawingView mDrawingView;
    private SeekBar mSizeSeekBar;
    private Button mUndoButton;
    private Button mRedoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_draw);

        mDrawingView = findViewById(R.id.drawing_view);
        mDrawingView.setUndoAndRedoEnable(true);
        mDrawingView.setBackgroundImage(BitmapFactory.decodeResource(getResources(),
                R.drawable.field_image));

        BrushView brushView = findViewById(R.id.brush_view);
        brushView.setDrawingView(mDrawingView);

        mSizeSeekBar = findViewById(R.id.size_seek_bar);
        mSizeSeekBar.setMax(100);
        mSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                BrushSettings brushSettings = mDrawingView.getBrushSettings();
                brushSettings.setSelectedBrushSize(i/100f);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });



//        findViewById(R.id.export).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);//ignoring the request code
//                    return;
//                }
//                Bitmap bitmap = mDrawingView.exportDrawing();
//                exportImage(bitmap);
//            }
//        });


        setupUndoAndRedo();
        setupBrushes();
        setupColorViews();
    }

    private void exportImage(Bitmap bitmap) {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/DeepScouterApp");
        folder.mkdirs();
        File imageFile = new File(folder, "field_image.png");
        try {
            storeBitmap(imageFile, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaScannerConnection.scanFile(
                this,
                new String[]{},
                new String[]{"image/png"},
                null);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMPORT_IMAGE){
            if (AppCompatActivity.RESULT_OK != resultCode)
                return;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                mDrawingView.setBackgroundImage(bitmap);
                mUndoButton.setEnabled(!mDrawingView.isUndoStackEmpty());
                mRedoButton.setEnabled(!mDrawingView.isRedoStackEmpty());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setBrushSelected(int brushID){
        BrushSettings settings = mDrawingView.getBrushSettings();
        settings.setSelectedBrush(brushID);
        int sizeInPercentage = (int) (settings.getSelectedBrushSize() * 100);
        mSizeSeekBar.setProgress(sizeInPercentage);
    }

    private void setupUndoAndRedo() {
        mUndoButton = findViewById(R.id.undo);
        mUndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawingView.undo();
                mUndoButton.setEnabled(!mDrawingView.isUndoStackEmpty());
                mRedoButton.setEnabled(!mDrawingView.isRedoStackEmpty());
            }
        });

        mRedoButton = findViewById(R.id.redo);
        mRedoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawingView.redo();
                mUndoButton.setEnabled(!mDrawingView.isUndoStackEmpty());
                mRedoButton.setEnabled(!mDrawingView.isRedoStackEmpty());
            }
        });

        mDrawingView.setOnDrawListener(new DrawingView.OnDrawListener() {
            @Override
            public void onDraw() {
                mUndoButton.setEnabled(true);
                mRedoButton.setEnabled(false);
            }
        });
    }

    private void setupColorViews() {
        View.OnClickListener colorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = ((ColorDrawable)view.getBackground()).getColor();
                BrushSettings brushSettings = mDrawingView.getBrushSettings();
                brushSettings.setColor(color);
            }
        };
        ViewGroup colorsContainer = findViewById(R.id.brush_colors_container);
        for (View colorView : colorsContainer.getTouchables())
            colorView.setOnClickListener(colorClickListener);

        View.OnClickListener bgClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = ((ColorDrawable)view.getBackground()).getColor();
                mDrawingView.setDrawingBackground(color);
            }
        };
    }

    private void setupBrushes() {
        setBrushSelected(Brushes.PEN);
    }

    private void storeBitmap(File file, Bitmap bitmap) throws Exception {
        if (!file.exists() && !file.createNewFile())
            throw new Exception("Not able to create " + file.getPath());
        FileOutputStream stream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        stream.flush();
        stream.close();
    }

    @Override
    public void onBackPressed() {
        exportImage(mDrawingView.exportDrawing());
        super.onBackPressed();
    }

}

