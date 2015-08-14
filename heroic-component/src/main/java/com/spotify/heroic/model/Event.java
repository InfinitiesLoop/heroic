/*
 * Copyright (c) 2015 Spotify AB.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.spotify.heroic.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Comparator;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

@Data
@AllArgsConstructor
public class Event implements TimeData {
    private static final Map<String, Object> EMPTY_PAYLOAD = ImmutableMap.of();

    private final long timestamp;
    private final Map<String, Object> payload;

    public Event(long timestamp) {
        this(timestamp, EMPTY_PAYLOAD);
    }

    @JsonCreator
    public Event(@JsonProperty("timestamp") Long timestamp, @JsonProperty("payload") Map<String, Object> payload) {
        this.timestamp = checkNotNull(timestamp, "timestamp");
        this.payload = Optional.fromNullable(payload).or(EMPTY_PAYLOAD);
    }

    @Override
    public int compareTo(TimeData o) {
        return Long.compare(timestamp, o.getTimestamp());
    }

    @Override
    public int hash() {
        return payload.hashCode();
    }

    public boolean valid() {
        return true;
    }

    private static final Comparator<TimeData> comparator = new Comparator<TimeData>() {
        @Override
        public int compare(TimeData a, TimeData b) {
            return Long.compare(a.getTimestamp(), b.getTimestamp());
        }
    };

    public static Comparator<TimeData> comparator() {
        return comparator;
    }
}