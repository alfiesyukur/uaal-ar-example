package com.unity.mynativeapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.company.product.OverrideUnityActivity;

public class MainUnityActivity extends OverrideUnityActivity {

    static ImageButton colorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addControlsToUnityFrame();
    }

    @Override
    protected void updateUnityShopItem() {
        UnitySendMessage("AR Session Origin", "SetProduct", Integer.toString(MainActivity.CurrentSelectedItem ));
        UnitySendMessage("AR Session Origin", "SetColor", MainActivity.GetColorStringForCurrent());
    }

    protected void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public static void UpdateColorButton()
    {
        if (colorButton != null)
        {
            colorButton.setColorFilter(MainActivity.GetNextColorForCurrent());
        }
    }

    public void addControlsToUnityFrame() {
            Display display = getWindowManager(). getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int size = (point.x / 2);

            ImageButton backButton = new ImageButton(this);
            backButton.setImageResource(R.drawable.button_back);
            backButton.setBackgroundColor(Color.TRANSPARENT);
            backButton.setScaleType(ImageView.ScaleType.FIT_START);
            backButton.setX(10);

            backButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showMainActivity();
                }
            });
            getUnityFrameLayout().addView(backButton, size, size / 2);


            colorButton = new ImageButton(this);
            colorButton.setImageResource(R.drawable.button_colour);
            colorButton.setBackgroundColor(Color.TRANSPARENT);
            colorButton.setScaleType(ImageView.ScaleType.FIT_START);
            colorButton.setX(point.x - size * 0.8f);
            colorButton.setColorFilter(MainActivity.GetNextColorForCurrent());
            colorButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onColorButtonPressed();
                }
            });
            getUnityFrameLayout().addView(colorButton, size, size / 2);
    }

    private void onColorButtonPressed()
    {
        MainActivity.IncCurrentConfig();
        UpdateColorButton();
        updateUnityShopItem();
    }
}
