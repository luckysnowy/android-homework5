package com.example.dell.mygym;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class dashboardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        final Fragment fragment = new WebviewFragment();
        final Bundle bundle = new Bundle();
        final  Fragment a=new anounceFragment();
        super.onActivityCreated(savedInstanceState);
        LinearLayout button = (LinearLayout) getActivity().findViewById(R.id.football_but);
        TextView b2=(TextView) getActivity().findViewById(R.id.in);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("url", "http://www.njtu.edu.cn/");
                //fragment = new WebviewFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,
                        fragment).commit();
                //Toast.makeText(getActivity(), "Football Section been clicked!", Toast.LENGTH_LONG).show();
            }
        });
         b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content,
                        a).commit();
                //Toast.makeText(getActivity(), "Football Section been clicked!", Toast.LENGTH_LONG).show();
            }
        });

    }
}

