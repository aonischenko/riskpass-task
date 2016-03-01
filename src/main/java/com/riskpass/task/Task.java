package com.riskpass.task;

import com.riskpass.task.config.Config;
import com.riskpass.task.services.ExtractionService;

public class Task {
  public static void main(final String[] args) {

    // Checks for arguments added
    if (args.length < 1) {
      System.out.println("ERROR : Nothing to process. Please specify input parameter.");
    } else if (args.length > 1) {
      System.out.println("ERROR : Too many input parameters.");
    } else {

      //TODO get separators regex from command line
      Config.Modifiable config = new Config.Modifiable(";", "\"");

      ExtractionService extractionService = new ExtractionService(config);
      extractionService.extract(args[0]).forEach(System.out::println);
    }
  }
}
