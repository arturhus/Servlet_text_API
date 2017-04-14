package ua.lviv.art;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by artur on 2.04.2017.
 */

public class Reader {

    public List<String> read(String q, String len, String lim) throws IOException {
        int limit = limitValidator(lim);
        int length = lengthValidator(len);
        String query = queryValidator(q);

        List<String> list = new ArrayList<>();
        if (!query.isEmpty()) {
            list = readByLines(list, query, limit);
            return searchByQuery(list, length, query, limit);
        } else {
            return readByLines(list, query, limit);
        }
    }

    public List<String> readByLines(List<String> list, String query, int lim) throws IOException {
        int charCounter = 0;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testfile.txt");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            if (!query.isEmpty()) {
                while ((line = in.readLine()) != null && charCounter <= lim) {
                    if (line.contains(query))
                        list.add(line + "\n");
                    charCounter = +line.length();
                }
            } else {
                while ((line = in.readLine()) != null) {
                    list.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int limitValidator(String limit) {
        if (limit.matches("^-?\\d+$"))
            return Integer.valueOf(limit);
        else
            return 10000;
    }

    public int lengthValidator(String length) {
        if (length.matches("^-?\\d+$"))
            return Integer.valueOf(length);
        else
            return 0;
    }

    public String queryValidator(String query) {
        if (query.trim().matches("(?i).*[a-zа-я].*")) {
            return query;
        } else
            return "";
    }

//    public List<String> fromLinesToWords(List<String> lines) {
//        String[] wordsArray;
//        List<String> wordsList = new ArrayList<>();
//        for (String line : lines) {
//            wordsArray = line.split(" ");
//            Collections.addAll(wordsList, wordsArray);
//        }
//        return wordsList;
//    }

    public List<String> searchByQuery(List<String> words, Integer length, String query, Integer limit) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (word.contains(query)) {
                if (length == 0 || word.length() <= length) {
                    result.add(word);
                } else {
                    result.add(word.substring(0, length));
                }
            }
        }
        if (result.isEmpty()) {
            result.add("There is no |" + query + "| in first " + limit + " symbols of file");
        }
        return result;
    }
}