package ru.mirea.populationcensus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.populationcensus.Model.City;
import ru.mirea.populationcensus.Model.Users;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private DatabaseReference mDataBase;
    private String City_name;
    private Spinner spinner;
    private List<City> listTemp;

    public Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search.
     */
    // TODO: Rename and change types and number of parameters
    public static Search newInstance(String param1, String param2) {
        Search fragment = new Search();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search2, container, false);

        Button search_information = (Button) view.findViewById(R.id.search_btn);
        spinner = view.findViewById(R.id.spinnerCity);

        listView = view.findViewById(R.id.list_information);
        listData = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);

        search_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                City_name = spinner.getSelectedItem().toString();
                mDataBase = FirebaseDatabase.getInstance("https://population-census-d9478-default-rtdb.firebaseio.com/").getReference(City_name);
                getDataFromDB();
                if(Users.isUserAuthorized == true) {
                    setOnClickItem();
                }
            }
        });


        return view;
    }

    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(listData.size() > 0) listData.clear();
                if(listTemp.size() > 0) listTemp.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    City city = ds.getValue(City.class);
                    assert city != null;
                    listData.add(city.name);
                    listTemp.add(city);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }

    private void setOnClickItem(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                City city = listTemp.get(i);

                Fragment fr = new NameAbout();
                Bundle args = new Bundle();
                args.putString("name", city.name);
                args.putString("sex", city.sex);
                args.putString("about", city.about);
                fr.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,fr);
                transaction.commit();
            }
        });
    }


}