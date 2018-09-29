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
            "Introduction",
            "How To Use?",
            "Developers"
    };
    public String[] slide_desc= {
            "The traditional way of manually monitoring the waste was the complex process and utilizes more human effort, time and cost.\n" +
                    "The basic project idea is to design a Smart Waste Detection System.\nSystem will automatically detect the garbage from the picture taken by the responsible-citizens.\n" +
                    "It will also have real-time monitoring capabilities, which would be remotely managed by the waste collectors (Garbage truck driver).\n" +
                    "The app is developed in the year of 2018.",
            "To create account,authenticate your number.\n2.The number will be verified using OTP.\n3.After successfully authentication you will be redirected to the profile\n where you can edit your profile details\n 4. To send Image,go to camera and capture image \n5. select the type of garbage then click 'YES' to proceed and 'NO' to discard \n 6. After sending image you can see the progress bar which indicate progress of work",
            "1.The PicToClean Application is developed by\n2. Harsh Panjwani \n3. Shivam Sharma\n4.Gaurav wasule\n5.Rahul Mishra\n6 .Shivam Mudliyar\n Guided By : Usman Gani Bhurani \n Co- Guided by : Mr. Gandhar Patwardhan    "

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
