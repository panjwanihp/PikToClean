package piktoclean.com.pik_to_clean.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import piktoclean.com.pik_to_clean.R;
import piktoclean.com.pik_to_clean.SliderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private ViewPager mSlideViewPager;
    private LinearLayout mdotsLayout;

    private TextView[] mDots;
    private SliderAdapter slideAdapter;

    private Button mNextBtn;
    private Button mBackBtn;

    private int mCurrentPage;
    private Context c;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        c=inflater.getContext();
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSlideViewPager = (ViewPager) getView().findViewById(R.id.slideViewPager);
        mdotsLayout = (LinearLayout) getView().findViewById(R.id.dotsLayout);

        mBackBtn = (Button) getView().findViewById(R.id.prevBtn);
        mNextBtn = (Button) getView().findViewById(R.id.nextBtn);

        slideAdapter = new SliderAdapter(c);
        mSlideViewPager.setAdapter(slideAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mSlideViewPager.setCurrentItem(mCurrentPage -  1);
            }
        });
    }

    public void addDotsIndicator(int postition){
        mDots =new TextView[3];
        mdotsLayout.removeAllViews();

        for (int i=0;i < mDots.length;i++){

            mDots[i] =  new TextView(c);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(50);
            mDots[i].setTextColor(getResources().getColor(R.color.pink));

            mdotsLayout.addView(mDots[i]);
        }
        if (mDots.length > 0){
            mDots[postition].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            mCurrentPage = i;

            if (i == 0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                //mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
            }else if (i == mDots.length - 1){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                //mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Finish");
                mBackBtn.setText("Back");

            }else {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                //mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
