package com.riskpass.task.config;

import java.util.regex.Pattern;

public class Config {

  public static final String DEFAULT_COMMA_REGEX = ",";
  public static final String DEFAULT_QUOTE_REGEX = "\"";

  // Fields initialization moved into the constructor
  Pattern commaPattern;
  Pattern nextQuotePattern;
  Pattern firstQuotePattern;
  Pattern lastQuotePattern;

  String commaRegex;
  String quoteRegex;

  public Config() {
    this(Config.DEFAULT_COMMA_REGEX, Config.DEFAULT_QUOTE_REGEX);
  }

  public Config(final String commaRegex, final String quoteRegex) {
    this.commaRegex = commaRegex != null && !commaRegex.isEmpty() ? commaRegex : DEFAULT_COMMA_REGEX;
    this.quoteRegex = quoteRegex != null && !quoteRegex.isEmpty() ? quoteRegex : DEFAULT_QUOTE_REGEX;

    this.updatePatterns();
  }

  public Pattern commaPattern() {
    return this.commaPattern;
  }

  public Pattern nextQuotePattern() {
    return this.nextQuotePattern;
  }

  public Pattern firstQuotePattern() {
    return firstQuotePattern;
  }

  public Pattern lastQuotePattern() {
    return this.lastQuotePattern;
  }

  public String commaRegex() {
    return commaRegex;
  }

  public String quoteRegex() {
    return quoteRegex;
  }

  void updatePatterns() {
    this.commaPattern = Pattern.compile(this.commaRegex);
    this.nextQuotePattern = Pattern.compile(String.format("%s\\s*,", this.quoteRegex));
    this.firstQuotePattern = Pattern.compile(String.format("^%s", this.quoteRegex));
    this.lastQuotePattern = Pattern.compile(String.format("%s$", this.quoteRegex));
  }

  //TODO Builder pattern is more suitable for this class
  public static class Modifiable extends Config {

    public Modifiable() {
      super();
    }

    public Modifiable(String commaRegex, String quoteRegex) {
      super(commaRegex, quoteRegex);
    }

    public void commaRegex(String commaRegex) {
      if (commaRegex == null || commaRegex.isEmpty()) {
        throw new IllegalArgumentException("Comma regex invalid.");
      }
      this.commaRegex = commaRegex;
      this.updatePatterns();
    }

    public void quoteRegex(String quoteRegex) {
      if (quoteRegex == null || quoteRegex.isEmpty()) {
        throw new IllegalArgumentException("Quote regex invalid.");
      }
      this.quoteRegex = quoteRegex;
      this.updatePatterns();
    }
  }
}
