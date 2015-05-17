package edu.sjsu.cmpe.cache.client;

public class Client {

	
	//Consistent Hashing IMplementation call
	
    static ConsistentHashing conHash = new ConsistentHashing();
    private final static  String Server1  =  "http://localhost:3000";
    private final static  String Server2  =  "http://localhost:3001";
    private final static  String Server3  =  "http://localhost:3002";
    
    public static void main(String[] args) throws Exception {
    	System.out.println("Client.main():: Adding  using Chronicle Map");
        System.out.println("\nInitiaating  Cache Client...");
        DistributedCacheService cacheA = new DistributedCacheService(Server1);
        DistributedCacheService cacheB = new DistributedCacheService(Server2);
        DistributedCacheService cacheC = new DistributedCacheService(Server3);

        conHash.add(cacheA);
        conHash.add(cacheB);
        conHash.add(cacheC);

        CacheServiceInterface bucket;
        String[] values = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};

        System.out.println("\n--PUT--");
        for (int k = 1; k <= 10; k++) {
            bucket = conHash.getBucket(Integer.toString(k));
            System.out.println(values[k - 1] + " =========== ServerAddress: " + (bucket.getServerURL()));
            bucket.put(Integer.toUnsignedLong(k), values[k - 1]);

        }

        System.out.println("\n--GET--");
        for (int k = 1; k <= 10; k++) {
            bucket = conHash.getBucket(Integer.toString(k));
            System.out.println("Server: " + (bucket.getServerURL()) + " =========> " + values[k - 1]);
        }
        System.out.println("\nExiting ...");
    }
}
