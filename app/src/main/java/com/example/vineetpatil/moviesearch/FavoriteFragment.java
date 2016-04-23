package com.example.vineetpatil.moviesearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    private static final String TAG = FavoriteFragment.class.getName();

    private SearchResultAdapter searchResultAdapter;
    private ListView listView;
    private EditText searchText;
    private Button searchButton;
    private Favorites favorites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.searchResultAdapter = new SearchResultAdapter(getContext(), new ArrayList<TitleRecord>(), true);
        this.favorites = Favorites.getInstance(getContext());

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        this.searchText = (EditText) rootView.findViewById(R.id.search_bar);
        this.searchText.setVisibility(View.GONE);
        this.searchButton = (Button) rootView.findViewById(R.id.search_button);
        this.searchButton.setVisibility(View.GONE);
        this.listView = (ListView) rootView.findViewById(R.id.list_view);
        this.listView.setAdapter(searchResultAdapter);
        getAllFavorites();

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DisplayUtil.displayItem(FavoriteFragment.this.searchResultAdapter.getItem(position), FavoriteFragment.this.getActivity());
            }
        });

        return rootView;
    }

    public void getAllFavorites() {
        Log.d(TAG, "getAllFavorites() called");
        if (favorites != null) {
            List<TitleRecord> titleRecords = favorites.getAllFavorites();
            if (titleRecords != null && !titleRecords.isEmpty()) {
                for (TitleRecord titleRecord : titleRecords) {
                    Log.d(TAG, "getAllFavorites() titleRecord : " + titleRecord);
                }
                this.searchResultAdapter.clear();
                this.searchResultAdapter.addAll(titleRecords);
            }
        }
    }
}
