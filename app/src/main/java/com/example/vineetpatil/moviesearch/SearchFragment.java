package com.example.vineetpatil.moviesearch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private static final String TAG = SearchFragment.class.getName();

    private static final String searchUrl = "http://www.omdbapi.com/?s=%s&page=%d";
    private static final String detailsUrl = "http://www.omdbapi.com/?i=%s&y=&plot=full&r=json";

    private String searchTextHint;
    private RequestQueue requestQueue;

    private SearchResultAdapter searchResultAdapter;

    private int currentPage = 0;
    private boolean loadingData = false;
    private String searchQuery = "";

    private ListView listView;
    private EditText searchText;
    private Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.searchResultAdapter = new SearchResultAdapter(getContext(), new ArrayList<TitleRecord>(), false);

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        this.searchText = (EditText) rootView.findViewById(R.id.search_bar);
        this.searchButton = (Button) rootView.findViewById(R.id.search_button);
        this.listView = (ListView) rootView.findViewById(R.id.list_view);
        this.listView.setAdapter(searchResultAdapter);
        currentPage = 0;  // The page number of search results from OMDB

        this.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // NO-OP
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // fetch new items as required.
                if (listView.getLastVisiblePosition() == searchResultAdapter.getCount() - 1 && !loadingData) {
                    // We are at the very end; fetch more items
                    search(searchQuery, ++currentPage);
                }
            }
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DisplayUtil.displayItem(SearchFragment.this.searchResultAdapter.getItem(position), SearchFragment.this.getActivity());
            }
        });

        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery = searchText.getText().toString();
                if (searchQuery != null && searchQuery.length() > 0 && !searchQuery.equals(searchTextHint)) {
                    currentPage = 1;
                    searchResultAdapter.clear();
                    requestQueue.cancelAll(TAG);
                    loadingData = false;
                    search(searchQuery, 1);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.searchTextHint = getActivity().getString(R.string.search_text);
        this.requestQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
    }

    private void search(String searchQuery, int pageNumber) {
        //  query OMDB for search results
        String url = String.format(searchUrl, searchQuery, pageNumber);
        GsonRequest<SearchResponse> searchResponseGsonRequest = new GsonRequest<>(url, SearchResponse.class,
                null, new SearchResultsListener(), new SearchResultsErrorListener());
        searchResponseGsonRequest.setTag(TAG);
        requestQueue.add(searchResponseGsonRequest);
        loadingData = true;
    }

    private void getDetails(String imdbID) {
        // query OMDB to get details of this title
        String url = String.format(detailsUrl, imdbID);
        GsonRequest<TitleRecord> titleRecordGsonRequest = new GsonRequest<>(url, TitleRecord.class,
                null, new TitleRecordListener(), new TitleRecordErrorListener());
        // Set TAG to this request so that this can be cancelled when not required.
        titleRecordGsonRequest.setTag(TAG);
        requestQueue.add(titleRecordGsonRequest);
    }

    private synchronized void addTitleRecord(final TitleRecord titleRecord) {
        this.searchResultAdapter.add(titleRecord);
    }

    private class SearchResultsListener implements Response.Listener<SearchResponse> {
        @Override
        public void onResponse(SearchResponse response) {
            //Log.d(TAG, "SearchResultsListener - onResponse : " + response.toString());
            if (response.getTotalResults() > 0 && response.getSearch() != null) {
                for (SearchResponse.SearchResult searchResult : response.getSearch()) {
                    getDetails(searchResult.getImdbID());
                }
            }
            SearchFragment.this.loadingData = false;
        }
    }

    private class SearchResultsErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "Error getting results from OMDB ", error.fillInStackTrace());
            SearchFragment.this.loadingData = false;
        }
    }

    private class TitleRecordListener implements Response.Listener<TitleRecord> {
        @Override
        public void onResponse(TitleRecord response) {
            //Log.d(TAG, "TitleRecordListener - onResponse : " + response.toString());
            addTitleRecord(response);
        }
    }

    private class TitleRecordErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "Error getting title detail results from OMDB ", error.fillInStackTrace());
        }
    }
}
