package cn.mrdear.test;

/**
 * --stringbuilder false
 */
public class StringBuilderExample {

  public static void main(String[] args) {

    // 常见错误写法
    String result = "num:";

    for (int i = 0; i < 10; i++) {
      result = result + String.valueOf(i);
    }
    System.out.println(result);
  }


}
