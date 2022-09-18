package com.example.demo.util;

import java.time.Instant;
import java.util.Date;
import java.util.*;



public class RateLimiter {

    public static class Pair {
        String key;
        long value;
        public Pair(String key, long value) {
            this.key = key;
            this.value = value;
        }
    }

    Deque<Pair> rateQ;
    Set<String> userSet;

    final long rateInSec = 10;

    private static RateLimiter rateLimiter = null;

    public static RateLimiter getInstance() {
        if (rateLimiter==null) {
            rateLimiter = new RateLimiter();
        }
        return rateLimiter;
    }

    private RateLimiter() {
        rateQ = new ArrayDeque<>();
        userSet = new HashSet<>();
    }

    public boolean rateLimit(String user) {
        long currentTime = Instant.now().getEpochSecond();
        while (!rateQ.isEmpty() && rateQ.getFirst().value < currentTime-rateInSec) {
            Pair p = rateQ.removeFirst();
            userSet.remove(p.key);
        }
        if (!userSet.contains(user)) {
            userSet.add(user);
            Pair p = new Pair(user, currentTime);
            rateQ.addLast(p);
            return true;
        } else {
            System.out.println("user reached rate limit");
            return false;
        }
    }
}
