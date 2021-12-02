# pipline-library  
### 引用  
##### 1. 只能在script中引用共享库，这是Jenkins规范的  
##### 2. vars默认使用def call()方式编写   
(root)
+- src   # Groovy source files  
|   +- org  
|       +- foo  
|           +- Bar.groovy  # for org.foo.Bar class  
+- vars  
|   +- foo.groovy          # for global 'foo' variable  
|   +- foo.txt             # help for 'foo' variable  
+- resources               # resource files (external libraries only)  
|   +- org  
|       +- foo  
|           +- bar.json    # static helper data for org.foo.Bar  
> 1. src 目录应该看起来像标准的 Java 源目录结构。当执行流水线时，该目录被添加到类路径下。  
> 2. vars 目录定义可从流水线访问的全局变量的脚本。 每个 *.groovy 文件的基名应该是一个 Groovy (~ Java) 标识符, 通常是 camelCased。 匹配 *.txt, 如果存在, 可以包含文档, 通过系统的配置标记格式化从处理 (所以可能是 HTML, Markdown 等，虽然 txt 扩展是必需的)。  
> 3. resources 目录允许从外部库中使用 libraryResource 步骤来加载有关的非 Groovy 文件。 目前，内部库不支持该特性。  

###### 一般src编写复杂的通用代码块，vars定义简单的变量模块，resources存放文本，例如（Dockerfile、json、yaml等）

- @Library('jenkinsShareLibrary@main') _    
引用全部 （第一种） 
- @Library('jenkinsShareLibrary@main')   
  import src/com/devops/tools  
  引用单个（第二种）
> @Library 引用函数库 @main 使用main分支

> scr 路径必备， com/devops 自定义， tools是你的groovy脚本名称   

- def tools = new com.devops.tools()  
> 导入共享库中的方法类 定义名为 tools

- String branchName = "${env.Branch}"  
> 在非pipeline定义全局变量，外部也可以调用  

- 引用是 脚本名+函数名：tools.tool()  