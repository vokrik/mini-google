/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mini.google;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Martin
 */
public class PageRankDatabase extends Database {
    
    
    void add(){
        
    }
    
    Map<String, Set<String>> reverseIndex(Map<String, Set<String>> index){
      Set<String> keys = index.keySet();
      Map<String, Set<String>> reversedIndex = new HashMap<>();
      for(String key : keys){// Prepare reversed index
            reversedIndex.put(key, new HashSet<>());
        }
      for(String key : keys){// Reverse index
         Set<String> values = index.get(key);
         for(String value : values){
             if(reversedIndex.containsKey(value)){
             reversedIndex.get(value).add(key);
                
             }
         }
        }
      
      return reversedIndex;
    }
}
