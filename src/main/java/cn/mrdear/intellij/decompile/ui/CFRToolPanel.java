package cn.mrdear.intellij.decompile.ui;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;

import org.apache.commons.lang3.StringUtils;
import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;
import org.benf.cfr.reader.util.getopt.GetOptParser;
import org.benf.cfr.reader.util.getopt.Options;
import org.benf.cfr.reader.util.getopt.OptionsImpl;
import org.jetbrains.org.objectweb.asm.ClassReader;

import cn.mrdear.intellij.decompile.util.ClassByteCodeSourceImpl;
import cn.mrdear.intellij.decompile.util.StringWriterDumperFactory;

import java.io.StringWriter;
import java.util.List;

/**
 * @author Quding Ding
 * @since 2020/3/7
 */
public class CFRToolPanel extends AbstractToolPanel {

    public CFRToolPanel(Project project) {
        super(project);
    }

    /**
     * 得到当前实例
     *
     * @param project 当前工程
     * @return 结果
     */
    public static CFRToolPanel getInstance(Project project) {
        return ServiceManager.getService(project, CFRToolPanel.class);
    }

    public void decompile(String path, StringWriter writer, ClassReader reader) {
        // fill args
        Pair<List<String>, Options> optionsPair = null;
        try {
            String temp = path.concat(" ").concat(SETTING.getCfr());
            String[] args = StringUtils.split(temp, ' ');

            GetOptParser getOptParser = new GetOptParser();
            optionsPair = getOptParser.parse(args, OptionsImpl.getFactory());
        } catch (Exception e) {
            this.setCode("parameter parse failed " + e.getMessage());
            writer.getBuffer().setLength(0);
            return;
        }

        // cfr
        try {
            Options options = optionsPair.getSecond();
            if (!options.optionIsSet(OptionsImpl.HELP)) {
                CfrDriver driver = new CfrDriver.Builder()
                    .withBuiltOptions(options)
                    .withOutputSink(new StringWriterDumperFactory(writer))
                    .withClassFileSource(new ClassByteCodeSourceImpl(options, reader.b, "temp.java"))
                    .build();
                driver.analyse(optionsPair.getFirst());
                this.setCode(writer.toString());
                return;
            }
            writer.append("decompile fail");
            this.setCode(writer.toString());
        } catch (Exception e) {
            this.setCode("decompile fail " + e.getMessage());
        } finally {
            writer.getBuffer().setLength(0);
        }
    }

}
