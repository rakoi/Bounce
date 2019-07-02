package www.limo.com.mymovies.entities;

import java.util.*;

public class TMBMovie {
    private long page;
    private long totalResults;
    private long totalPages;
    private Result[] results;

    public long getPage() { return page; }
    public void setPage(long value) { this.page = value; }

    public long getTotalResults() { return totalResults; }
    public void setTotalResults(long value) { this.totalResults = value; }

    public long getTotalPages() { return totalPages; }
    public void setTotalPages(long value) { this.totalPages = value; }

    public Result[] getResults() { return results; }
    public void setResults(Result[] value) { this.results = value; }
}
