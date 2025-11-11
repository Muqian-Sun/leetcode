package com.muqian.leetcode.dp.path;

import com.muqian.leetcode.dp.DPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeetCode 62. 不同路径
 *
 * 题目描述：
 * 一个机器人位于一个 m x n 网格的左上角（起始点在下图中标记为 "Start" ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 "Finish" ）。
 * 问总共有多少条不同的路径？
 *
 * 示例 1:
 * 输入：m = 3, n = 7
 * 输出：28
 *
 * 示例 2:
 * 输入：m = 3, n = 2
 * 输出：3
 * 解释：
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向下
 *
 * ============================================================================
 * 💡 思路讲解：如何想到用动态规划？
 * ============================================================================
 *
 * 【第1步】分析问题特征
 * - 问题：从左上角到右下角有多少条路径？
 * - 约束：只能向右或向下移动
 * - 关键观察：要到达位置(i,j)，只有两种可能：
 *   → 从上方(i-1,j)向下走一步
 *   → 从左方(i,j-1)向右走一步
 * - 发现：到达(i,j)的路径数 = 到达(i-1,j)的路径数 + 到达(i,j-1)的路径数
 * - 这是典型的"大问题可以由小问题组合而成"，符合动态规划特征！
 *
 * 【第2步】定义状态
 * - 思考：我们要求的是什么？→ 到达某个位置的路径数
 * - 定义：dp[i][j] = 从起点(0,0)到达位置(i,j)的路径数
 * - 这个定义很直观，直接描述了问题本身
 *
 * 【第3步】推导状态转移方程
 * - 站在位置(i,j)，思考：我是怎么到这里的？
 *   → 可能性1：从上方(i-1,j)来的，路径数为 dp[i-1][j]
 *   → 可能性2：从左方(i,j-1)来的，路径数为 dp[i][j-1]
 * - 由于这两种方式是互不重叠的（要么从上来，要么从左来）
 * - 总路径数 = 两种可能性之和
 * - 得出：dp[i][j] = dp[i-1][j] + dp[i][j-1]
 *
 * 【第4步】确定初始状态（边界条件）
 * - 第一行 dp[0][j]：只能一直向右走，所以都是1
 * - 第一列 dp[i][0]：只能一直向下走，所以都是1
 * - 起点 dp[0][0] = 1
 *
 * 【第5步】确定计算顺序
 * - 因为dp[i][j]依赖dp[i-1][j]和dp[i][j-1]
 * - 必须从上到下、从左到右计算（保证依赖的子问题已经解决）
 *
 * 💡 为什么是加法？
 * - 因为从上方来和从左方来是"或"的关系（两种独立的路径来源）
 * - 计数问题中，"或"对应加法，"且"对应乘法
 *
 * 💡 这道题的精髓
 * - 二维DP的经典入门题
 * - 路径计数问题的标准模板
 * - 体现了"无后效性"：到达(i,j)只关心有多少条路径，不关心具体怎么走的
 *
 * ============================================================================
 * 方法1：二维动态规划
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(m * n)
 *
 * 方法2：空间优化
 * - 观察：计算第i行时，只需要第i-1行的数据
 * - 优化：使用一维滚动数组
 * 时间复杂度：O(m * n)
 * 空间复杂度：O(n)
 * ============================================================================
 *
 * @author muqian
 */
public class UniquePaths {
    private static final Logger log = LoggerFactory.getLogger(UniquePaths.class);

    private int stepCounter = 0;

    /**
     * 方法1：二维动态规划
     *
     * @param m 网格行数
     * @param n 网格列数
     * @return 不同路径数
     */
    public int uniquePaths(int m, int n) {
        log.info("\n╔══════════════════════════════════════════════════════════════════╗");
        log.info("║  LeetCode 62. 不同路径 - 二维动态规划详解                      ║");
        log.info("╚══════════════════════════════════════════════════════════════════╝");
        log.info("\n【问题】网格大小：{} × {}，求从左上角到右下角的路径数\n", m, n);

        log.info("【思考过程】");
        log.info("  💭 如何到达位置(i,j)？");
        log.info("     → 从上方(i-1,j)向下走一步");
        log.info("     → 从左方(i,j-1)向右走一步");
        log.info("  💡 路径数相加：dp[i][j] = dp[i-1][j] + dp[i][j-1]");
        log.info("  ✓ 这是二维DP路径计数的标准模板\n");

        int[][] dp = new int[m][n];

        // 初始化第一行和第一列
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        stepCounter = 0;

        log.info("【初始化】");
        log.info("  第一行：只能一直向右走，所以都是1");
        log.info("  第一列：只能一直向下走，所以都是1");
        log.info("  DP数组:{}", DPUtils.format2DArray(dp));

        log.info("\n【动态规划过程】从位置(1,1)开始，逐行逐列计算");
        log.info("─".repeat(70));

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int fromTop = dp[i - 1][j];
                int fromLeft = dp[i][j - 1];
                dp[i][j] = fromTop + fromLeft;

                if ((i <= 2 && j <= 2) || (i == m-1 && j == n-1)) {
                    log.info("\n[步骤{}] 计算位置({}, {})", ++stepCounter, i, j);
                    log.info("│");
                    log.info("│  💭 思考：从哪里可以到达({},{})?", i, j);
                    log.info("│     路径来源1：从上方({},{})向下 → 有 {} 条路径", i-1, j, fromTop);
                    log.info("│     路径来源2：从左方({},{})向右 → 有 {} 条路径", i, j-1, fromLeft);
                    log.info("│");
                    log.info("│  ✓ 总路径数 = {} + {} = {}", fromTop, fromLeft, dp[i][j]);
                    log.info("│     公式：dp[{}][{}] = dp[{}][{}] + dp[{}][{}]",
                            i, j, i-1, j, i, j-1);

                    if (i <= 2 && j <= 2) {
                        log.info("│");
                        log.info("│  当前DP数组:{}", DPUtils.format2DArray(dp));
                    }
                    log.info("─".repeat(70));
                }
            }
        }

        log.info("\n【最终DP数组】");
        log.info(DPUtils.format2DArray(dp));

        log.info("\n【结果】");
        log.info("  从({},{})到({},{})共有 {} 条不同路径", 0, 0, m-1, n-1, dp[m-1][n-1]);
        log.info("  （通过二维DP，在O(m*n)时间内求解）");
        log.info("\n{}", DPUtils.printSummary(stepCounter, dp[m - 1][n - 1]));

        return dp[m - 1][n - 1];
    }

    /**
     * 方法2：一维空间优化
     *
     * @param m 网格行数
     * @param n 网格列数
     * @return 不同路径数
     */
    public int uniquePathsOptimized(int m, int n) {
        log.info("\n========== 开始计算不同路径数（一维空间优化） ==========");
        log.info("网格大小：{} × {}", m, n);
        log.info("优化：使用一维数组滚动更新\n");

        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        stepCounter = 0;

        log.info("[步骤{}] 初始化：dp = {}", ++stepCounter, DPUtils.format1DArray(dp));

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j - 1];
            }

            if (i <= 2 || i == m - 1) {
                log.info("\n[步骤{}] 处理第 {} 行", ++stepCounter, i);
                log.info("│   dp = {}", DPUtils.format1DArray(dp));
            }
        }

        log.info("\n{}", DPUtils.printSummary(stepCounter, dp[n - 1]));
        log.info("\n========== 计算完成 ==========\n");

        return dp[n - 1];
    }
}
