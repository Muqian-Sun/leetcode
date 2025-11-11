# LeetCode Hot 100 - 动态规划题解 (Java)

这个项目包含了 LeetCode Hot 100 中所有动态规划题目的 Java 解决方案，并提供了**可视化 debug 功能**，帮助你深入理解动态规划的执行过程。

## 🌟 特点

- ✅ **完整的题解**: 涵盖 LeetCode Hot 100 中所有主要的动态规划题目
- 🎨 **可视化 Debug**: 每道题都提供可视化输出，展示 DP 数组的变化过程
- 📝 **详细注释**: 包含详细的算法思路、状态转移方程和复杂度分析
- 🚀 **多种解法**: 很多题目提供多种解法（基础 DP、空间优化、其他算法等）
- 💡 **易于运行**: 每个类都有独立的 main 方法，可以直接运行查看效果

## 📁 项目结构

```
leetcode/
├── src/main/java/com/leetcode/dp/
│   ├── utils/
│   │   └── DPVisualizer.java          # 可视化工具类
│   └── problems/
│       ├── P70_ClimbingStairs.java    # 爬楼梯
│       ├── P198_HouseRobber.java      # 打家劫舍
│       ├── P53_MaximumSubarray.java   # 最大子数组和
│       ├── P62_UniquePaths.java       # 不同路径
│       ├── P64_MinimumPathSum.java    # 最小路径和
│       ├── P300_LongestIncreasingSubsequence.java  # 最长递增子序列
│       ├── P416_PartitionEqualSubsetSum.java       # 分割等和子集
│       ├── P322_CoinChange.java                    # 零钱兑换
│       ├── P279_PerfectSquares.java                # 完全平方数
│       ├── P121_BestTimeToBuyAndSellStock.java     # 买卖股票的最佳时机
│       ├── P122_BestTimeToBuyAndSellStockII.java   # 买卖股票的最佳时机 II
│       ├── P309_BestTimeToBuyAndSellStockWithCooldown.java  # 含冷冻期
│       ├── P5_LongestPalindromicSubstring.java     # 最长回文子串
│       ├── P72_EditDistance.java                   # 编辑距离
│       ├── P221_MaximalSquare.java                 # 最大正方形
│       └── P32_LongestValidParentheses.java        # 最长有效括号
└── README.md
```

## 📚 题目分类

### 1️⃣ 基础动态规划
| 题号 | 题目 | 难度 | 文件 |
|------|------|------|------|
| 70 | 爬楼梯 | 简单 | P70_ClimbingStairs.java |
| 198 | 打家劫舍 | 中等 | P198_HouseRobber.java |
| 53 | 最大子数组和 | 中等 | P53_MaximumSubarray.java |

### 2️⃣ 路径问题
| 题号 | 题目 | 难度 | 文件 |
|------|------|------|------|
| 62 | 不同路径 | 中等 | P62_UniquePaths.java |
| 64 | 最小路径和 | 中等 | P64_MinimumPathSum.java |

### 3️⃣ 子序列问题
| 题号 | 题目 | 难度 | 文件 |
|------|------|------|------|
| 300 | 最长递增子序列 | 中等 | P300_LongestIncreasingSubsequence.java |

### 4️⃣ 背包问题
| 题号 | 题目 | 难度 | 文件 |
|------|------|------|------|
| 416 | 分割等和子集 | 中等 | P416_PartitionEqualSubsetSum.java |
| 322 | 零钱兑换 | 中等 | P322_CoinChange.java |
| 279 | 完全平方数 | 中等 | P279_PerfectSquares.java |

### 5️⃣ 股票问题
| 题号 | 题目 | 难度 | 文件 |
|------|------|------|------|
| 121 | 买卖股票的最佳时机 | 简单 | P121_BestTimeToBuyAndSellStock.java |
| 122 | 买卖股票的最佳时机 II | 中等 | P122_BestTimeToBuyAndSellStockII.java |
| 309 | 最佳买卖股票时机含冷冻期 | 中等 | P309_BestTimeToBuyAndSellStockWithCooldown.java |

### 6️⃣ 字符串 DP
| 题号 | 题目 | 难度 | 文件 |
|------|------|------|------|
| 5 | 最长回文子串 | 中等 | P5_LongestPalindromicSubstring.java |
| 72 | 编辑距离 | 困难 | P72_EditDistance.java |

