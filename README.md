# LeetCode Hot 100 - 二叉树题解

本项目包含LeetCode Hot 100中二叉树相关题目的Java实现，每个题目都包含：
- 详细的中文注释
- 完整的解题思路和算法说明
- 时间复杂度和空间复杂度分析
- **执行流程日志输出**
- JUnit 5测试用例

## 项目结构

```
src/main/java/com/muqian/leetcode/tree/
├── TreeNode.java                      # 二叉树节点定义
├── TreeUtils.java                     # 工具类：构建树、打印树
├── traversal/                         # 遍历类题目
│   ├── InorderTraversal.java         # 94. 中序遍历
│   ├── PreorderTraversal.java        # 144. 前序遍历
│   └── LevelOrderTraversal.java      # 102. 层序遍历
├── depth/                            # 深度/直径类
│   ├── MaxDepth.java                 # 104. 最大深度
│   └── DiameterOfBinaryTree.java     # 543. 直径
├── structure/                        # 结构变换类
│   ├── InvertTree.java               # 226. 翻转二叉树
│   └── IsSymmetric.java              # 101. 对称二叉树
├── path/                             # 路径问题
│   ├── HasPathSum.java               # 112. 路径总和
│   └── PathSumIII.java               # 437. 路径总和III
├── construction/                     # 构造类
│   └── BuildTreePreIn.java           # 105. 前序+中序构造
├── lca/                              # 最近公共祖先
│   └── LowestCommonAncestor.java     # 236. LCA
└── bst/                              # 二叉搜索树
    └── IsValidBST.java               # 98. 验证BST
```

## 已实现题目列表

### 遍历类
- [x] 94. 二叉树的中序遍历（递归 + 迭代）
- [x] 144. 二叉树的前序遍历（递归 + 迭代）
- [x] 102. 二叉树的层序遍历

### 深度/直径类
- [x] 104. 二叉树的最大深度（递归 + 迭代）
- [x] 543. 二叉树的直径

### 结构变换类
- [x] 226. 翻转二叉树（递归 + 迭代）
- [x] 101. 对称二叉树（递归 + 迭代）

### 路径问题
- [x] 112. 路径总和
- [x] 437. 路径总和III（双重递归 + 前缀和）

### 构造类
- [x] 105. 从前序与中序遍历构造二叉树

### 其他
- [x] 236. 二叉树的最近公共祖先
- [x] 98. 验证二叉搜索树（范围检查 + 中序遍历）

## 特色功能

### 1. 详细的执行流程日志
每个算法都使用SLF4J + Logback记录详细的执行流程，包括：
- 递归的进入和退出
- 节点的访问顺序
- 中间变量的值
- 关键决策点

示例输出：
```
========== 开始递归中序遍历 ==========
→ 进入节点: 1
  ├─ 遍历左子树
  → 进入节点: 2
    ├─ 遍历左子树
    → 进入节点: 4
      ├─ 遍历左子树
      → 节点为null，返回
      ├─ 访问当前节点: 4
      └─ 遍历右子树
      → 节点为null，返回
    ← 节点 4 处理完成
    ├─ 访问当前节点: 2
    ...
========== 遍历完成，结果: [4, 2, 5, 1, 3] ==========
```

### 2. 完整的注释和说明
每个类包含：
- 题目描述
- 解题思路
- 算法步骤
- 时间复杂度和空间复杂度分析
- 关键代码的详细注释

### 3. 多种解法
大部分题目提供多种实现方式：
- 递归 vs 迭代
- 不同的算法思路

## 运行测试

### 运行所有测试
```bash
mvn test
```

### 运行特定测试
```bash
mvn test -Dtest=BinaryTreeProblemsTest#testInorderTraversalRecursive
```

### 查看测试覆盖率
所有测试通过：**17/17** ✓

## 环境要求

- Java 21
- Maven 3.x

## 依赖

- JUnit 5.10.1 - 单元测试
- SLF4J 2.0.9 - 日志API
- Logback 1.4.14 - 日志实现

## 使用示例

```java
// 创建测试树
TreeNode root = TreeUtils.buildTree(new Integer[]{1, 2, 3, 4, 5});

// 中序遍历
InorderTraversal solution = new InorderTraversal();
List<Integer> result = solution.inorderTraversalRecursive(root);
// 输出：[4, 2, 5, 1, 3]
// 同时会在控制台打印详细的执行流程
```

## 学习建议

1. **先看注释理解思路** - 每个类的头部都有详细的解题思路
2. **运行测试观察日志** - 执行流程日志能帮助理解算法的实际运行过程
3. **对比不同解法** - 理解递归和迭代的区别，以及不同算法的优劣
4. **动手调试** - 可以修改测试用例，观察不同输入下的执行流程

## 贡献

欢迎提交Issue和Pull Request！

## 作者

muqian

## 许可证

MIT License
