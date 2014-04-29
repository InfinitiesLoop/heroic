package com.spotify.heroic.cache.model;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

import com.spotify.heroic.model.DataPoint;
import com.spotify.heroic.model.TimeSerieSlice;

@ToString(of = { "result", "misses" })
public class CacheQueryResult {
    /**
     * Collected results so far. Should be joined by the result from the above
     * cache misses.
     */
    @Getter
    private final List<DataPoint> result;

    /**
     * Cache misses that has to be queried and aggregated from raw storage.
     */
    @Getter
    private final List<TimeSerieSlice> misses;

    public CacheQueryResult(List<DataPoint> result,
            List<TimeSerieSlice> misses) {
        this.result = result;
        this.misses = misses;
    }
}