package com.riskpass.task;

import com.riskpass.task.config.Config;
import com.riskpass.task.services.ExtractionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ComplexTaskTest {

  ExtractionService extractionService;

  @Before
  public void init() {
    Config.Modifiable config = new Config.Modifiable("<>", "\\^\\^");

    extractionService = new ExtractionService(config);
  }

  private void test(String example, String[] expected) {
    List<String> result = extractionService.extract(example);
    Assert.assertArrayEquals(expected, result.<String>toArray());
  }

  @Test
  public void simpleTest() {
    String example = "1<>2<>3<>4<>5<>6";
    String[] expected = new String[]{"1", "2", "3", "4", "5", "6"};
    test(example, expected);
  }

  @Test
  public void quotedValueTest() {
    String example = "1<>^^2^^";
    String[] expected = new String[]{"1", "2"};
    test(example, expected);
  }

}
