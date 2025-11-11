# LeetCode Hot 100 - 题解集合

本项目包含LeetCode Hot 100中的Java实现，每个题目都包含：
- 详细的中文注释
- 完整的解题思路和算法说明
- 时间复杂度和空间复杂度分析
- **执行流程日志输出**
- JUnit 5测试用例

## 项目结构

```
src/main/java/com/muqian/leetcode/
├── tree/                               # 二叉树相关题目
│   ├── TreeNode.java                   # 二叉树节点定义
│   ├── TreeUtils.java                  # 工具类：构建树、打印树
│   ├── traversal/                      # 遍历类题目
│   ├── depth/                          # 深度/直径类
│   ├── structure/                      # 结构变换类
│   ├── path/                           # 路径问题
│   ├── construction/                   # 构造类
│   ├── lca/                            # 最近公共祖先
│   └── bst/                            # 二叉搜索树
├── backtracking/                       # 回溯算法题目
│   ├── combination/                    # 组合问题
│   ├── permutation/                    # 排列问题
│   └── ...                             # 其他回溯问题
└── dp/                                 # 动态规划题目 ⭐ 新增
    ├── DPUtils.java                    # DP工具类
    ├── basic/                          # 基础动态规划
    │   ├── ClimbingStairs.java         # 70. 爬楼梯
    │   ├── HouseRobber.java            # 198. 打家劫舍
    │   └── MaximumSubarray.java        # 53. 最大子数组和
    ├── path/                           # 路径问题
    │   └── UniquePaths.java            # 62. 不同路径
    ├── knapsack/                       # 背包问题
    │   └── CoinChange.java             # 322. 零钱兑换
    ├── stock/                          # 股票问题
    │   └── BestTimeToBuyAndSellStock.java  # 121. 买卖股票的最佳时机
    ├── string/                         # 字符串DP
    │   └── LongestPalindromicSubstring.java  # 5. 最长回文子串
    └── sequence/                       # 子序列问题（待扩展）
```

## 已实现题目列表

### 二叉树（Tree）
- [x] 94. 二叉树的中序遍历（递归 + 迭代）
- [x] 144. 二叉树的前序遍历（递归 + 迭代）
- [x] 102. 二叉树的层序遍历
- [x] 104. 二叉树的最大深度（递归 + 迭代）
- [x] 543. 二叉树的直径
- [x] 226. 翻转二叉树（递归 + 迭代）
- [x] 101. 对称二叉树（递归 + 迭代）
- [x] 112. 路径总和
- [x] 437. 路径总和III（双重递归 + 前缀和）
- [x] 105. 从前序与中序遍历构造二叉树
- [x] 236. 二叉树的最近公共祖先
- [x] 98. 验证二叉搜索树（范围检查 + 中序遍历）

### 回溯算法（Backtracking）
- [x] 78. 子集
- [x] 46. 全排列
- [x] 47. 全排列 II
- [x] 39. 组合总和
- [x] 77. 组合

### 动态规划（Dynamic Programming）⭐ 新增

#### 基础动态规划
- [x] 70. 爬楼梯（基础DP + 空间优化）
- [x] 198. 打家劫舍（基础DP + 空间优化）
- [x] 53. 最大子数组和（Kadane算法 + 空间优化）

#### 路径问题
- [x] 62. 不同路径（二维DP + 一维优化）

#### 背包问题
- [x] 322. 零钱兑换（完全背包）

#### 股票问题
- [x] 121. 买卖股票的最佳时机（一次遍历 + 状态机DP）

#### 字符串DP
- [x] 5. 最长回文子串（DP + 中心扩展）

## 特色功能

### 1. 详细的执行流程日志
每个算法都使用SLF4J + Logback记录详细的执行流程，包括：
- DP数组的初始化过程
- 状态转移的每一步计算
- 中间变量的值
- 关键决策点的选择

