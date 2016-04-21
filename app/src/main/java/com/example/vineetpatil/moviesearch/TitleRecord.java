package com.example.vineetpatil.moviesearch;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

public class TitleRecord {
    private static final Gson gson = new Gson();

    /**
     * {"Title":"Batman","Year":"1989","Rated":"PG-13","Released":"23 Jun 1989","Runtime":"126 min",
     * "Genre":"Action, Adventure","Director":"Tim Burton","Writer":"Bob Kane (Batman characters),
     * Sam Hamm (story), Sam Hamm (screenplay), Warren Skaaren (screenplay)","Actors":"Michael Keaton,
     * Jack Nicholson, Kim Basinger, Robert Wuhl","Plot":"The Dark Knight of Gotham City begins his war
     * on crime with his first major enemy being the clownishly homicidal Joker.","Language":"English,
     * French","Country":"USA, UK","Awards":"Won 1 Oscar. Another 9 wins & 22 nominations.",
     * "Poster":"http://ia.media-imdb.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg",
     * "Metascore":"66","imdbRating":"7.6","imdbVotes":"260,641","imdbID":"tt0096895","Type":"movie","Response":"True"}
     */
    @NonNull private String Title;
    @NonNull private String Year;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Poster;
    @NonNull private String imdbID;
    @NonNull private String Type;

    public TitleRecord(@NonNull String title, @NonNull String year, @NonNull String imdbID, @NonNull String type) {
        this.Title = title;
        this.Year = year;
        this.imdbID = imdbID;
        this.Type = type;
    }

    @NonNull
    public String getTitle() {
        return Title;
    }

    public void setTitle(@NonNull String title) {
        this.Title = title;
    }

    @NonNull
    public String getYear() {
        return Year;
    }

    public void setYear(@NonNull String year) {
        this.Year = year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        this.Rated = rated;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        this.Released = released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        this.Runtime = runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        this.Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        this.Director = director;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        this.Writer = writer;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        this.Actors = actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        this.Plot = plot;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        this.Poster = poster;
    }

    @NonNull
    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(@NonNull String imdbID) {
        this.imdbID = imdbID;
    }

    @NonNull
    public String getType() {
        return Type;
    }

    public void setType(@NonNull String type) {
        this.Type = type;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    public static TitleRecord fromJson(String json) {
        return gson.fromJson(json, TitleRecord.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TitleRecord that = (TitleRecord) o;

        if (!Title.equals(that.Title)) return false;
        if (!Year.equals(that.Year)) return false;
        if (Rated != null ? !Rated.equals(that.Rated) : that.Rated != null) return false;
        if (Released != null ? !Released.equals(that.Released) : that.Released != null) return false;
        if (Runtime != null ? !Runtime.equals(that.Runtime) : that.Runtime != null) return false;
        if (Genre != null ? !Genre.equals(that.Genre) : that.Genre != null) return false;
        if (Director != null ? !Director.equals(that.Director) : that.Director != null) return false;
        if (Writer != null ? !Writer.equals(that.Writer) : that.Writer != null) return false;
        if (Actors != null ? !Actors.equals(that.Actors) : that.Actors != null) return false;
        if (Plot != null ? !Plot.equals(that.Plot) : that.Plot != null) return false;
        if (Poster != null ? !Poster.equals(that.Poster) : that.Poster != null) return false;
        if (!imdbID.equals(that.imdbID)) return false;
        return Type.equals(that.Type);

    }

    @Override
    public int hashCode() {
        int result = Title.hashCode();
        result = 31 * result + Year.hashCode();
        result = 31 * result + (Rated != null ? Rated.hashCode() : 0);
        result = 31 * result + (Released != null ? Released.hashCode() : 0);
        result = 31 * result + (Runtime != null ? Runtime.hashCode() : 0);
        result = 31 * result + (Genre != null ? Genre.hashCode() : 0);
        result = 31 * result + (Director != null ? Director.hashCode() : 0);
        result = 31 * result + (Writer != null ? Writer.hashCode() : 0);
        result = 31 * result + (Actors != null ? Actors.hashCode() : 0);
        result = 31 * result + (Plot != null ? Plot.hashCode() : 0);
        result = 31 * result + (Poster != null ? Poster.hashCode() : 0);
        result = 31 * result + imdbID.hashCode();
        result = 31 * result + Type.hashCode();
        return result;
    }
}
