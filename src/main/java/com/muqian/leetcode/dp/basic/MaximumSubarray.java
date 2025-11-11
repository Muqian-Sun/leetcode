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
 * 解题思路：
 *
 * 方法1：动态规划（Kadane算法）
 * - 状态定义：dp[i] 表示以 nums[i] 结尾的最大子数组和
 * - 状态转移方程：dp[i] = max(dp[i-1] + nums[i], nums[i])
 *   - 延续之前的子数组：dp[i-1] + nums[i]
 *   - 重新开始：nums[i]
 * - 最终答案：max(dp[0..n-1])
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)，可优化到O(1)
 *
 * 方法2：分治法
 * - 将数组分成左右两部分
 * - 最大子数组和可能在：左半部分、右半部分、或跨越中点
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(log n)
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
        log.info("\n========== 开始计算最大子数组和（动态规划） ==========");
        log.info("数组：{}", DPUtils.arrayToString(nums));
        log.info("状态转移方程：dp[i] = max(dp[i-1] + nums[i], nums[i])");
        log.info("说明：dp[i]表示以nums[i]结尾的最大子数组和\n");

        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int maxSum = dp[0];

        stepCounter = 0;

        log.info("[步骤{}] 初始化", ++stepCounter);
        log.info("│   dp[0] = nums[0] = {}", dp[0]);
        log.info("│   maxSum = {}", maxSum);
        log.info("│   DP数组: {}\n", DPUtils.format1DArray(dp));

        for (int i = 1; i < n; i++) {
            int extendPrev = dp[i - 1] + nums[i];
            int startNew = nums[i];
            dp[i] = Math.max(extendPrev, startNew);
            int prevMax = maxSum;
            maxSum = Math.max(maxSum, dp[i]);

            log.info("[步骤{}] 处理位置 {} (值={})", ++stepCounter, i, nums[i]);
            log.info("│   方案1 - 延续之前子数组: dp[{}] + nums[{}] = {} + {} = {}",
                    i-1, i, dp[i-1], nums[i], extendPrev);
            log.info("│   方案2 - 重新开始: nums[{}] = {}", i, startNew);
            log.info("│   dp[{}] = max({}, {}) = {}", i, extendPrev, startNew, dp[i]);

            if (maxSum != prevMax) {
                log.info("│   ✓ 更新最大和: {} → {}", prevMax, maxSum);
            } else {
                log.info("│   当前最大和: {}", maxSum);
            }

            log.info("│   DP数组: {}\n", DPUtils.format1DArray(dp));
        }

        log.info("{}", DPUtils.printSummary(stepCounter, maxSum));
        log.info("\n========== 计算完成 ==========\n");

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
