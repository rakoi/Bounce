package www.limo.com.mymovies.entities;

import com.google.gson.annotations.SerializedName;

import java.util.*;

public class Result {
    private long voteCount;
    private long id;
    private boolean video;
    @SerializedName("vote_average")
    private double voteAverage;
    private String title;
    private double popularity;
    @SerializedName("poster_path")
    private String posterPath;
    private OriginalLanguage originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("genre_ids")
    private long[] genreIDS;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private boolean adult;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

    public long getVoteCount() { return voteCount; }
    public void setVoteCount(long value) { this.voteCount = value; }

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public boolean getVideo() { return video; }
    public void setVideo(boolean value) { this.video = value; }

    public double getVoteAverage() { return voteAverage; }
    public void setVoteAverage(double value) { this.voteAverage = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public double getPopularity() { return popularity; }
    public void setPopularity(double value) { this.popularity = value; }

    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String value) { this.posterPath = value; }

    public OriginalLanguage getOriginalLanguage() { return originalLanguage; }
    public void setOriginalLanguage(OriginalLanguage value) { this.originalLanguage = value; }

    public String getOriginalTitle() { return originalTitle; }
    public void setOriginalTitle(String value) { this.originalTitle = value; }

    public long[] getGenreIDS() { return genreIDS; }
    public void setGenreIDS(long[] value) { this.genreIDS = value; }

    public String getBackdropPath() { return backdropPath; }
    public void setBackdropPath(String value) { this.backdropPath = value; }

    public boolean getAdult() { return adult; }
    public void setAdult(boolean value) { this.adult = value; }

    public String getOverview() { return overview; }
    public void setOverview(String value) { this.overview = value; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String value) { this.releaseDate = value; }
}
