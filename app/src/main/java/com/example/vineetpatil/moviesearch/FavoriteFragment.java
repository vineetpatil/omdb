package com.example.vineetpatil.moviesearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO : Implement a custom Dialog to display contents of this item
                displayItem(FavoriteFragment.this.searchResultAdapter.getItem(position));
            }
        });

        return rootView;
    }

    private void displayItem(TitleRecord item) {
        // TODO : Implement a custom Dialog to display contents of this item
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllFavorites();
    }

    private void getAllFavorites() {
        List<TitleRecord> titleRecords = favorites.getAllFavorites();
        if (titleRecords != null && !titleRecords.isEmpty()) {
            this.searchResultAdapter.clear();
            this.searchResultAdapter.addAll(titleRecords);
        }
    }
}
