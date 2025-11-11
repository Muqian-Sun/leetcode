package com.muqian.leetcode.dp.basic;

import com.muqian.leetcode.dp.DPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeetCode 70. 爬楼梯
 *
 * 题目描述：
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 示例 1:
 * 输入: n = 2
 * 输出: 2
 * 解释: 有两种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶
 * 2. 2 阶
 *
 * 示例 2:
 * 输入: n = 3
 * 输出: 3
 * 解释: 有三种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶 + 1 阶
 * 2. 1 阶 + 2 阶
 * 3. 2 阶 + 1 阶
 *
 * ============================================================================
 * 💡 思路讲解：如何想到用动态规划？
 * ============================================================================
 *
 * 【第1步】分析问题特征
 * - 问题：爬到第n阶有多少种方法？
 * - 关键观察：要到达第n阶，最后一步要么是从第(n-1)阶爬1步，要么是从第(n-2)阶爬2步
 * - 发现：大问题可以分解成小问题！这是动态规划的信号
 *
 * 【第2步】定义状态（最重要的一步！）
 * - 思考：我们要求的是什么？→ 爬到第n阶的方法数
 * - 定义：dp[i] = 爬到第i阶的方法数
 * - 为什么这样定义？因为这样可以描述"到达某个阶数"的所有可能性
 *
 * 【第3步】推导状态转移方程（核心思想）
 * - 站在第i阶，想一想：我是怎么到这里的？
 *   → 可能性1：从第(i-1)阶爬1步上来，这种情况有 dp[i-1] 种方法
 *   → 可能性2：从第(i-2)阶爬2步上来，这种情况有 dp[i-2] 种方法
 * - 总方法数 = 两种可能性之和
 * - 得出：dp[i] = dp[i-1] + dp[i-2]
 *
 * 【第4步】确定初始状态
 * - dp[1] = 1：爬到第1阶只有1种方法（爬1步）
 * - dp[2] = 2：爬到第2阶有2种方法（爬1步+爬1步，或爬2步）
 *
 * 【第5步】确定计算顺序
 * - 因为dp[i]依赖dp[i-1]和dp[i-2]，所以要从小到大计算
 *
 * 💡 为什么是加法而不是乘法？
 * - 因为两种到达方式是"或"的关系（要么从i-1来，要么从i-2来）
 * - "或"对应加法，"且"对应乘法
 *
 * 💡 发现规律：这就是斐波那契数列！
 * - F(1)=1, F(2)=2, F(n)=F(n-1)+F(n-2)
 *
 * ============================================================================
 * 方法1：动态规划（完整版本）
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 方法2：空间优化
 * - 观察：dp[i]只依赖dp[i-1]和dp[i-2]，不需要保存整个数组
 * - 优化：只用两个变量滚动更新
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 * ============================================================================
 *
 * @author muqian
 */
public class ClimbingStairs {
    private static final Logger log = LoggerFactory.getLogger(ClimbingStairs.class);

    private int stepCounter = 0;

    /**
     * 方法1：动态规划
     *
     * @param n 楼梯阶数
     * @return 爬到楼顶的方法数
     */
    public int climbStairs(int n) {
        log.info("\n╔══════════════════════════════════════════════════════════════════╗");
        log.info("║  LeetCode 70. 爬楼梯 - 动态规划思路详解                         ║");
        log.info("╚══════════════════════════════════════════════════════════════════╝");
        log.info("\n【问题】爬到第 {} 阶楼梯有多少种方法？（每次只能爬1或2阶）\n", n);

        log.info("【思考过程】");
        log.info("  💭 如何到达第n阶？");
        log.info("     → 从第(n-1)阶爬1步，或");
        log.info("     → 从第(n-2)阶爬2步");
        log.info("  💡 发现：到达第n阶的方法数 = 到达第(n-1)阶的方法数 + 到达第(n-2)阶的方法数");
        log.info("  ✓ 得出状态转移方程：dp[i] = dp[i-1] + dp[i-2]\n");

        if (n <= 2) {
            log.info("【特殊情况】n <= 2，直接返回 {}", n);
            log.info("\n========== 计算完成 ==========\n");
            return n;
        }

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        stepCounter = 0;

        log.info("【初始化】");
        log.info("  dp[1] = 1  （爬到第1阶只有1种方法：爬1步）");
        log.info("  dp[2] = 2  （爬到第2阶有2种方法：1+1 或 2）");
        log.info("  DP数组: {}\n", DPUtils.format1DArray(dp));

        log.info("【动态规划过程】从第3阶开始，利用前面的结果计算");
        log.info("─".repeat(70));

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];

            log.info("\n[步骤{}] 计算第 {} 阶的方法数", ++stepCounter, i);
            log.info("│");
            log.info("│  💡 思考：要到达第{}阶，我可以从哪里来？", i);
            log.info("│     方式1: 从第{}阶爬1步 → 有 dp[{}] = {} 种方法", i-1, i-1, dp[i-1]);
            log.info("│     方式2: 从第{}阶爬2步 → 有 dp[{}] = {} 种方法", i-2, i-2, dp[i-2]);
            log.info("│");
            log.info("│  ✓ 总方法数 = {} + {} = {}", dp[i-1], dp[i-2], dp[i]);
            log.info("│     公式：dp[{}] = dp[{}] + dp[{}]", i, i-1, i-2);
            log.info("│");
            log.info("│  当前DP数组: {}", DPUtils.format1DArray(dp));
            log.info("─".repeat(70));
        }

        log.info("\n【结果】");
        log.info("  爬到第 {} 阶楼梯共有 {} 种方法", n, dp[n]);
        log.info("  （这就是斐波那契数列的第{}项！）", n);
        log.info("\n{}", DPUtils.printSummary(stepCounter, dp[n]));

        return dp[n];
    }

    /**
     * 方法2：空间优化的动态规划
     *
     * @param n 楼梯阶数
     * @return 爬到楼顶的方法数
     */
    public int climbStairsOptimized(int n) {
        log.info("\n========== 开始计算爬楼梯方法数（空间优化） ==========");
        log.info("问题：爬到第 {} 阶楼梯有多少种方法？", n);
        log.info("优化：使用两个变量代替数组\n");

        if (n <= 2) {
            log.info("n <= 2，直接返回 {}", n);
            log.info("\n========== 计算完成 ==========\n");
            return n;
        }

        int prev2 = 1;  // dp[i-2]
        int prev1 = 2;  // dp[i-1]
        int current = 0;

        stepCounter = 0;

        log.info("[步骤{}] 初始化：prev2 = 1, prev1 = 2", ++stepCounter);

        for (int i = 3; i <= n; i++) {
            current = prev1 + prev2;

            log.info("\n[步骤{}] 计算第 {} 阶", ++stepCounter, i);
            log.info("│   current = prev1 + prev2");
            log.info("│   current = {} + {} = {}", prev1, prev2, current);
            log.info("│   更新：prev2 = {}, prev1 = {}", prev1, current);

            prev2 = prev1;
            prev1 = current;
        }

        log.info("\n{}", DPUtils.printSummary(stepCounter, current));
        log.info("\n========== 计算完成 ==========\n");

        return current;
    }

    /**
     * 方法3：斐波那契数列（数学方法）
     * 使用通项公式直接计算
     *
     * @param n 楼梯阶数
     * @return 爬到楼顶的方法数
     */
    public int climbStairsMath(int n) {
        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;
        double psi = (1 - sqrt5) / 2;
        return (int) Math.round((Math.pow(phi, n + 1) - Math.pow(psi, n + 1)) / sqrt5);
    }
}
