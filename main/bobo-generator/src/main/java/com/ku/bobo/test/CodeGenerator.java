package com.ku.bobo.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2020/10/29 15:56
 * @Created by xb
 */
public class CodeGenerator {

    //    static String Url = "jdbc:mysql://139.224.129.134:3306/grave?serverTimezone=UTC&characterEncoding=utf-8&useSSL=false";
    static String Url = "jdbc:mysql://127.0.0.1:3306/main?serverTimezone=UTC&characterEncoding=utf-8&useSSL=false";
    static String DriverName = "com.mysql.cj.jdbc.Driver";
    static String Username = "root";
    static String Password = "123456";

    static String mp = "grave-mp";

    static String modules[] = new String[]{ mp ,"bobo-system"};

    public static void main(String[] args) {

        for (String module : modules) {
            generator( module );
        }

    }

    static void generator( String module ){

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/src/main/java");
//        gc.setOutputDir( projectPath+"/mall-mp"+"/src/main/java");
        gc.setOutputDir( projectPath +"/"+ module +"/src/main/java");
        gc.setAuthor("zxb");
        gc.setOpen(false);
        gc.setIdType( IdType.AUTO );
        gc.setFileOverride( true );
        gc.setBaseResultMap( true );
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl( Url );
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername( Username );
        dsc.setPassword( Password );
//        dsc.setUrl( Url );
//        // dsc.setSchemaName("public");
//        dsc.setDriverName( DriverName );
//        dsc.setUsername( Username );
//        dsc.setPassword( Password );
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("");
        pc.setParent("com.ku.bobo.modules.poll");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
//        if ( mp.equals( module )  ){
//            templateConfig.setController( null );
//            templateConfig.setService(null);
//            templateConfig.setServiceImpl(null);
//            templateConfig.setXml( null );
//            templateConfig.setMapper( null );
//        }else{
//            templateConfig.setController( "/resources/controller.java" );
//            templateConfig.setEntity( null );
//        }

//        templateConfig.setController( null );
//        templateConfig.setService(null);
//        templateConfig.setServiceImpl(null);
//        templateConfig.setXml( null );
//        templateConfig.setMapper( null );
//        templateConfig.setEntity( null );

        templateConfig.setController( "/controller.java" );


        mpg.setTemplate(templateConfig);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
//        strategy.setInclude( "us_admin" .split(","));
//        strategy.setTablePrefix("ps_");
        LikeTable likeTable = new LikeTable( "poll_" );
        strategy.setLikeTable( likeTable );
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setInclude( "poll" );
//        strategy.setExclude("ps_brand");
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
