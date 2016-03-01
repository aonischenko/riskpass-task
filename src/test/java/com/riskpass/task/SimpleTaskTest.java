package com.riskpass.task;

import com.riskpass.task.services.ExtractionService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SimpleTaskTest {

  ExtractionService extractionService = new ExtractionService();

  private void test(String example, String[] expected) {
    List<String> result = extractionService.extract(example);
    Assert.assertArrayEquals(expected, result.<String>toArray());
  }

  @Test
  public void simpleTest() {
    String example = "1,2,3,4,5,6";
    String[] expected = new String[]{"1", "2", "3", "4", "5", "6"};
    test(example, expected);
  }

  @Test
  public void lastCellEmptyTest() {
    String example = "1,2,3,4,5,";
    String[] expected = new String[]{"1", "2", "3", "4", "5", ""};
    test(example, expected);
  }

  @Test
  public void lastCellComplexTest() {
    String example = "1,2,3,4,5 ";
    String[] expected = new String[]{"1", "2", "3", "4", "5"};
    test(example, expected);
  }

  @Test
  public void lastCellWhitespaceTest() {
    String example = "1,2,3,4,5, ";
    String[] expected = new String[]{"1", "2", "3", "4", "5", ""};
    test(example, expected);
  }

  @Test
  public void separatorsTest() {
    String example = "1.2,3,4,5.6";
    String[] expected = new String[]{"1.2", "3", "4", "5.6"};
    test(example, expected);
  }

  @Test
  public void quotedValueTest() {
    String example = "1,\"2\"";
    String[] expected = new String[]{"1", "2"};
    test(example, expected);
  }

  @Test
  public void quotedWhitespaceTest() {
    String example = " 1 ,\" 2\", \"3 \", \" 4 \",   \"  5\" ";
    String[] expected = new String[]{"1", " 2", "3 ", " 4 ", "  5"};
    test(example, expected);
  }

  @Test
  public void escapedQuoteTest() {
    String example = " 1 ,\\\"2,";
    // that's not fair and maybe escaped quotes should be unescaped in the result
    String[] expected = new String[]{"1", "\\\"2", ""};
    test(example, expected);
  }

  @Test
  public void innerQuoteTest() {
    String example = " 1 ,\"2\"\"";
    String[] expected = new String[]{"1", "2\""};
    test(example, expected);
  }

  @Test
  public void escapedInnerQuoteTest() {
    String example = " 1 ,\"\"2\"3\"";
    String[] expected = new String[]{"1", "\"2\"3"};
    test(example, expected);
  }

  @Test(expected = IllegalStateException.class)
  public void unescapedInnerQuoteTest() {
    String example = " 1 ,\"\"2\"3";
    String[] expected = new String[]{"1", "\"2\"3"};
    test(example, expected);
  }

  @Test(expected = IllegalStateException.class)
  public void unclosedQuoteTest() {
    String example = " 1 ,\"2,";
    String[] expected = new String[]{"1", "2"};
    test(example, expected);
  }

}
