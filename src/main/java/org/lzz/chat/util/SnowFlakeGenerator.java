package org.lzz.chat.util;

import org.springframework.stereotype.Component;

@Component
public class SnowFlakeGenerator {

    private static long datacenterId = 0L;
    private static final long datacenterIdBits = 2L;
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    private static long sequenceBits = 8L;
    private static final long sequenceMax = -1L ^ (-1L << sequenceBits);;
    private static final long twepoch = 1440000000000L;

    private long datacenterIdShift = sequenceBits;
    private long timestampLeftShift = sequenceBits + datacenterIdBits;

    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;


    private SnowFlakeGenerator() {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new RuntimeException("datacenterId > maxDatacenterId");
        }
    }

    public synchronized Long generateLongId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) % sequenceMax;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}