package umn.ac.id.balapor_kawanua.walkthroughpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import umn.ac.id.balapor_kawanua.HomePage.HomePage;
import umn.ac.id.balapor_kawanua.MyProfile.AllArtikelActivity;
import umn.ac.id.balapor_kawanua.R;

public class WalkThrough extends AppCompatActivity {

    ViewPager view_pager_wt;
    LinearLayout linear_layout_wt;

    private TextView[] mDots;
    private SliderAdapter sliderAdapter;
    private int mCurrentPage;
    Button btnFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);

        view_pager_wt = (ViewPager) findViewById(R.id.view_pager_wt);
        linear_layout_wt = (LinearLayout) findViewById(R.id.linear_layout_wt);

        btnFinish = findViewById(R.id.btnFinish);

        sliderAdapter = new SliderAdapter(this);
        view_pager_wt.setAdapter(sliderAdapter);

        addDots(0);

        view_pager_wt.addOnPageChangeListener(viewListener);
    }

// Method to make a 3 dots and add to linear layout
    public void addDots(int position){
        mDots = new TextView[3];
        linear_layout_wt.removeAllViews();

        for (int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.black));

            linear_layout_wt.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.blue));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int i) {
            addDots(i);
            mCurrentPage = i;

            if (mCurrentPage == 0) {
                btnFinish.setEnabled(false);
                btnFinish.setVisibility(View.INVISIBLE);
            } else if (mCurrentPage == 1) {
                btnFinish.setEnabled(false);
                btnFinish.setVisibility(View.INVISIBLE);
            } else {
                btnFinish.setEnabled(true);
                btnFinish.setVisibility(View.VISIBLE);
                btnFinish.setText("Finish");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public void ClickFinishWalkThrough(View view) {
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage = new Intent(WalkThrough.this, AllArtikelActivity.class);
                startActivity(nextPage);
            }
        });
    }

}