#### 动态规划日志示例：
```
========== 开始计算爬楼梯方法数（动态规划） ==========
问题：爬到第 5 阶楼梯有多少种方法？
状态转移方程：dp[i] = dp[i-1] + dp[i-2]

[步骤1] 初始化：dp[1] = 1, dp[2] = 2
DP数组: [0, 1, 2, 0, 0, 0]

[步骤2] 计算第 3 阶
│   dp[3] = dp[2] + dp[1]
│   dp[3] = 2 + 1 = 3
│   DP数组: [0, 1, 2, 3, 0, 0]

[步骤4] 计算第 5 阶
│   dp[5] = dp[4] + dp[3]
│   dp[5] = 5 + 3 = 8
│   DP数组: [0, 1, 2, 3, 5, 8]

╔══════════════════════════════════════╗
║  执行摘要                            ║
╠══════════════════════════════════════╣
║  总步骤数: 4                         ║
║  最终结果: 8                         ║
╚══════════════════════════════════════╝

========== 计算完成 ==========
```

### 2. 完整的注释和说明
每个类包含：
- 题目描述
- 解题思路
- 状态定义和状态转移方程
- 算法步骤
- 时间复杂度和空间复杂度分析
- 关键代码的详细注释

### 3. 多种解法
大部分题目提供多种实现方式：
- 基础DP vs 空间优化
- 不同的算法思路（如DP vs 贪心 vs 分治）
- 递归 vs 迭代

## 运行测试

### 运行所有测试
```bash
mvn test
```

### 运行特定测试类
```bash
# 二叉树测试
mvn test -Dtest=BinaryTreeProblemsTest

# 动态规划测试
mvn test -Dtest=DynamicProgrammingProblemsTest
```

### 运行特定测试方法
```bash
mvn test -Dtest=DynamicProgrammingProblemsTest#testClimbStairs
```

### 查看测试覆盖率
- 二叉树：**17/17** ✓
- 回溯：**10/10** ✓
- 动态规划：**14/14** ✓

## 环境要求

- Java 21
- Maven 3.x

## 依赖

- JUnit 5.10.1 - 单元测试
- SLF4J 2.0.9 - 日志API
- Logback 1.4.14 - 日志实现
- Tree Printer 2.1.0 - ASCII树可视化

## 使用示例

### 动态规划示例
```java
// 爬楼梯
ClimbingStairs solution = new ClimbingStairs();
int ways = solution.climbStairs(5);  // 输出：8
// 同时会在控制台打印详细的执行流程

// 打家劫舍
HouseRobber robber = new HouseRobber();
int maxMoney = robber.rob(new int[]{2, 7, 9, 3, 1});  // 输出：12
```

### 二叉树示例
```java
// 创建测试树
TreeNode root = TreeUtils.buildTree(new Integer[]{1, 2, 3, 4, 5});

// 中序遍历
InorderTraversal solution = new InorderTraversal();
List<Integer> result = solution.inorderTraversalRecursive(root);
// 输出：[4, 2, 5, 1, 3]
```

## 学习建议

1. **先看注释理解思路** - 每个类的头部都有详细的解题思路
2. **运行测试观察日志** - 执行流程日志能帮助理解算法的实际运行过程
3. **对比不同解法** - 理解基础解法和优化解法的区别
4. **动手调试** - 可以修改测试用例，观察不同输入下的执行流程
5. **总结模式** - 归纳同类型题目的解题模式（如背包DP、股票DP等）

## 动态规划核心要素

每道DP题目都包含以下核心要素的说明：

1. **状态定义**：dp[i] 或 dp[i][j] 代表什么含义
2. **状态转移方程**：如何从子问题推导到当前问题
3. **初始化**：边界条件的处理
4. **遍历顺序**：确保计算顺序正确
5. **空间优化**：如何降低空间复杂度

## 贡献

欢迎提交Issue和Pull Request！

## 作者

muqian

## 许可证

MIT License
