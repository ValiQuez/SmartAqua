/*  CENG-322-0NA: Group 6
    Denis Shwaloff - N01422583
    Alvaro Rodrigo Chavez Moya - N01455107
    Paolo Adrian Quezon - N01424883
    Nicholas Dibiase - N01367109            */

package ca.buckleupinc.it.smartaqua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.SmartAquaToolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.SmartAquaDrawerLayout);
        NavigationView navigationView = findViewById(R.id.SmartAquaNavigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaHome()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.SmartAquaWaterMenu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaQuality()).commit();
        } else if (itemId == R.id.SmartAquaLightMenu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaLight()).commit();
        } else if (itemId == R.id.SmartAquaTemperatureMenu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaQuality()).commit();
        } else if (itemId == R.id.SmartAquaSwitchMenu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaLight()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaHome()).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.implemented_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    protected void exitByBackKey() {
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage(R.string.alertDialogMesg)
                .setPositiveButton(R.string.yes, (arg0, arg1) -> {
                    finish();
                    //close();
                })
                .setNegativeButton(R.string.no, (arg0, arg1) -> {}).show();
    }
}


