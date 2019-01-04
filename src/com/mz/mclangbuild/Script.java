package com.mz.mclangbuild;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class Script {
  private ArrayList<String> locales;
  private HashMap<String, TreeMap<String, String>> localeToEntries;

  private String group;
  private String suffix;
  private String name;
  private String fullName;
  private int enumIndex;
  private int langIndex;
  private int lineCount;

  private int mode;
  private static final int SCRIPT = 0, GROUP = 1, SUFFIX = 2, NAME = 3, NAMEENUM = 4, PROCESSING_NAME = 5,
      PROCESSING_NAMEENUM = 6;

  private File scriptLocation;

  /**
   * parse from file
   * 
   * @param file
   * @throws FileNotFoundException
   */
  public Script(File file) throws IOException {
    scriptLocation = file;
    inspect();
  }

  private void inspect() throws FileNotFoundException {
    Scanner scan = new Scanner(scriptLocation, "UTF-8");
    lineCount = 0;
    while (scan.hasNextLine()) {
      lineCount++;
      System.out.println(lineCount + " " + scan.nextLine());
    }
    scan.close();
  }

  public void save() throws IOException {
    File root = scriptLocation.getParentFile();
    for (String locale : locales) {
      File f = root.getAbsoluteFile().toPath().resolve(locale + ".lang").toFile();
      if (!f.exists()) {
        f.createNewFile();
      }
      PrintWriter pw = new PrintWriter(f, "UTF-8");
      pw.println("#Auto-Generated By iTNTPiston");
      TreeMap<String, String> entries = localeToEntries.get(locale);
      for (Entry<String, String> e : entries.entrySet()) {
        pw.println(e.getKey() + "=" + e.getValue());
      }
      pw.flush();
      pw.close();
    }
  }

  public void execute() throws Exception {

    Scanner scan = new Scanner(scriptLocation, "UTF-8");
    lineCount = 0;
    try {
      // find head
      lineCount++;
      String head = scan.nextLine();
      while (!head.startsWith("#SCRIPT")) {
        if (!scan.hasNextLine()) {
          System.out.println("Fail to read script head!");
          System.out.println("Expecting #SCRIPT");
          throw new Exception("Execution Error on Line " + lineCount);
        }
        head = scan.nextLine();
      }
      locales = new ArrayList<String>();
      localeToEntries = new HashMap<String, TreeMap<String, String>>();
      lineCount++;

      String locale = scan.nextLine();
      while (locale.length() > 0) {
        locales.add(locale);
        localeToEntries.put(locale, new TreeMap<String, String>());
        lineCount++;
        locale = scan.nextLine();
      }
      group = "";
      name = "";
      suffix = "";
      fullName = "";
      enumIndex = 0;
      langIndex = 0;
      mode = SCRIPT;
      while (scan.hasNextLine()) {
        lineCount++;
        String nextLine = scan.nextLine();
        if (nextLine.startsWith("#END"))
          break;
        interpretLine(trimStartTabs(nextLine));
      }
    } finally {
      scan.close();
    }
  }

  private String trimStartTabs(String line) {
    for (int i = 0; i < line.length(); i++) {
      if (line.charAt(i) == '\u0009' || line.charAt(i) == ' ')
        continue;
      return line.substring(i);
    }
    return "";
  }

  private String trimEndSpaces(String line) {
    for (int i = line.length() - 1; i >= 0; i--) {
      if (line.charAt(i) == ' ')
        continue;
      return line.substring(0, i + 1);
    }
    return "";
  }

  private void interpretLine(String line) {
    switch (mode) {
    case SCRIPT:
      interpretScript(line);
      return;
    case GROUP:
      group = appendToGroup(group, trimEndSpaces(line));
      mode = SCRIPT;
      return;
    case SUFFIX:
      suffix = trimEndSpaces(line);
      mode = SCRIPT;
      return;
    case NAME:
      name = trimEndSpaces(line);
      mode = PROCESSING_NAME;
      fullName = appendToGroup(group, name + suffix);
      langIndex = 0;
      return;
    case PROCESSING_NAME:
      if (line.length() == 0 || langIndex == locales.size()) {
        mode = SCRIPT;
      } else {
        String locale = locales.get(langIndex);
        System.out.println(locale + ": " + fullName + "=" + line);
        localeToEntries.get(locale).put(fullName, line);
        langIndex++;
      }
      return;
    case NAMEENUM:
      name = trimEndSpaces(line);
      mode = PROCESSING_NAMEENUM;
      fullName = appendToGroup(group, name + suffix);
      langIndex = 0;
      enumIndex = 0;
      return;
    case PROCESSING_NAMEENUM:
      if (line.length() == 0 || langIndex == locales.size()) {
        if (langIndex >= locales.size() - 1) {
          mode = SCRIPT;
        } else {
          enumIndex = 0;
          langIndex++;
        }
      } else {
        String entryName = fullName + enumIndex;
        String locale = locales.get(langIndex);
        System.out.println(locale + ": " + entryName + "=" + line);
        localeToEntries.get(locale).put(entryName, line);
        enumIndex++;
      }
    }
    return;
  }

  private void interpretScript(String line) {
    if (line.startsWith("#GROUP")) {
      mode = GROUP;
    } else if (line.startsWith("#SUFFIX")) {
      mode = SUFFIX;
    } else if (line.startsWith("#NAMEENUM")) {
      mode = NAMEENUM;
    } else if (line.startsWith("#NAME")) {
      mode = NAME;
    } else if (line.startsWith("#RETURNALL")) {
      mode = SCRIPT;
      group = "";
    } else if (line.startsWith("#RETURN")) {
      mode = SCRIPT;
      int i = group.lastIndexOf('.');
      if (i == -1)
        group = "";
      else
        group = group.substring(0, i);
    }
  }

  private static String appendToGroup(String group, String name) {
    if (group.length() == 0)
      return name;
    else
      return group + "." + name;
  }
}