package umn.ac.id.balapor_kawanua.AdminInstansi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import umn.ac.id.balapor_kawanua.AdminAllUser.AdminAllUserActivity;
import umn.ac.id.balapor_kawanua.AdminArtikel.AdminArtikelActivity;
import umn.ac.id.balapor_kawanua.AdminProfile.AdminProfileActivity;
import umn.ac.id.balapor_kawanua.R;

public class AdminInstansiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_instansi);
    }

    public void menuHome(View view) {
        Intent homePage = new Intent(AdminInstansiActivity.this, AdminInstansiActivity.class);
        startActivity(homePage);
    }

    public void menuAllUserData(View view) {
        Intent allAccPage = new Intent(AdminInstansiActivity.this, AdminAllUserActivity.class);
        startActivity(allAccPage);
    }

    public void menuAdminProfile(View view) {
        Intent myProfilePage = new Intent(AdminInstansiActivity.this, AdminProfileActivity.class);
        startActivity(myProfilePage);
    }

    public void articlePage(View view) {
        Intent articlePage = new Intent(this, AdminArtikelActivity.class);
        startActivity(articlePage);
    }

    public void viewKepolisian(View view) {
        Intent kepolisian = new Intent(this, AllInstansiActivity.class);

        String key = "kepolisian";
        kepolisian.putExtra("Instansi", key);

        startActivity(kepolisian);
    }

    public void viewRumahSakit(View view) {
        Intent rumahsakit = new Intent(this, AllInstansiActivity.class);

        String key = "rumah sakit";
        rumahsakit.putExtra("Instansi", key);
        startActivity(rumahsakit);
    }

    public void viewPemadamKebakaran(View view) {
        Intent pemadamKebakaran = new Intent(this, AllInstansiActivity.class);

        String key = "pemadam kebakaran";
        pemadamKebakaran.putExtra("Instansi", key);
        startActivity(pemadamKebakaran);
    }

    public void viewDinasPemerintahan(View view) {
        Intent dinasPemerintahan = new Intent(this, AllInstansiActivity.class);

        String key = "dinas pemerintahan";
        dinasPemerintahan.putExtra("Instansi", key);
        startActivity(dinasPemerintahan);
    }

}