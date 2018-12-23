package com.example.dell.mygym;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;
public class coachesFragment extends ListFragment{
    String[] players = {"Wang Mo", "Li Jian", "刘教练", "David Zhang", "赵武 教练", "Van Persie"};
    String[] experience = {
            "Perfection is not attainable, but if we chase perfection we can catch excellence",
            "Health & Fitness Lifestyle Transformation.Gym doesn't change live, People do.",
            "If we chase perfection we can catch excellence",
            "Gym doesn't change live, People do.",
            "An accomplished fitness trainer with seven years of experience n hand",
            "Gym doesn't change live, People do.",
    };
    int[] images = {R.drawable.trainer1, R.drawable.trainer2, R.drawable.trainer3, R.drawable.trainer4, R.drawable.trainer5, R.drawable.trainer6};
    String []phones={"057187063728","057187063729","057187063720","057187063721","057187063722","057187063723"};
    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        //MAP
        HashMap<String, String> map = new HashMap<String, String>();

        //FILL
        for (int i = 0; i < players.length; i++) {
            map = new HashMap<String, String>();
            map.put("Player", players[i]);
            map.put("Info",experience[i]);
            map.put("Image", Integer.toString(images[i]));
            map.put("Phones",phones[i]);
            data.add(map);
        }

        //KEYS IN MAP
        String[] from = {"Player","Phones","Info", "Image"};

        //IDS OF VIEWS
        int[] to = {R.id.nameTxt,R.id.phone, R.id.infoTxt, R.id.imageView1};

        //ADAPTER
        adapter = new SimpleAdapter(getActivity().getBaseContext(), data, R.layout.trainerlist_item, from, to);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        getListView().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub

                Toast.makeText(getActivity(), data.get(pos).get("Player"), Toast.LENGTH_SHORT).show();
                String phn=data.get(pos).get("Phones").trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phn);
                intent.setData(data);
                startActivity(intent);
            }
        });
    }


}

