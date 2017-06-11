package com.omertron.imdbapi.request;

import java.io.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by JiachenWang on 2017/6/8.
 */
public class ForFile {


    private final static String path = "D:\\ids.txt";

    public static boolean writeFileContent(Set<String> idSet) throws IOException{
        File file = new File(path);
        FileWriter fw = null;
        BufferedWriter writer = null;
        Iterator iterator = idSet.iterator();
        try {
            fw = new FileWriter(file, true);
            writer = new BufferedWriter(fw);
            while(iterator.hasNext()){
                writer.write(iterator.next().toString());
                writer.newLine();//换行
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                writer.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
