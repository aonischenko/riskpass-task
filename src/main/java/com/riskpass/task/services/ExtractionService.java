package com.riskpass.task.services;

import com.riskpass.task.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ExtractionService {

  private final Config config;

  // Default constructor added
  public ExtractionService() {
    this(new Config());
  }

  public ExtractionService(final Config config) {
    this.config = config != null ? config : new Config();
  }

  // Field getter placed after constructor
  public Config config() {
    return this.config;
  }

  public List<String> extract(final String line) {
    List<String> values = new ArrayList<>();

    String text = line.trim();
    while (!text.isEmpty()) {

      // Replacing check for double quote with the config quote pattern
      Matcher matcher = this.config().firstQuotePattern().matcher(text);

      if (matcher.find()) {
        // Replaced unquote call to use pattern instead of substring
        String unquotedText = matcher.replaceFirst("");
        text = this.addQuotedCell(unquotedText, values);
      } else {
        text = this.addUnquotedCell(text, values);
      }
    }

    return values;
  }

  private String addQuotedCell(
    final String text,
    final List<String> values
  ) {
    String newText = "";

    // Unquote moved into extract method, since we have the same matcher there
    Matcher matcher = this.config().nextQuotePattern().matcher(text);
    if (matcher.find()) {
      newText = this.addCell(
        text, values,
        matcher.start(), matcher.end(),
        false
      );
    } else {
      this.addLastQuotedCell(text, values);
    }

    return newText;
  }

  // Renamed
  private String addUnquotedCell(
    final String text,
    final List<String> values
  ) {
    String newText = "";

    Matcher matcher = this.config().commaPattern().matcher(text);
    if (matcher.find()) {
      newText = this.addCell(
        text, values,
        matcher.start(), matcher.end(),
        true
      );
    } else {
      this.addLastUnquotedCell(text, values);
    }

    return newText;
  }

  private void addLastQuotedCell(
    final String text,
    final List<String> values
  ) {
    Matcher matcher = this.config().lastQuotePattern().matcher(text);
    if (!matcher.find()) {
      throw new IllegalStateException();
    }

    String value = matcher.replaceFirst("");
    values.add(value);
  }

  // Renamed
  private void addLastUnquotedCell(
    final String text,
    final List<String> values
  ) {
    values.add(text.trim());
  }

  private String addCell(
    final String text,
    final List<String> values,
    final int start,
    final int end,
    final boolean trim
  ) {
    String value = text.substring(0, start);
    if (trim) {
      value = value.trim();
    }
    values.add(value);

    String newText = text.substring(end).trim();
    if (newText.isEmpty()) {
      values.add("");
    }

    return newText;
  }
}
