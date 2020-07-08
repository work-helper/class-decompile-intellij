package cn.mrdear.intellij.decompile.util;

/**
 * 将unicode转换成中文字符
 *
 * @author 千堆雪
 * @version 1.0.0
 */
public abstract class UnicodeUtil {
    /**
     * unicode码转换成自然语言显示
     *
     * @param original 原始字符串
     * @return 转换后的字符串
     */
    public static String unicodeToNative(String original) {
        String[] tokens = original.split("\\\\u");
        StringBuilder sb = new StringBuilder(tokens[0]);
        for (int i = 1; i < tokens.length; i++) {
            String code = tokens[i];

            try {
                sb.append((char) Integer.parseInt(code.substring(0, 4), 16));
                if (code.length() > 4) {
                    sb.append(code.substring(4, code.length()));
                }
            } catch (NumberFormatException e) {
                sb.append(code);
            }
        }
        return sb.toString();
    }
}
