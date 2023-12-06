package org.pojler.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public List<String> getLines(String filename) {
        List<String> lines = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());
        try{
            lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
        }
        catch (IOException ex){
            System.err.println("error reading file " + file.getAbsolutePath());
        }

        return lines;
    }

    public char[][] linesAsCharArray (String filename) {
        List<String> lines = getLines(filename);
        char[][] result = new char[lines.size()][lines.get(0).length()];
        for(int i = 0; i < result.length; i++) {
            result[i] = lines.get(i).toCharArray();
        }
        return result;
    }

    public String fileAsString (String filename) {
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            return new Scanner(new File(classLoader.getResource(filename).getFile())).useDelimiter("\\Z").next();
        }
        catch (IOException ex) {

        }
        return "";
    }

}
