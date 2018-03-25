package yelpReviewHashing;

import org.json.*;
import java.io.*;
import java.util.*;

public class Parser {

    static void parse(String file, HashTable ht) throws IOException {
        //Create file reader
        BufferedReader bufRead = new BufferedReader(new FileReader(file));
        String line;

        //Run infinite loop
        while (true) {
            //Read line of file
            line = bufRead.readLine();

            //If line is not empty
            if (line != null) {
                //New json object
                org.json.JSONObject jsonObject = new org.json.JSONObject(line);

                //Pull relevant data from json line
                Business business = new Business(jsonObject.get("business_id").toString(), jsonObject.get("name").toString(),
                        jsonObject.get("city").toString(), jsonObject.get("state").toString(), jsonObject.getFloat("stars"));

                //Create list of categories from file
                ArrayList<String> categoryCollection = new ArrayList<String>();
                JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("categories").toString());

                //Add categories from json to category list in business object
                for (int j = 0; j < jsonArray.length(); j++) {
                    categoryCollection.add(jsonArray.get(j).toString());
                }
                business.categories.addAll(categoryCollection);

                //Add business to hashtable
                ht.put(business);
                //if line is empty then EOF reached, let user know and break loop
            } else {
                System.out.println("Done");
                break;
            }


        }


    }
}


