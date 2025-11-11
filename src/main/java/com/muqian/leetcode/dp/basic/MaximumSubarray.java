package com.muqian.leetcode.dp.basic;

import com.muqian.leetcode.dp.DPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeetCode 53. 最大子数组和
 *
 * 题目描述：
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），
 * 返回其最大和。
 *
 * 子数组是数组中的一个连续部分。
 *
 * 示例 1:
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 *
 * 示例 2:
 * 输入：nums = [1]
 * 输出：1
 *
 * 示例 3:
 * 输入：nums = [5,4,-1,7,8]
 * 输出：23
 *
 * ============================================================================
 * 💡 思路讲解：如何想到用动态规划（Kadane算法）？
 * ============================================================================
 *
 * 【第1步】分析问题特征
 * - 问题：找出连续子数组的最大和
 * - 关键约束：子数组必须是连续的
 * - 难点：暴力枚举所有子数组需要O(n²)或O(n³)，太慢了
 * - 关键观察：当我们遍历到第i个元素时，思考：
 *   → 要不要把这个元素加入到之前的子数组中？
 *   → 还是从这个元素重新开始一个新的子数组？
 * - 发现：每个位置的决策依赖于前一个位置的结果！这是DP的信号
 *
 * 【第2步】定义状态（关键！）
 * - 思考：我们要求的是什么？→ 最大子数组和
 * - 但直接定义"前i个元素的最大子数组和"行不通，因为无法建立递推关系
 * - 巧妙转换：定义 dp[i] = 以 nums[i] 结尾的最大子数组和
 * - 为什么这样定义？因为"必须包含当前元素"这个约束让递推变得简单！
 *
 * 【第3步】推导状态转移方程
 * - 站在第i个位置，我有两个选择：
 *   → 选择1：把nums[i]加入到前面的子数组中，获得 dp[i-1] + nums[i]
 *   → 选择2：从nums[i]重新开始一个新的子数组，获得 nums[i]
 * - 什么时候选择1？当 dp[i-1] > 0 时（前面有正贡献）
 * - 什么时候选择2？当 dp[i-1] <= 0 时（前面是负贡献，不如重新开始）
 * - 得出：dp[i] = max(dp[i-1] + nums[i], nums[i])
 *
 * 【第4步】确定初始状态
 * - dp[0] = nums[0]：只有第一个元素时，最大和就是它自己
 *
 * 【第5步】确定最终答案
 * - 注意！dp[i]表示"以i结尾"的最大和，不是"前i个"的最大和
 * - 所以最终答案是：max(dp[0], dp[1], ..., dp[n-1])
 * - 需要在计算过程中维护一个全局最大值
 *
 * 💡 为什么这个算法叫Kadane算法？
 * - 由计算机科学家Jay Kadane在1984年发明
 * - 巧妙之处：将O(n²)暴力枚举优化到O(n)单次遍历
 * - 核心思想：局部最优 → 全局最优
 *
 * 💡 这道题的精髓
 * - 状态定义的巧妙转换："必须以i结尾" 而不是 "前i个元素"
 * - 这个转换让递推关系变得简单明了
 * - 贪心的思想：当前面的和为负数时，果断放弃，重新开始
 *
 * ============================================================================
 * 方法1：动态规划（Kadane算法）
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)，可优化到O(1)
 *
 * 方法2：分治法
 * - 将数组分成左右两部分，最大和可能在：左半、右半、或跨越中点
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(log n)
 * ============================================================================
 *
 * @author muqian
 */
public class MaximumSubarray {
    private static final Logger log = LoggerFactory.getLogger(MaximumSubarray.class);

    private int stepCounter = 0;

