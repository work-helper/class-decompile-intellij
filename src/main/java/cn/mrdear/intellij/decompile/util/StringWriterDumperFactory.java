package cn.mrdear.intellij.decompile.util;

import org.benf.cfr.reader.api.OutputSinkFactory;
import org.benf.cfr.reader.api.SinkReturns;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Quding Ding
 * @since 2018/7/7
 */
public class StringWriterDumperFactory implements OutputSinkFactory {
  /**
   * 输出路径
   */
  private StringWriter writer;

  public StringWriterDumperFactory(StringWriter writer) {
    this.writer = writer;
  }

  @Override
  public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> collection) {
    if (sinkType == SinkType.JAVA && collection.contains(SinkClass.DECOMPILED)) {
      // I'd like "Decompiled".  If you can't do that, I'll take STRING.
      return Arrays.asList(SinkClass.DECOMPILED, SinkClass.STRING);
    } else {
      // I only understand how to sink strings, regardless of what you have to give me.
      return Collections.singletonList(SinkClass.STRING);
    }
  }

  @Override
  public <T> Sink<T> getSink(SinkType sinkType, SinkClass sinkClass) {
    if (sinkType == SinkType.JAVA && sinkClass == SinkClass.DECOMPILED) {
      return x -> dumpDecompiled.accept((SinkReturns.Decompiled) x);
    }
    if (sinkType == SinkType.EXCEPTION) {
      return x -> writer.append(((SinkReturns.ExceptionMessage) x).getMessage());
    }
    return ignore -> {};
  }


  private Consumer<SinkReturns.Decompiled> dumpDecompiled = d -> {
    writer.append("Package [").append(d.getPackageName()).append("] Class [").append(d.getClassName()).append("]");
    writer.append(d.getJava());
  };
}
