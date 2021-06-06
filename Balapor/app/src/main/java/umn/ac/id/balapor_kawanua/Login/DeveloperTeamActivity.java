package umn.ac.id.balapor_kawanua.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import umn.ac.id.balapor_kawanua.R;

public class DeveloperTeamActivity extends AppCompatActivity {
    TextView tvNomorTeo, tvNomorYono, tvNomorMikel;

    ImageView ivGithubTeo, ivLinkedinTeo, ivIgTeo,
            ivGithubYono, ivLinkedinYono, ivIgYono,
            ivGithubMikel, ivLinkedinMikel, ivIgMikel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_team);

        /*Start TextView*/
        tvNomorTeo = findViewById(R.id.tvNomorTeo);
        tvNomorYono = findViewById(R.id.tvNomorYono);
        tvNomorMikel = findViewById(R.id.tvNomorMikel);
        /*End TextView*/

        /*Start ImageView*/
        ivGithubTeo = findViewById(R.id.ivGithubTeo);
        ivLinkedinTeo = findViewById(R.id.ivLinkedinTeo);
        ivIgTeo = findViewById(R.id.ivIgTeo);

        ivGithubYono = findViewById(R.id.ivGithubYono);
        ivLinkedinYono = findViewById(R.id.ivLinkedinYono);
        ivIgYono = findViewById(R.id.ivIgYono);

        ivGithubMikel = findViewById(R.id.ivGithubMikel);
        ivLinkedinMikel = findViewById(R.id.ivLinkedinMikel);
        ivIgMikel = findViewById(R.id.ivIgMikel);
        /*End ImageView*/

        DeveloperTeo();
        DeveloperYono();
        DeveloperMikel();
    }

    public void back(View view) {
        Intent back = new Intent(DeveloperTeamActivity.this, LoginActivity.class);
        startActivity(back);
    }


    /*
    Start
    Teo
    Profile
    */
    private void DeveloperTeo() {
        tvNomorTeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomorTelp = tvNomorTeo.getText().toString();
                Uri uriTelp = Uri.parse("tel:" + nomorTelp);
                Intent call = new Intent(Intent.ACTION_DIAL, uriTelp);
                startActivity(call);
            }
        });

        ivGithubTeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri Link = Uri.parse("https://github.com/tywowiling88");
                Intent go = new Intent(Intent.ACTION_VIEW, Link);
                startActivity(go);
            }
        });

        ivLinkedinTeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri Link = Uri.parse("https://www.linkedin.com/in/teofilus-yohanes-wowiling-16274a1b2/");
                Intent go = new Intent(Intent.ACTION_VIEW, Link);
                startActivity(go);
            }
        });

        ivIgTeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri Link = Uri.parse("https://www.instagram.com/teoow");
                Intent go = new Intent(Intent.ACTION_VIEW, Link);
                startActivity(go);
            }
        });
    }
    /*
    End
    Teo
    Profile
    */

    /*
    Start
    Yono
    Profile
    */
    private void DeveloperYono() {
        tvNomorYono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomorTelp = tvNomorYono.getText().toString();
                Uri uriTelp = Uri.parse("tel:" + nomorTelp);
                Intent call = new Intent(Intent.ACTION_DIAL, uriTelp);
                startActivity(call);
            }
        });

        ivGithubYono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri Link = Uri.parse("https://github.com/heriyononuaji");
                Intent go = new Intent(Intent.ACTION_VIEW, Link);
                startActivity(go);
            }
        });

        ivLinkedinYono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri Link = Uri.parse("https://www.linkedin.com/in/heriyono-mohamad-nuaji-6701a5207/");
                Intent go = new Intent(Intent.ACTION_VIEW, Link);
                startActivity(go);
            }
        });

        ivIgYono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri Link = Uri.parse("https://www.instagram.com/hmnuaji");
                Intent go = new Intent(Intent.ACTION_VIEW, Link);
                startActivity(go);
            }
        });
    }
    /*
    End
    Yono
    Profile
    */

    /*
    Start
    Mikel
    Profile
    */
    private void DeveloperMikel() {
        tvNomorMikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomorTelp = tvNomorMikel.getText().toString();
                Uri uriTelp = Uri.parse("tel:" + nomorTelp);
                Intent call = new Intent(Intent.ACTION_DIAL, uriTelp);
                startActivity(call);
            }
        });

        ivGithubMikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri Link = Uri.parse("https://github.com/mjhondry");
                Intent go = new Intent(Intent.ACTION_VIEW, Link);
                startActivity(go);
            }
        });

        ivLinkedinMikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri Link = Uri.parse("https://www.linkedin.com/in/michae-jhondry-6921a5207/");
                Intent go = new Intent(Intent.ACTION_VIEW, Link);
                startActivity(go);
            }
        });

        ivIgMikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri Link = Uri.parse("https://www.instagram.com/mjhondry_");
                Intent go = new Intent(Intent.ACTION_VIEW, Link);
                startActivity(go);
            }
        });
    }
    /*
    End
    Mikel
    Profile
    */



}