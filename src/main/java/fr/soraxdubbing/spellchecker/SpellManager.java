package fr.soraxdubbing.spellchecker;

import java.io.*;

public class SpellManager {


    public static int parcoursFile(File fileRead,File fileWrite,PrintWriter pw) throws IOException {
        InputStream is = new FileInputStream(fileRead);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            if(!line.contains(" ")){
                count++;
                pw.println(line);
            }
        }
        is.close();
        br.close();
        return count;
    }
}
