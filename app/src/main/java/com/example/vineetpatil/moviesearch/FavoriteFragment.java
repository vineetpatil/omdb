package com.example.vineetpatil.moviesearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class FavoriteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        EditText searchText = (EditText) rootView.findViewById(R.id.search_bar);
        Button searchButton = (Button) rootView.findViewById(R.id.search_button);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(new SearchResultAdapter(getContext()));

        return rootView;
    }
}
