package com.example.vineetpatil.moviesearch;

import com.google.gson.Gson;

import java.util.List;

public class SearchResponse {
    private static final Gson gson = new Gson();

    private List<SearchResult> Search;
    private int totalResults;
    private boolean Response;

    public List<SearchResult> getSearch() {
        return Search;
    }

    public void setSearch(List<SearchResult> search) {
        this.Search = search;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public boolean isResponse() {
        return Response;
    }

    public void setResponse(boolean response) {
        this.Response = response;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    public static class SearchResult {
        private String Title;
        private String Year;
        private String imdbID;
        private String Type;
        private String Poster;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            this.Title = title;
        }

        public String getYear() {
            return Year;
        }

        public void setYear(String year) {
            this.Year = year;
        }

        public String getImdbID() {
            return imdbID;
        }

        public void setImdbID(String imdbID) {
            this.imdbID = imdbID;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            this.Type = type;
        }

        public String getPoster() {
            return Poster;
        }

        public void setPoster(String poster) {
            this.Poster = poster;
        }

        @Override
        public String toString() {
            return gson.toJson(this);
        }
    }
}
