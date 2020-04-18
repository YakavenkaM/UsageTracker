package com.company;

import java.util.*;

/**
 * Written by Mikhail Yakavenka on 04/18/2020 for a test assignment given by CUNY Tech Prep
 * Logs are given in no particular order.
 * Write a function that returns a 'STRING_ARRAY', denoting user IDs of suspicious users
 * who were involved in at least 'threshold' number of log entries.
 */
public class Main {

    /**
     * A Log file is provided as a string array.
     * Each entry represents a money transfer in form: senderId, userId, amount
     * Each of the values are separated by a space.
     **/
    public static void main(String[] args) {
        List<String> logsList = new ArrayList<>();

        int threshold = 2;
        // Log entries are separated by spaces and contain senderId , recipientId, amount is this order
        // all values must be digits, not start with 0 and be at most 9 digits long
        // Following exhausts all of the required test cases
        logsList.add("676000000 23BB 13");
        logsList.add("676000000 BB 12");
        logsList.add("6760000000 99 14");
        logsList.add("6760000000 22 99");
        logsList.add("33 99 14");
        logsList.add("947 22 99");
        logsList.add("947 22 99");
        logsList.add("22 22 99");

        //find fraudulent activity
        List<String> logsListProcessed = processLogs(logsList, threshold);

        //Display threshold entries
        System.out.println("\n============ Suspicious User IDs =============");
        for (String id : logsListProcessed) {
            System.out.println("\t\tEntries in scending order: " + id);
        }
    }

    /**
     * Process all logs, create individual string entries "logEntry"
     * Create a map that returns id and count of unique id's
     * Return an sorted in ascending order STRING_ARRAY for id's that are at least at threshold value
     **/
    private static List<String> processLogs(List<String> logs, int threshold) {
        List<String> STRING_ARRAY = new ArrayList<>(); // final sorted list
        Map<Integer, Integer> map = new HashMap<>(); //Map used to count number of unique ID's
        List<Integer> intListToSort = new ArrayList<>(); // temporary integer list
        for (String logEntry : logs) {
            String[] listParsed = logEntry.split(" "); // parse out sender_id and recipient_id
            String sId = listParsed[0]; // senderId as a string to perform checks
            String rId = listParsed[1]; // recipientId as a string to perform checks
            int senderId = 0;
            int recipientId;

            //cannot start with 0 or be longer than 9 digits
            if (sId.charAt(0) != '0' && sId.length() <= 9) {
                try { // try parsing since only digits are allowed
                    senderId = Integer.parseInt(sId);
                    map.putIfAbsent(senderId, 0); // add first entry for sender
                    map.put(senderId, map.get(senderId) + 1); // increment existing sender
                } catch (NumberFormatException e) {
                    System.out.println("parseInt(sId) exception: " + e);
                }
            }

            //cannot start with 0 or be longer than 9 digits
            if (rId.charAt(0) != '0' && rId.length() <= 9) {
                try {  // try parsing since only digits are allowed
                    recipientId = Integer.parseInt(rId);
                    map.putIfAbsent(recipientId, 0); // add first entry for recipient
                    // if senderId == recipientId in same log entry, dont increment for same ID
                    if (recipientId != senderId) {
                        map.put(recipientId, map.get(recipientId) + 1);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("parseInt(rId) Got an exception: " + e);
                }
            }
        }

        map.forEach((key, value) -> {
            if (value >= threshold) {
                intListToSort.add(key);
                System.out.println("\n======= <Users with at least threshold of " + threshold + " entries> ========");
                System.out.println("\tUser :" + key + "\n\tNumber of entries: " + value);
                System.out.println("=============================================================\n");
            }
        });

        // sort Integer list, as Collections does not sort string by digit values
        Collections.sort(intListToSort);
        for (int id : intListToSort) { // now add them to the list that assignment required to return
            STRING_ARRAY.add(String.valueOf(id)); // add everything to return array converting id's to strings
        }

        return STRING_ARRAY;
    }
}


