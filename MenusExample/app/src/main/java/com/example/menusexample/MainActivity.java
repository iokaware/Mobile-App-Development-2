package com.example.menusexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        registerForContextMenu(textView);

        textView.setOnClickListener(view -> {
            showPopupMenu();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search:
                showToast(getString(R.string.search));
                return true;
            case R.id.item_favorites:
                showToast(getString(R.string.favorites));
                return true;
            case R.id.item_settings:
                showToast(getString(R.string.settings));
                return true;
            case R.id.item_info:
                showToast(getString(R.string.info));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search:
                showToast(getString(R.string.search));
                return true;
            case R.id.item_favorites:
                showToast(getString(R.string.favorites));
                return true;
            case R.id.item_settings:
                showToast(getString(R.string.settings));
                return true;
            case R.id.item_info:
                showToast(getString(R.string.info));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void showToast(String text) {
        Toast.makeText(getApplicationContext(),
                "You Selected " + text, Toast.LENGTH_LONG).show();
    }

    public void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, textView);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.item_search:
                    showToast(getString(R.string.search));
                    return true;
                case R.id.item_favorites:
                    showToast(getString(R.string.favorites));
                    return true;
                case R.id.item_settings:
                    showToast(getString(R.string.settings));
                    return true;
                case R.id.item_info:
                    showToast(getString(R.string.info));
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }
}