package www.limo.com.mymovies.entities;

import java.util.*;

public class Show {
    private long id;
    private String url;
    private String name;
    private String type;
    private String language;
    private String[] genres;
    private String status;
    private long runtime;
    private String premiered;
    private String officialSite;
    private Schedule schedule;
    private Rating rating;
    private long weight;
    private Network network;
    private Object webChannel;
    private Externals externals;
    private Image image;
    private String summary;
    private long updated;
    private ShowLinks links;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getURL() { return url; }
    public void setURL(String value) { this.url = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getType() { return type; }
    public void setType(String value) { this.type = value; }

    public String getLanguage() { return language; }
    public void setLanguage(String value) { this.language = value; }

    public String[] getGenres() { return genres; }
    public void setGenres(String[] value) { this.genres = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public long getRuntime() { return runtime; }
    public void setRuntime(long value) { this.runtime = value; }

    public String getPremiered() { return premiered; }
    public void setPremiered(String value) { this.premiered = value; }

    public String getOfficialSite() { return officialSite; }
    public void setOfficialSite(String value) { this.officialSite = value; }

    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule value) { this.schedule = value; }

    public Rating getRating() { return rating; }
    public void setRating(Rating value) { this.rating = value; }

    public long getWeight() { return weight; }
    public void setWeight(long value) { this.weight = value; }

    public Network getNetwork() { return network; }
    public void setNetwork(Network value) { this.network = value; }

    public Object getWebChannel() { return webChannel; }
    public void setWebChannel(Object value) { this.webChannel = value; }

    public Externals getExternals() { return externals; }
    public void setExternals(Externals value) { this.externals = value; }

    public Image getImage() { return image; }
    public void setImage(Image value) { this.image = value; }

    public String getSummary() { return summary; }
    public void setSummary(String value) { this.summary = value; }

    public long getUpdated() { return updated; }
    public void setUpdated(long value) { this.updated = value; }

    public ShowLinks getLinks() { return links; }
    public void setLinks(ShowLinks value) { this.links = value; }
}
