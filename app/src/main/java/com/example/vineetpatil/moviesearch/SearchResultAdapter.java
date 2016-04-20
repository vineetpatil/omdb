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
    private final List<TitleRecord> titleRecords;
    private final ImageLoader imageLoader;

    public SearchResultAdapter(Context context, List<TitleRecord> titleRecords) {
        super(context, R.layout.list_item, titleRecords);
        this.context = context;
        this.titleRecords = titleRecords;
        this.imageLoader = VolleySingleton.getInstance(context).getImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
            rowView.setTag(viewHolder);
        }

        // fill data
        TitleRecord titleRecord = titleRecords.get(position);
        ViewHolder holder = (ViewHolder) rowView.getTag();
        if (isUrlValid(titleRecord.getPoster())) {
            Log.d(TAG, "Downloading image at url " + titleRecord.getPoster());
            holder.poster.setImageUrl(titleRecord.getPoster(), imageLoader);
        }
        holder.favoriteButton.setChecked(false);  // by default not favorite TODO: implementation of favorites
        holder.title.setText(titleRecord.getTitle());
        holder.director.setText(titleRecord.getDirector() + " (" + titleRecord.getYear() + ")");
        holder.plot.setText(titleRecord.getPlot());

        return rowView;
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

    static class ViewHolder {
        public NetworkImageView poster;
        public ToggleButton favoriteButton;
        public TextView title;
        public TextView director;
        public TextView plot;
    }
}
