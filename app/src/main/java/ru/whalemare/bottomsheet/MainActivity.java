package ru.whalemare.bottomsheet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import ru.whalemare.sheetmenu.SheetMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setup();
            }
        });
    }

    // builder of an ordinary man
    private void setup() {
        SheetMenu.with(this)
                .setTitle(R.string.title)
                .setMenu(R.menu.empty_menu)
                .setClick(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                }).show();
    }

    // builder of a smoker
    private void setup2() {
        new SheetMenu(
                R.string.title,
                null,
                R.menu.menu,
                null, null,
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                },
                true
        ).show(this);
    }

    private void setup3(){
        SheetMenu sheetMenu = new SheetMenu();
        sheetMenu.setMenu(R.menu.menu);
        sheetMenu.setTitle("Title");
        sheetMenu.setClick(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }
}
