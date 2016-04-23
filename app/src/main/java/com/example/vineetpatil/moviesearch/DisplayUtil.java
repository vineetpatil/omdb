package com.example.vineetpatil.moviesearch;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.net.MalformedURLException;
import java.net.URL;

public class DisplayUtil {

    public static void displayItem(TitleRecord item, Context context) {
        // Implement a custom Dialog to display contents of this item
        final ImageLoader imageLoader = VolleySingleton.getInstance(context).getImageLoader();
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_display_dialog);
        dialog.setTitle(context.getString(R.string.item_detail_title));

        String movieTitle = String.format(context.getString(R.string.title), TextUtils.isEmpty(item.getTitle()) ? "N/A" : item.getTitle());
        String director_year = String.format(context.getString(R.string.director_year),
                TextUtils.isEmpty(item.getDirector()) ? "N/A" : item.getDirector(),
                TextUtils.isEmpty(item.getYear()) ? "N/A" : item.getYear());
        String plot_summary = String.format(context.getString(R.string.plot_summary), TextUtils.isEmpty(item.getPlot()) ? "N/A" : item.getPlot());
        String writer = String.format(context.getString(R.string.writer), TextUtils.isEmpty(item.getWriter()) ? "N/A" : item.getWriter());
        String actors = String.format(context.getString(R.string.actors), TextUtils.isEmpty(item.getActors()) ? "N/A" : item.getActors());
        String rated_released_runtime_genre = String.format(context.getString(R.string.rated_released_runtime_genre),
                TextUtils.isEmpty(item.getRated()) ? "N/A" : item.getRated(),
                TextUtils.isEmpty(item.getReleased()) ? "N/A" : item.getReleased(),
                TextUtils.isEmpty(item.getRuntime()) ? "N/A" : item.getRuntime(),
                TextUtils.isEmpty(item.getGenre()) ? "N/A" : item.getGenre());
        String language_country_metascore_imdbrating = String.format(context.getString(R.string.language_country_metascore_imdbrating),
                TextUtils.isEmpty(item.getLanguage()) ? "N/A" : item.getLanguage(),
                TextUtils.isEmpty(item.getCountry()) ? "N/A" : item.getCountry(),
                TextUtils.isEmpty(item.getMetascore()) ? "N/A" : item.getMetascore(),
                TextUtils.isEmpty(item.getImdbRating()) ? "N/A" : item.getImdbRating());
        String awards = String.format(context.getString(R.string.awards), TextUtils.isEmpty(item.getAwards()) ? "N/A" : item.getAwards());

        final NetworkImageView networkImageView = (NetworkImageView) dialog.findViewById(R.id.poster);
        if (isUrlValid(item.getPoster())) {
            networkImageView.setImageUrl(item.getPoster(), imageLoader);
        }
        final ToggleButton favoriteButton = (ToggleButton) dialog.findViewById(R.id.favorite_button);
        favoriteButton.setChecked(checkForFavorite(item, context));
        final TextView titleTextView = (TextView) dialog.findViewById(R.id.title);
        titleTextView.setText(movieTitle);
        final TextView directorYearTextView = (TextView) dialog.findViewById(R.id.director_year);
        directorYearTextView.setText(director_year);
        final TextView plotTextView = (TextView) dialog.findViewById(R.id.plot);
        plotTextView.setText(plot_summary);
        final TextView writerTextView = (TextView) dialog.findViewById(R.id.writer);
        writerTextView.setText(writer);
        final TextView actorsTextView = (TextView) dialog.findViewById(R.id.actors);
        actorsTextView.setText(actors);
        final TextView ratedTextView = (TextView) dialog.findViewById(R.id.rated_released_runtime_genre);
        ratedTextView.setText(rated_released_runtime_genre);
        final TextView languageTextView = (TextView) dialog.findViewById(R.id.language_country_metascore_imdbrating);
        languageTextView.setText(language_country_metascore_imdbrating);
        final TextView awardsTextView = (TextView) dialog.findViewById(R.id.awards);
        awardsTextView.setText(awards);

        final Button dismissButton = (Button) dialog.findViewById(R.id.dismiss_button);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static boolean isUrlValid(String urlString) {
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

    private static boolean checkForFavorite(TitleRecord titleRecord, Context context) {
        Favorites favorites = Favorites.getInstance(context);
        if (favorites.getFavorite(titleRecord.getImdbID()) != null) {
            return true;
        }
        return false;
    }
}