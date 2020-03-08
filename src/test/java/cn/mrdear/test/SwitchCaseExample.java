package cn.mrdear.test;

/**
 *
 * --decodestringswitch false
 *
 * @author Quding Ding
 * @since 2018/7/7
 */
public class SwitchCaseExample {

  public static void main(String[] args) {
    SwitchCaseExample example = new SwitchCaseExample();
    example.switchCase("key1");
  }

  private Integer switchCase(String key) {
    switch (key) {
      case "key1":
        return 1;
      case "key4":
        break;
      case "key3":
      case "key3.1":
        return 3;
      default:
        return 0;
    }
    return -1;
  }


}
