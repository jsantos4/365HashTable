package yelpReviewHashing;

import java.util.ArrayList;

public class HashTable {
    //define node for linked lists in table
    static final class node {
        String key;
        Business value;
        node next;
        node(String key, Business value, node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    //local variables
    private static node[] table;
    private static final int initialcap = 4;
    private double count;

    HashTable() {
        table = new node[initialcap];
    }

    //Hashes business names
    private int hash(String k, int size) {
        int sum = 0;
        for(int i = 0; i < k.length(); i++) {   //Run through each character in name(key)

            char a = k.charAt(i);
            int ascii = (int) a;        //Get ascii value and sum up
            sum = sum + ascii;
        }
        return sum % size;          //Return modulo of ascii total of key
    }

    //Inserts business objects into table
    public void put(Business b) {
        String key = b.getName();
        int hashcode = hash(key, table.length);     //Hash key
        int i = hashcode & table.length - 1;
        for (node e = table[i]; e != null; e = e.next) {    //Run through nodes in index until the end
            if (key.equals(e.key)) {
                e.value = b;    //Set node's value
                count++;        //Increment hashtable counter
                return;
            }
        }
        node p = new node(key, b, table[i]);    //Create node at index if empty
        table[i] = p;
        count++;

        if ((count / table.length) >= 0.75) {   //If table is 75% full
            resize();                           //Call resize()
        }
    }

    //Returns all keys
    public ArrayList<String> getAll(ArrayList list) {
        for (int i = 0; i < table.length; i++) {
            for (node e = table[i]; e != null; e = e.next) {    //Run through whole table
                list.add(e.key);        //Insert node into arraylist
            }
        }
        return list;    //Return arraylist
    }

    //Returns if element in table is empty
    private boolean isEmpty(node e) {
        return e == null;
    }

    //Returns value of a searched key
    private Business search(String k) {
        int hashcode = hash(k, table.length);
        int i = hashcode & table.length - 1;    //Hash key
        for (node e = table[i]; !isEmpty(e); e = e.next) {
            if (k.equals(e.key)) {
                return e.value;         //Run through index's nodes and return searched business
            }
        }
        return null;
    }

    public ArrayList<String> getSimilar(String key) {
        Business business = search(key);
        ArrayList<String> similarBusinesses = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            for (HashTable.node e = table[i]; e != null; e = e.next) {      //Run through table
                if (business.getCity().equals(e.value.getCity()) && !business.getName().equals(e.value.getName())) {    //If node value's city equals searched businesses city
                    e.value.similarCategories = 0;
                    for (int k = 0; k < e.value.categories.size(); k++) {   //Run through node value's categories
                        if (business.categories.contains(e.value.categories.get(k))) {  //If searched businesses categories contains node value's category
                            e.value.similarCategories++;       //Increment similar category counter
                        }
                    }
                }
                if (e.value.similarCategories > 2 && e.value.getStars() > 3.5 ) {   //If node value has at least three categories in common with searched business and is at least 4 stars
                    e.value.similarCategories = 0;      //Reset similar category counter
                    similarBusinesses.add(e.value.toString());  //Add node value to similar business list
                }
            }
        }

        if (similarBusinesses.isEmpty()) {      //If no similar businesses are found, return "None found" message
            similarBusinesses.add("No similar businesses found in the area.");
        }

        return similarBusinesses;
    }

    //doubles table size when three quarters load is reached
    private void resize() {
        node[] newTable = new node[table.length*2];     //Create new table with twice size of original
        //Can't use put, essentially had to rewrite and tweak
        //the put method to remap all elements of the old table
        for (int i = 0; i < table.length; i++) {
            for (node e = table[i]; e != null; e = e.next) {        //Run through table
                int hc = hash(e.key, newTable.length);      //Rehash node
                if (newTable[hc] == null){          //If index is empty
                    node p = new node(e.key, e.value, newTable[hc]);
                    newTable[hc] = p;           //Place node in index
                } else {        //If not
                    node f = newTable[hc];
                    while(!isEmpty(f)) {
                        f = f.next;     //Run through nodes(bucket)
                    }
                    node p = new node(e.key, e.value, f);       //Place node at end of bucket
                    f = p;
                }
            }
        }
        table = newTable;       //Set table to new table

    }
}

