package com.mz.mclangbuild;

import java.io.File;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    System.out.println("Lang Generating Tool v1.0 By iTNTPiston");
    String fileName = args.length > 0 ? args[0] : "buildscript.txt";
    File root = new File(fileName).getAbsoluteFile();
    System.out.println("Loading Script: " + root.getAbsolutePath());
    Script s = new Script(root);
    System.out.println("Script Loaded.");
    System.out.println("Executing Script...");
    try {
      s.execute();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println();
      System.out.println("Failed!");
      System.out.println();
      return;
    }
    System.out.println("Generating...");
    s.save();
    System.out.println();
    System.out.println("Successful!");
    System.out.println();
  }
}
