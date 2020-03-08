package cn.mrdear.intellij.decompile.util;

import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;
import org.benf.cfr.reader.state.ClassFileSourceImpl;
import org.benf.cfr.reader.util.getopt.Options;

import java.io.IOException;

/**
 * hack下ClassFileSourceImpl,使其支持直接读取字节码
 * @author Quding Ding
 * @since 2018/2/11
 */
public class ClassByteCodeSourceImpl extends ClassFileSourceImpl {

  private byte[] classSource;

  private String className;

  public ClassByteCodeSourceImpl(Options options,byte[] classSource,String className) {
    super(options);
    this.classSource = classSource;
    this.className = className;
  }

  public ClassByteCodeSourceImpl(Options options) {
    super(options);
  }


  @Override
  public Pair<byte[], String> getClassFileContent(String inputPath) throws IOException {
    Pair<byte[], String> fileContent = null;
    try {
      fileContent = super.getClassFileContent(inputPath);
    } catch (IOException e) {
      if (inputPath.lastIndexOf(className) > 0) {
        fileContent = Pair.make(classSource, inputPath);
      } else {
        throw e;
      }
    }
    return fileContent;
  }
}
