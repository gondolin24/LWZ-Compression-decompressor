package Compressor;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class Compressor {


    private Map<String, String> keyTable;


    public Map<String, String> getKeyTable() {
        return keyTable;
    }

    public String CompressString(String stringToCompress) {

        keyTable = new HashMap<>();

        char[] charArray = stringToCompress.toCharArray();
        List<String> characterArray = new ArrayList<>();
        for (char c : charArray) {
            characterArray.add(String.valueOf(c));
        }
        new HashSet<>(characterArray).forEach(current->{
            keyTable.put(String.valueOf(keyTable.size()),current);
        });
        String[] tableValue = {null};
        String[] encodedString = {null};
        tableValue[0] = "";
        characterArray.forEach(character -> {
            String concatValue = tableValue[0] + character;

            if (valueExistsInTable(concatValue)) {
                tableValue[0] = concatValue;
            } else {
                encodedString[0] = encodedString[0] + getEncoded(tableValue[0]);
                keyTable.put(valueOf(keyTable.size()), concatValue);
                tableValue[0] = character;
            }
        });

        return encodedString[0];
    }

      private String getEncoded(String current) {

        //iterative way ... probably better and cleaner..
        //to all the stream haters : @kisunji
        for(Entry<String,String> entrySet:keyTable.entrySet()){
            if(current.equals(entrySet.getValue()))
                return entrySet.getKey();
        }
        return "";
        //Java 8 stream version
       // return  keyTable.entrySet().stream().findFirst().filter(entry -> entry.getValue().equals(current)).map(Entry::getKey).orElse("");
    }


    private boolean valueExistsInTable(String current) {
        return keyTable.size() != 0 && keyTable.values().contains(current);

    }
}
