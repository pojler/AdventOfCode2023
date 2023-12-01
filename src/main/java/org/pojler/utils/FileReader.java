package org.pojler.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

}
