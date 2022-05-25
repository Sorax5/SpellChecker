package fr.soraxdubbing.spellchecker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class SpellChecker extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        System.out.println("[SpellChecker] Plugin is loading...");
        this.getDataFolder().mkdir();

        System.out.println("[SpellChecker] MagicSpells directory is loading...");
        File directory = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getName(),"").replace("\\","/")+"MagicSpells");

        System.out.println("[SpellChecker] Spell file is loading...");
        File configFile = new File(this.getDataFolder().getPath().replace("\\","/") +"/spell.txt");


        System.out.println("[SpellChecker] Done !"+ directory.getPath());

        try {
            configFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!directory.exists()) {
            directory.mkdir();
        }

        long start = System.currentTimeMillis();
        String[] content = directory.list();
        List<File> magicFiles = new ArrayList<File>();
        for (String s : content) {
            File filetoAdd = new File(directory +"/"+ s);
            if (!filetoAdd.isDirectory() && filetoAdd.getName().contains("spells")) {
                magicFiles.add(filetoAdd);
            }
        }
        System.out.println("[SpellChecker] " + magicFiles.size() + " spells files found");
        System.out.println("[SpellChecker] MagicSpells files are loading...");

        try {
            PrintWriter pw = new PrintWriter(configFile);
            int count = 0;
            for (File f : magicFiles) {
                try {
                    String line = "----------------------------- "+ f.getName() + " -----------------------------";
                    pw.println(line);
                    count += SpellManager.parcoursFile(f, configFile,pw);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            long end = System.currentTimeMillis();

            pw.println("----------------------------- RESUME -----------------------------");
            pw.println("Total file : " + magicFiles.size());
            pw.println("Total spells : " + count);
            pw.println("Time processe : " + (end - start) + " ms");
            pw.println("----------------------------- END -----------------------------");
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        System.out.println("[SpellChecker] Done !");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
