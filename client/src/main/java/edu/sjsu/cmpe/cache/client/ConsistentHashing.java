package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.Hashing;

import java.util.SortedMap;
import java.util.TreeMap;
/**
 * Authored By SAI AISHWARYA
 * This class contains methods that implements Consistent Hashing
 */
public class ConsistentHashing {

    private final SortedMap<Integer, CacheServiceInterface> circularToken  = new TreeMap<Integer, CacheServiceInterface>();

    public void add(DistributedCacheService node) {
        int key = getHash(node.cacheServerUrl);
        circularToken.put(key, node);
    }

    public CacheServiceInterface getBucket(String key) {
        if (circularToken.isEmpty()) {
            return null;
        }
        int hash = getHash(key);
        if (!circularToken.containsKey(hash)) {
            SortedMap<Integer, CacheServiceInterface> tailMap = circularToken.tailMap(hash);
            hash = tailMap.isEmpty() ? circularToken.firstKey() : tailMap.firstKey();
        }
        return circularToken.get(hash);
    }

    public int getHash(String key) {
        int number;
        number = Hashing.murmur3_128().newHasher().putString(key).hash().asInt();
        return number;
    }

}