    /**
     * 方法1：动态规划（Kadane算法）
     *
     * @param nums 整数数组
     * @return 最大子数组和
     */
    public int maxSubArray(int[] nums) {
        log.info("\n╔══════════════════════════════════════════════════════════════════╗");
        log.info("║  LeetCode 53. 最大子数组和 - Kadane算法详解                    ║");
        log.info("╚══════════════════════════════════════════════════════════════════╝");
        log.info("\n【问题】数组：{}，求连续子数组的最大和\n", DPUtils.arrayToString(nums));

        log.info("【思考过程】");
        log.info("  💭 遍历到第i个元素时的决策：");
        log.info("     → 把nums[i]加入前面的子数组？（如果前面有正贡献）");
        log.info("     → 从nums[i]重新开始？（如果前面是负贡献）");
        log.info("  💡 状态定义：dp[i] = 以nums[i]结尾的最大子数组和");
        log.info("  ✓ 状态转移：dp[i] = max(dp[i-1] + nums[i], nums[i])\n");

        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int maxSum = dp[0];

        stepCounter = 0;

        log.info("【初始化】");
        log.info("  dp[0] = {} (第一个元素)", dp[0]);
        log.info("  maxSum = {} (全局最大和)", maxSum);
        log.info("  DP数组: {}\n", DPUtils.format1DArray(dp));

        log.info("【Kadane算法过程】从第1个位置开始决策");
        log.info("─".repeat(70));

        for (int i = 1; i < n; i++) {
            int extendPrev = dp[i - 1] + nums[i];
            int startNew = nums[i];
            dp[i] = Math.max(extendPrev, startNew);
            int prevMax = maxSum;
            maxSum = Math.max(maxSum, dp[i]);

            log.info("\n[步骤{}] 处理位置 {} (值 = {})", ++stepCounter, i, nums[i]);
            log.info("│");
            log.info("│  💭 思考：是延续前面的子数组，还是重新开始？");
            log.info("│     方案1 - 延续：dp[{}] + {} = {} + {} = {}",
                    i-1, nums[i], dp[i-1], nums[i], extendPrev);
            log.info("│     方案2 - 重新开始：{}", startNew);
            log.info("│");

            String decision;
            if (dp[i] == extendPrev) {
                decision = "延续前面的子数组";
                if (dp[i-1] > 0) {
                    log.info("│  💡 因为 dp[{}] = {} > 0，前面有正贡献，选择延续", i-1, dp[i-1]);
                } else {
                    log.info("│  💡 虽然 dp[{}] = {} <= 0，但延续后仍是最佳选择", i-1, dp[i-1]);
                }
            } else {
                decision = "重新开始新子数组";
                log.info("│  💡 因为 dp[{}] = {} <= 0，前面是负贡献，选择重新开始", i-1, dp[i-1]);
            }

            log.info("│  ✓ dp[{}] = {} (决策：{})", i, dp[i], decision);
            log.info("│");

            if (maxSum != prevMax) {
                log.info("│  🎯 更新全局最大和: {} → {}", prevMax, maxSum);
            } else {
                log.info("│  全局最大和: {}", maxSum);
            }

            log.info("│");
            log.info("│  当前DP数组: {}", DPUtils.format1DArray(dp));
            log.info("─".repeat(70));
        }

        log.info("\n【结果】");
        log.info("  最大子数组和 = {}", maxSum);
        log.info("  （通过 {} 步决策，利用Kadane算法在O(n)时间内求解）", stepCounter);
        log.info("\n{}", DPUtils.printSummary(stepCounter, maxSum));

        return maxSum;
    }

    /**
     * 方法2：空间优化的动态规划
     *
     * @param nums 整数数组
     * @return 最大子数组和
     */
    public int maxSubArrayOptimized(int[] nums) {
        log.info("\n========== 开始计算最大子数组和（空间优化） ==========");
        log.info("数组：{}", DPUtils.arrayToString(nums));
        log.info("优化：使用单个变量代替数组\n");

        int currentSum = nums[0];
        int maxSum = nums[0];

        stepCounter = 0;

        log.info("[步骤{}] 初始化：currentSum = {}, maxSum = {}", ++stepCounter, currentSum, maxSum);

        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(currentSum + nums[i], nums[i]);
            int prevMax = maxSum;
            maxSum = Math.max(maxSum, currentSum);

            log.info("\n[步骤{}] 处理位置 {} (值={})", ++stepCounter, i, nums[i]);
            log.info("│   currentSum = max({} + {}, {}) = {}",
                    currentSum - Math.max(currentSum + nums[i], nums[i]) + nums[i - 1],
                    nums[i], nums[i], currentSum);
            log.info("│   maxSum = {}", maxSum);
        }

        log.info("\n{}", DPUtils.printSummary(stepCounter, maxSum));
        log.info("\n========== 计算完成 ==========\n");

        return maxSum;
    }
}