### 7️⃣ 矩阵 DP
| 题号 | 题目 | 难度 | 文件 |
|------|------|------|------|
| 221 | 最大正方形 | 中等 | P221_MaximalSquare.java |
| 32 | 最长有效括号 | 困难 | P32_LongestValidParentheses.java |

## 🚀 快速开始

### 运行单个题目

每个题目文件都包含一个 `main` 方法，可以直接运行：

```bash
# 编译
javac -d bin src/main/java/com/leetcode/dp/utils/DPVisualizer.java
javac -d bin -cp bin src/main/java/com/leetcode/dp/problems/P70_ClimbingStairs.java

# 运行
java -cp bin com.leetcode.dp.problems.P70_ClimbingStairs
```

### 使用可视化功能

所有题目都提供了可视化版本，只需调用带有 `Visualization` 的方法：

```java
P70_ClimbingStairs solution = new P70_ClimbingStairs();

// 开启可视化
solution.climbStairsWithVisualization(5, true);

// 关闭可视化
solution.climbStairsWithVisualization(5, false);
```

## 🎨 可视化示例

运行爬楼梯问题的可视化输出：

```
═══════════════════════════════════════════════════════════════════════════════
  LeetCode 70. 爬楼梯
═══════════════════════════════════════════════════════════════════════════════

问题: 爬到第 5 阶楼梯有多少种方法？
状态转移方程: dp[i] = dp[i-1] + dp[i-2]

>>> 步骤 0: 初始化基础情况
============================================================
初始化: dp[1]=1, dp[2]=2
索引: [0, 1, 2, 3, 4, 5]
数值: [0, 1, 2, 0, 0, 0]
============================================================

>>> 步骤 1: 计算第 3 阶
  → dp[3] = dp[2] + dp[1] = 2 + 1: dp[3] = 3
============================================================
当前DP数组状态
索引: [0, 1, 2, 3, 4, 5]
数值: [0, 1, 2, 3, 0, 0]
============================================================

✓ 最终结果: 8
```

## 📖 使用说明

### DPVisualizer 工具类

这是一个强大的可视化工具类，提供以下功能：

- `print1DArray()`: 打印一维 DP 数组
- `print2DArray()`: 打印二维 DP 数组
- `printStep()`: 打印当前步骤信息
- `printTitle()`: 打印题目标题
- `printTransition()`: 打印状态转移方程
- `printResult()`: 打印最终结果
- `highlightUpdate()`: 高亮显示更新位置

### 代码示例

```java
// 使用可视化工具
DPVisualizer.printTitle("LeetCode 70. 爬楼梯");
DPVisualizer.printTransition("dp[i] = dp[i-1] + dp[i-2]");
DPVisualizer.print1DArray(dp, "当前DP数组状态");
DPVisualizer.highlightUpdate(i, dp[i], "更新说明");
DPVisualizer.printResult(result);
```

## 💡 学习建议

1. **先理解问题**: 仔细阅读题目描述和示例
2. **看可视化输出**: 运行代码，观察 DP 数组的变化过程
3. **理解状态转移**: 重点关注状态转移方程的推导
4. **对比多种解法**: 理解基础解法和优化解法的区别
5. **手动模拟**: 尝试在纸上模拟小规模数据的执行过程
6. **总结模式**: 归纳同类型题目的解题模式

## 🔍 动态规划核心要素

每道题目的注释都包含以下核心要素：

1. **状态定义**: `dp[i]` 或 `dp[i][j]` 代表什么含义
2. **状态转移方程**: 如何从子问题推导到当前问题
3. **初始化**: 边界条件的处理
4. **遍历顺序**: 确保计算顺序正确
5. **空间优化**: 如何降低空间复杂度

## 📊 复杂度分析

每个解法都标注了：
- ⏰ 时间复杂度
- 💾 空间复杂度

大多数题目提供了空间优化版本，展示如何从二维降到一维，或从一维降到常数空间。

## 🤝 贡献

欢迎提出问题、建议或贡献代码！

## 📄 许可

本项目仅供学习交流使用。

## 🙏 致谢

感谢 LeetCode 提供的优质题目平台。

---

**Happy Coding! 祝你学习愉快！** 🎉
