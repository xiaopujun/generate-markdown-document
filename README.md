- 这是一个通过正则表达式解析java注释信息从而生成markdown格式开发文档的插件
- 你只需三步即可快速生成markdown格式的接口说明文档

### 一、使用方法

- 第一步：遵循注释规范写书写方法、类的注释信息
- 第二步：选中需要生成文档的代码部分，格式化代码（ctrl+alt+L）后复制代码
- 第三步：使用快捷键ctrl+K或Tools菜单中的Generate Markdown Document即可生成文档到剪切板中

生成文档后，粘贴即可使用

### 二、注释代码示例

注：所有的信息说明都需要他对应的标签，反之则无法解析出正确的文档

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yinzhenpu
 * @description 这是一个java注释文档自动生成的测试类
 * @date 2022-04-30
 */
public class Demo01 {
    /**
     * @param p1 测试参数1，描述类参数1的基本含义
     * @description 测试方法1，描述类方法1的基本功能及使用方式
     */
    public void m1(String p1) {

    }

    /**
     * @param p1 测试参数1
     * @param p2 测试参数2
     * @return 返回值为string
     * @description 测试方法2
     */
    public String m2(String p1, Integer p2) {
        return "";
    }

    /**
     * @param p1 测试参数1
     * @param p2 测试参数2
     * @param p3 测试参数3
     * @return 返回值为List类型
     * @description 这个方法3的使用描述，这个描述可能会超级长以至于需要
     * 换行
     */
    public List<Long> m3(String p1, Integer p2, List<Long> p3) {
        return new ArrayList<>();
    }

    /**
     * @param p1 测试参数1
     * @param p2 测试参数2
     * @param p3 测试参数3
     * @param p4 测试参数4
     * @return Integer类型的返回值
     * @description 这个方法3的使用描述，
     * 这个描述可能会超级长
     * 以至于需要好几行
     */
    public Integer m4(String p1, Integer p2, List<Long> p3, Map<String, Object> p4) {
        return 1;
    }
}


```

### 三、效果图：

![demo1.gif](https://s2.loli.net/2022/04/30/4cxnq3YvBlsdhwo.gif)