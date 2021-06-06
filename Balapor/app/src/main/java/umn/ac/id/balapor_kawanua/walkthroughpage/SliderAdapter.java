package umn.ac.id.balapor_kawanua.walkthroughpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import umn.ac.id.balapor_kawanua.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    /*Array Of Assets*/
    public int[] slider_images = {
            R.drawable.main_photo_1,
            R.drawable.main_photo_2,
            R.drawable.main_photo_3
    };

    public String[] slider_heading = {
            "What ?",
            "How ?",
            "Goals"
    };

    public String[] slider_paragraph = {
            "Balapor menyediakan layanan pengaduan kepada masyarakat Sulawesi Utara untuk menunjang keamanan dan kenyamanan selama beraktivitas",
            "Laporan hanya perlu berupa cerita ataupun gambar beserta dengan lokasi kejadian, kemudian laporan tersebut akan diproses dan ditindak lanjuti",
            "Dengan adanya laporan dari masyarakat, diharapkan pengawasan akan kinerja dari segala instansi yang berada di Sulawesi Utara menjadi lebih baik, dan memberikan informasi tentang semua instansi yang berada di Sulawesi Utara"
    };

    @Override
    public int getCount() {
        return slider_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_walkthrough, container, false);

        ImageView ivWalkthroughImage = (ImageView) view.findViewById(R.id.ivWalkthroughImage);
        TextView tvWalkthroughHeading = (TextView) view.findViewById(R.id.tvWalkthroughHeading);
        TextView tvWalkthroughParagraph = (TextView) view.findViewById(R.id.tvWalkthroughParagraph);

        ivWalkthroughImage.setImageResource(slider_images[position]);
        tvWalkthroughHeading.setText(slider_heading[position]);
        tvWalkthroughParagraph.setText(slider_paragraph[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
