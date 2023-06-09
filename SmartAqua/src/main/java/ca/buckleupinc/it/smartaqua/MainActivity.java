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

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
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
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaTemperature()).commit();
        } else if (itemId == R.id.SmartAquaSwitchMenu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaSwitch()).commit();
        } else if (itemId == R.id.SmartAquaSettingsMenu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaSettings()).commit();
        } else if (itemId == R.id.SmartAquaFeedbackMenu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaFeedback()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaHome()).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.implemented_menu, menu);
        for(int i = 0; i < menu.size(); i++){
            MenuItem menuItem = menu.getItem(i);
            SpannableString spannable = new SpannableString(
                    menu.getItem(i).getTitle().toString()  );
            spannable.setSpan(new ForegroundColorSpan(Color.BLACK),
                    0, spannable.length(), 0);
            menuItem.setTitle(spannable);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.SmartAquaHomeBtn) {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaHome()).commit();
        } else if (itemId == R.id.SmartAquaSetM) {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaSettings()).commit();
        } else if (itemId == R.id.SmartAquaFeedbackM) {
            getSupportFragmentManager().beginTransaction().replace(R.id.SmartAquaFragmentSection, new SmartAquaFeedback()).commit();
        } else if (itemId == R.id.SmartAquaContactUsM) {
            String phoneNum = "519-902-1759";

            String uri = "tel:" + phoneNum.trim() ;
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    //Alert Dialog message on Back Key pressed
    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.icon_error_48px)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(getResources().getString(R.string.alertDialogMesg))
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> finish())
                .setNegativeButton(R.string.no, null)
                .show();
    }
}


