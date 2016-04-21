package com.example.vineetpatil.moviesearch;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SearchResultAdapter extends ArrayAdapter<TitleRecord> {
    private static final String TAG = SearchResultAdapter.class.getName();

    private final Context context;
    private final ImageLoader imageLoader;
    private final Favorites favorites;
    private final boolean shouldDeleteNonFavorites;

    public SearchResultAdapter(Context context, List<TitleRecord> titleRecords, boolean shouldDeleteNonFavorites) {
        super(context, R.layout.list_item, titleRecords);
        this.context = context;
        this.shouldDeleteNonFavorites = shouldDeleteNonFavorites;
        this.imageLoader = VolleySingleton.getInstance(context).getImageLoader();
        this.favorites = Favorites.getInstance(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TitleRecord titleRecord = getItem(position);
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.poster = (NetworkImageView) rowView.findViewById(R.id.poster);
            viewHolder.favoriteButton = (ToggleButton) rowView.findViewById(R.id.favorite_button);
            viewHolder.title = (TextView) rowView.findViewById(R.id.title);
            viewHolder.director = (TextView) rowView.findViewById(R.id.director);
            viewHolder.plot = (TextView) rowView.findViewById(R.id.plot);
            viewHolder.titleRecord = titleRecord;
            rowView.setTag(viewHolder);
        }

        // fill data
        final ViewHolder holder = (ViewHolder) rowView.getTag();
        if (isUrlValid(titleRecord.getPoster())) {
            holder.poster.setImageUrl(titleRecord.getPoster(), imageLoader);
        }
        holder.title.setText(titleRecord.getTitle());
        holder.director.setText(titleRecord.getDirector() + " (" + titleRecord.getYear() + ")");
        holder.plot.setText(titleRecord.getPlot());
        holder.titleRecord = titleRecord;
        boolean isFavorite = checkForFavorite(titleRecord);  // favorites might have changed with time
        holder.isFavorite = isFavorite;
        holder.favoriteButton.setChecked(isFavorite);
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.favoriteButton.isChecked()) {
                    favorites.putFavorite(holder.titleRecord.getImdbID(), holder.titleRecord);
                    holder.isFavorite = true;
                } else {
                    favorites.deleteFavorite(holder.titleRecord.getImdbID());
                    holder.isFavorite = false;
                    if (shouldDeleteNonFavorites) {
                        // Used for Favorites Fragment where only Favorites are to be displayed. Non-Favorites would be removed
                        SearchResultAdapter.this.remove(holder.titleRecord);
                    }
                }
            }
        });

        return rowView;
    }

    private boolean checkForFavorite(TitleRecord titleRecord) {
        if (favorites.getFavorite(titleRecord.getImdbID()) != null) {
            return true;
        }
        return false;
    }

    public boolean isUrlValid(String urlString) {
        if (!TextUtils.isEmpty(urlString)) {
            URL url = null;
            try {
                url = new URL(urlString);
                return (!TextUtils.isEmpty(url.getHost()) && !TextUtils.isEmpty(url.getPath()));
            } catch (MalformedURLException e) {
                return false;
            }
        }

        return false;
    }

    private class ViewHolder {
        public NetworkImageView poster;
        public ToggleButton favoriteButton;
        public TextView title;
        public TextView director;
        public TextView plot;
        public boolean isFavorite = false;
        public TitleRecord titleRecord;
    }
}
