# class decompile

use javap & asm & cfr decompile .class file and show

![](http://imgblog.mrdear.cn/1583656954.png?imageMogr2/thumbnail/!60p)

## javap

use javap need external tools support, like this picture config

![](http://imgblog.mrdear.cn/1583656067.png?imageMogr2/thumbnail/!100p)


```bash
  -help  --help  -?        输出此用法消息
  -version                 版本信息
  -v  -verbose             输出附加信息
  -l                       输出行号和本地变量表
  -public                  仅显示公共类和成员
  -protected               显示受保护的/公共类和成员
  -package                 显示程序包/受保护的/公共类
                           和成员 (默认)
  -p  -private             显示所有类和成员
  -c                       对代码进行反汇编
  -s                       输出内部类型签名
  -sysinfo                 显示正在处理的类的
                           系统信息 (路径, 大小, 日期, MD5 散列)
  -constants               显示最终常量
  -classpath <path>        指定查找用户类文件的位置
  -cp <path>               指定查找用户类文件的位置
  -bootclasspath <path>    覆盖引导类文件的位置

```

## cfr

refer to [http://www.benf.org/other/cfr/](http://www.benf.org/other/cfr/)

```bash
CFR 0.149

   --aexagg                         (boolean) 
   --aggressivesizethreshold        (int >= 0)  default: 15000
   --allowcorrecting                (boolean)  default: true
   --analyseas                      (One of [DETECT, JAR, WAR, CLASS]) 
   --arrayiter                      (boolean)  default: true if class file from version 49.0 (Java 5) or greater
   --caseinsensitivefs              (boolean)  default: true
   --clobber                        (boolean) 
   --collectioniter                 (boolean)  default: true if class file from version 49.0 (Java 5) or greater
   --commentmonitors                (boolean)  default: false
   --comments                       (boolean)  default: true
   --decodeenumswitch               (boolean)  default: true if class file from version 49.0 (Java 5) or greater
   --decodefinally                  (boolean)  default: true
   --decodelambdas                  (boolean)  default: true if class file from version 52.0 (Java 8) or greater
   --decodestringswitch             (boolean)  default: true if class file from version 51.0 (Java 7) or greater
   --dumpclasspath                  (boolean)  default: false
   --eclipse                        (boolean)  default: true
   --elidescala                     (boolean)  default: false
   --extraclasspath                 (string) 
   --forcecondpropagate             (boolean) 
   --forceexceptionprune            (boolean) 
   --forcereturningifs              (boolean) 
   --forcetopsort                   (boolean) 
   --forcetopsortaggress            (boolean) 
   --forloopaggcapture              (boolean) 
   --hidebridgemethods              (boolean)  default: true
   --hidelangimports                (boolean)  default: true
   --hidelongstrings                (boolean)  default: false
   --hideutf                        (boolean)  default: true
   --ignoreexceptions               (boolean)  default: false
   --ignoreexceptionsalways         (boolean)  default: false
   --importfilter                   (string) 
   --innerclasses                   (boolean)  default: true
   --instanceofpattern              (boolean)  default: true if class file from version 58.0 (Java 14) or greater, or experimental in 58.0 (Java 14)
   --j14classobj                    (boolean)  default: false if class file from version 49.0 (Java 5) or greater
   --jarfilter                      (string) 
   --labelledblocks                 (boolean)  default: true
   --lenient                        (boolean)  default: false
   --liftconstructorinit            (boolean)  default: true
   --methodname                     (string) 
   --obfuscationpath                (string) 
   --outputdir                      (string) 
   --outputpath                     (string) 
   --override                       (boolean)  default: true if class file from version 50.0 (Java 6) or greater
   --previewfeatures                (boolean)  default: true
   --pullcodecase                   (boolean)  default: false
   --recordtypes                    (boolean)  default: true if class file from version 58.0 (Java 14) or greater, or experimental in 58.0 (Java 14)
   --recover                        (boolean)  default: true
   --recovertypeclash               (boolean) 
   --recovertypehints               (boolean) 
   --relinkconststring              (boolean)  default: true
   --removebadgenerics              (boolean)  default: true
   --removeboilerplate              (boolean)  default: true
   --removedeadmethods              (boolean)  default: true
   --removeinnerclasssynthetics     (boolean)  default: true
   --rename                         (boolean)  default: false
   --renamedupmembers               (boolean)  default: Value of option 'rename'
   --renameenumidents               (boolean)  default: Value of option 'rename'
   --renameillegalidents            (boolean)  default: Value of option 'rename'
   --renamesmallmembers             (int >= 0)  default: 0
   --showinferrable                 (boolean)  default: false if class file from version 51.0 (Java 7) or greater
   --showversion                    (boolean)  default: true
   --silent                         (boolean)  default: false
   --skipbatchinnerclasses          (boolean)  default: true
   --stringbuffer                   (boolean)  default: false if class file from version 49.0 (Java 5) or greater
   --stringbuilder                  (boolean)  default: true if class file from version 49.0 (Java 5) or greater
   --stringconcat                   (boolean)  default: true if class file from version 53.0 (Java 9) or greater
   --sugarasserts                   (boolean)  default: true
   --sugarboxing                    (boolean)  default: true
   --sugarenums                     (boolean)  default: true if class file from version 49.0 (Java 5) or greater
   --switchexpression               (boolean)  default: true if class file from version 57.0 (Java 13) or greater, or experimental in 56.0 (Java 12)
   --tidymonitors                   (boolean)  default: true
   --tryresources                   (boolean)  default: true if class file from version 51.0 (Java 7) or greater
   --usenametable                   (boolean)  default: true
   --help                           (string) 
```