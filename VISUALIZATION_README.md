# 树形可视化使用说明

## 📖 概述

这是一个交互式的网页可视化工具，用于展示：
- **回溯决策树**：显示回溯算法的决策过程、剪枝和结果
- **二叉树递归树**：显示递归调用的关系和返回值

## 🚀 快速开始

### 1. 打开可视化页面

在浏览器中打开 `visualization.html` 文件：

```bash
# 方式1: 直接双击文件
open visualization.html

# 方式2: 使用浏览器打开
firefox visualization.html
# 或
chrome visualization.html
```

### 2. 使用示例数据

页面会自动加载示例数据，您可以：
- 🖱️ **拖拽画布**：左键按住拖动
- 🔍 **缩放**：鼠标滚轮
- 👆 **点击节点**：查看详细信息
- 🎨 **调整布局**：使用顶部控件

### 3. 加载自定义数据

#### 方式A: 从Java程序导出JSON

在Java代码中添加导出功能：

```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;

// 导出决策树为JSON
public static void exportDecisionTreeToJson(DecisionTreeNode root, String filename) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    try (FileWriter writer = new FileWriter(filename)) {
        gson.toJson(root, writer);
        System.out.println("树结构已导出到: " + filename);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

// 导出递归树为JSON
public static void exportRecursionTreeToJson(RecursionTreeNode root, String filename) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    try (FileWriter writer = new FileWriter(filename)) {
        gson.toJson(root, writer);
        System.out.println("递归树已导出到: " + filename);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

然后在你的算法中：

```java
// 运行算法后导出
BacktrackUtils.exportDecisionTreeToJson(decisionTree, "backtrack-tree.json");
TreeUtils.exportRecursionTreeToJson(recursionTree, "recursion-tree.json");
```

#### 方式B: 手动创建JSON文件

创建一个JSON文件，格式如下：

**回溯决策树格式：**
```json
{
  "type": "backtrack",
  "displayText": "[]",
  "description": "开始",
  "depth": 0,
  "nodeType": "INTERNAL",
  "children": [
    {
      "displayText": "[2]",
      "choice": 2,
      "depth": 1,
      "nodeType": "LEAF",
      "description": "找到解"
    }
  ]
}
```

**递归树格式：**
```json
{
  "type": "recursion",
  "functionName": "maxDepth",
  "treeNode": { "val": 3 },
  "depth": 0,
  "nodeType": "CALL",
  "returnValue": 3,
  "children": [
    {
      "functionName": "maxDepth",
      "treeNode": { "val": 9 },
      "depth": 1,
      "nodeType": "CALL",
      "returnValue": 1
    }
  ]
}
```

### 4. 上传JSON文件

在可视化页面中：
1. 点击"点击或拖拽 JSON 文件到这里"区域
2. 选择你的 JSON 文件
3. 树形结构会自动渲染

## 🎮 功能说明

### 控件

| 控件 | 功能 |
|------|------|
| 树类型 | 切换回溯决策树/递归树显示模式 |
| 布局 | 纵向（根在上）或横向（根在左） |
| 节点间距 | 调整节点之间的距离 |
| 重置视图 | 恢复默认缩放和位置 |
| 导出图片 | 保存当前树形图为PNG图片 |

### 节点颜色

- 🟢 **绿色**：有效结果（叶子节点✓）
- 🔴 **红色**：剪枝节点（✂）
- 🔵 **蓝色**：普通节点
- 🟠 **橙色**：基础情况（递归终止）
- ⚫ **灰色**：空节点（null）

### 交互操作

- **拖拽**：鼠标左键按住画布拖动
- **缩放**：鼠标滚轮
- **查看详情**：点击任意节点查看详细信息
- **隐藏详情**：点击画布空白处

## 📊 数据格式详解

### DecisionTreeNode 字段

```typescript
{
  displayText: string;      // 显示文本，如 "[2, 3]"
  description?: string;     // 描述信息，如 "和=8"
  depth: number;           // 递归深度
  nodeType: "INTERNAL" | "LEAF" | "PRUNED";  // 节点类型
  choice?: any;            // 当前步骤的选择
  children?: DecisionTreeNode[];  // 子节点数组
}
```

### RecursionTreeNode 字段

```typescript
{
  functionName: string;     // 函数名，如 "maxDepth"
  treeNode?: {             // 对应的二叉树节点
    val: number;
  };
  depth: number;           // 递归深度
  nodeType: "CALL" | "BASE_CASE" | "RETURN";  // 节点类型
  returnValue?: any;       // 返回值
  params?: any[];          // 函数参数
  description?: string;    // 描述信息
  children?: RecursionTreeNode[];  // 子调用
}
```

## 🔧 高级用法

### 在pom.xml中添加Gson依赖

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

### 示例：完整的导出代码

```java
public class CombinationSum {
    public static void main(String[] args) {
        // 1. 运行算法
        DecisionTreeNode root = new DecisionTreeNode("[]", "开始", 0);
        List<List<Integer>> results = combinationSum(
            new int[]{2, 3, 6, 7},
            7,
            root
        );

        // 2. 打印控制台树
        BacktrackUtils.printDecisionTreeVertical(root, "组合总和决策树");

        // 3. 导出JSON供可视化
        BacktrackUtils.exportDecisionTreeToJson(root, "output/combination-sum.json");

        System.out.println("可视化文件已生成，请在浏览器中打开 visualization.html");
    }
}
```

## 💡 提示

1. **大型树优化**：对于节点数量很多的树，建议调大节点间距以避免重叠
2. **导出高清图片**：放大树到合适大小后再导出图片
3. **数据验证**：确保JSON文件格式正确，可以使用在线JSON验证工具检查
4. **浏览器兼容性**：推荐使用Chrome、Firefox或Edge浏览器

## 🐛 常见问题

**Q: 页面显示空白？**
A: 检查浏览器控制台是否有JavaScript错误，确认JSON数据格式正确

**Q: 节点重叠怎么办？**
A: 增加"节点间距"滑块的值，或者使用拖拽和缩放功能调整视图

**Q: 如何处理超大树？**
A: 可以修改JSON文件，只导出树的一部分进行可视化

**Q: 能否自定义节点颜色？**
A: 可以修改 `visualization.html` 中的 `drawNode()` 函数来自定义样式

## 📝 更新日志

- **v1.0** (2025-01-11)
  - 初始版本
  - 支持回溯决策树和递归树可视化
  - 交互式拖拽、缩放
  - 节点详情显示
  - 统计信息面板
  - 图片导出功能
