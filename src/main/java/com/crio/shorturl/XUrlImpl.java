package com.crio.shorturl;

import java.util.HashMap;

class XUrlImpl implements XUrl {

    // shortURL -> longURL
    private HashMap<String, String> shortToLongUrlMap = new HashMap<>();
    // longURL -> shortURL 
    private HashMap<String, String> longToShortUrlMap = new HashMap<>();
    // Hit count
    private HashMap<String, Integer> hitCount = new HashMap<>();

    // If longUrl already has a corresponding shortUrl, return that shortUrl
    // If longUrl is new, create a new shortUrl for the longUrl and return it
    @Override
    public String registerNewUrl(String longUrl) {
        // If longUrl already has a corresponding shortUrl, return that shortUrl
        if(longToShortUrlMap.containsKey(longUrl)) return longToShortUrlMap.get(longUrl);

        String shortUrl = "";
        // If longUrl is new, create a new shortUrl for the longUrl and return it
        // keys should not be duplicate
        while(!shortToLongUrlMap.containsKey(shortUrl)) {

            // reset shortUrl for while loop logic
            shortUrl = "http://short.url/";

            // where xxxxxxxxx is a unique alphanumeric string which is 9 characters in length. 
            String shortURLKey = generateKey();

            // the short URL should be http://short.url/xxxxxxxxx, 
            shortUrl += shortURLKey;
            // add to maps
            shortToLongUrlMap.put(shortUrl, longUrl);
            

        }
        // add to maps
        // shortToLongUrlMap.put(shortUrl, longUrl);
        longToShortUrlMap.put(longUrl, shortUrl);

        return shortUrl;
    }

    // If shortUrl is already present, return null
    // Else, register the specified shortUrl for the given longUrl
    // Note: You don't need to validate if longUrl is already present, 
    //       assume it is always new i.e. it hasn't been seen before 
    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if(shortToLongUrlMap.containsKey(shortUrl)) { 
            return null;
        }
        shortToLongUrlMap.put(shortUrl, longUrl);
        longToShortUrlMap.put(longUrl, shortUrl);
        return shortUrl;
    }

    // If shortUrl doesn't have a corresponding longUrl, return null
    // Else, return the corresponding longUrl
    @Override
    public String getUrl(String shortUrl) {
        // System.out.println("In GetUrl");
        if(shortToLongUrlMap.containsKey(shortUrl) == false) return null;
        String longUrl = shortToLongUrlMap.get(shortUrl);
        // update hit count 
        // increment count
        hitCount.put(longUrl, hitCount.getOrDefault(longUrl, 0) + 1);
        // System.out.println("hit "+hitCount.toString());
        return longUrl;
    }

    // Return the number of times the longUrl has been looked up using getUrl()
    @Override
    public Integer getHitCount(String longUrl) {
        return hitCount.getOrDefault(longUrl, 0);
        // return null;
    }

    // Delete the mapping between this longUrl and its corresponding shortUrl
    // Do not zero the Hit Count for this longUrl
    @Override
    public String delete(String longUrl) {
        // remove the reference from the both maps
        String shortUrl = longToShortUrlMap.get(longUrl);
        shortToLongUrlMap.remove(shortUrl);
        return longToShortUrlMap.remove(longUrl);
    }


    // the short URL should be http://short.url/xxxxxxxxx, 
    // where xxxxxxxxx is a unique alphanumeric string which is 9 characters in length. 
    String generateKey() {
        String base62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvwxyz";
        StringBuilder shortURLKey = new StringBuilder();
        for(int i=0; i<9; i++) {
            int index = (int) (Math.random() * 62);
            char ch = base62.charAt(index);
            shortURLKey.append(ch);
        }
        return shortURLKey.toString();
    }
}