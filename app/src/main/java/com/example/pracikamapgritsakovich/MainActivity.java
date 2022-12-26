package com.example.pracikamapgritsakovich;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import helper.DB;
import helper.DBHelper;
import model.Settings;

public class MainActivity extends AppCompatActivity {
    map map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB.helper = new DBHelper(this, "map.db", null, 1);
        DB.helper.getSettings();
        map = findViewById(R.id.mapView);
        map.ctx = (Activity) this;
        tile.map = map;
        tile.life = Settings.getLife();
        int level = Settings.level;
        map.offsetX = Settings.offsetX;
        map.offsetY = Settings.offsetY;

        for (int i = 0; i < map.levels.length; i++)
        {
            if (level == map.levels[i])
            {
                map.currentLevel = i;
                break;
            }
        }
        map.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DB.helper.updateView(map.offsetX, map.offsetY, map.levels[map.currentLevel]);
    }

    public void onZoomIn(View v)
    {
        if (map.currentLevel == map.levels.length - 1)
            return;
        map.currentLevel++;
        map.offsetX *= 2;
        map.offsetY *= 2;
        map.offsetX -= map.width /2;
        map.offsetY -= map.height /2;
        map.l = new ArrayList<lines>();
        map.invalidate();
    }

    public void onZoomOut(View v)
    {
        if (map.currentLevel == 0)
            return;
        map.currentLevel--;
        map.offsetX += map.width /2;
        map.offsetY += map.height /2;
        map.offsetX /= 2;
        map.offsetY /= 2;
        map.l = new ArrayList<lines>();
        map.invalidate();
    }

    public void onSettingsClick(View v)
    {
        Intent intent = new Intent(this, settingsActivity.class);
        startActivity(intent);
    }
}