package cn.mrdear.test;

/**
 * --sugarenums false
 */
public enum EnumExample {

  APPLE(1),
  BANANA(2),;

  private int value;

  EnumExample(int value) {
    this.value = value;
  }


}
