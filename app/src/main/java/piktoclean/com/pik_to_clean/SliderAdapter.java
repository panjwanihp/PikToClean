package piktoclean.com.pik_to_clean;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by Admin on 19-Mar-18.
 */

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
      R.drawable.gd,
      R.drawable.gd,
            R.drawable.gd
    };
    public String[] slide_headings ={
            "Pmkvy",
            "objectives",
            "scope"
    };
    public String[] slide_desc= {
            "Approved for another four years (2016-2020) to benefit 10 million youth Allocated Budget 12,000 Crores Pradhan Mantri Kaushal Vikas Yojana (PMKVY) is the flagship scheme of the Ministry of Skill Development and Entrepreneurship (MSDE). The objective of this Skill Certification Scheme is to enable a large number of Indian youth to take up industry-relevant skill training that will help them in securing a better livelihood. Individuals with prior learning experience or skills will also be assessed and certified under Recognition of Prior Learning (RPL).\" />\n",
            "1.Create opportunities for all to acquire skills, especially for youth, women and disadvantaged groups (Scheduled Castes, Scheduled Tribes, and Other Backward Classes)\n2.Promote commitment by all stakeholders to own skill development initiatives.\n3.Develop a high-quality skilled workforce that will help emerging employment market needs\n4.Enable effective coordination between different ministries, the Centre and the States and public and private providers.",
            "1.Encourage Standardization in the certification process\n2.Provide Monetary Awards to the candidates who complete the course successfully\n3.Training for self – employment development\n4.Adult learning, retiring employees and lifelong learning\n5.institutional based skill development including vocational schools/ ITCs/ ITIS/ polytechnics"

    };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view ==(RelativeLayout) o;
    }

    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        ((ViewPager) container).removeView((RelativeLayout)object);
    }
    @Override
    public Object instantiateItem(ViewGroup container,int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

}
