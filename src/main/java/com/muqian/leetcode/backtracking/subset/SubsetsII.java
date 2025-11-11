package com.muqian.leetcode.backtracking.subset;

import com.muqian.leetcode.backtracking.BacktrackUtils;
import com.muqian.leetcode.tree.ColorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 90. 子集 II
 *
 * 题目描述：
 * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。
 * 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
 *
 * 示例 1:
 * 输入：nums = [1,2,2]
 * 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
 *
 * 示例 2:
 * 输入：nums = [0]
 * 输出：[[],[0]]
 *
 * 解题思路：
 * 与子集I的区别在于数组中有重复元素，需要去重
 *
 * 去重策略：
 * 1. 先排序，让相同元素相邻
 * 2. 在"同一层"跳过相同元素：i > start && nums[i] == nums[i-1]
 *
 * 关键理解"同层"vs"同路径"：
 * - 同层：for 循环中的元素（横向遍历）
 * - 同路径：递归栈中的元素（纵向遍历）
 *
 * 示例：nums = [1,2,2]
 * 决策树：
 *                   []
 *        /          |          \
 *      [1]         [2]      [2]❌跳过（同层重复）
 *     /   \       /   \
 *  [1,2] [1,2]  [2,2]
 *    |
 * [1,2,2]（不跳过，因为是同路径，不是同层）
 *
 * 判断条件：i > start 而不是 i > 0
 * - i > start：同层去重
 * - i > 0：会错误地把同路径的也去掉
 *
 * 时间复杂度：O(n × 2^n)
 * 空间复杂度：O(n)
 *
 * @author muqian
 */
public class SubsetsII {
    private static final Logger log = LoggerFactory.getLogger(SubsetsII.class);

    private List<List<Integer>> result;
    private List<Integer> path;
    private int stepCounter;
    private int skipCount;

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        log.info("\n========== 开始生成所有子集（含去重） ==========");
        log.info("输入数组: {}", arrayToString(nums));

        result = new ArrayList<>();
        path = new ArrayList<>();
        stepCounter = 0;
        skipCount = 0;

        // 排序是去重的前提
        Arrays.sort(nums);
        log.info("排序后: {}\n", arrayToString(nums));

        backtrack(nums, 0, 0);

        log.info("\n{} 统计信息:", ColorUtils.phase(""));
        log.info("  收集子集数: {}", stepCounter);
        log.info("  跳过重复数: {}", ColorUtils.success(String.valueOf(skipCount)));

        log.info("\n最终结果（共 {} 个子集）：", result.size());
        for (int i = 0; i < result.size(); i++) {
            log.info("  [{}] {}", i + 1, result.get(i));
        }

        log.info("\n========== 生成完成 ==========\n");
        return result;
    }

    private void backtrack(int[] nums, int start, int depth) {
        String indent = "  ".repeat(depth);

        // 显示当前决策树节点状态
        log.info("{}╔═══ 决策树节点 (深度:{}) ═══",
                indent, ColorUtils.info(String.valueOf(depth)));
        log.info("{}║ 当前路径: {}",
                indent, ColorUtils.highlight(path.toString()));
        log.info("{}║ 可选元素: {}",
                indent, ColorUtils.info(BacktrackUtils.formatChoices(nums, start)));
        log.info("{}╚═══", indent);

        // 收集子集
        result.add(new ArrayList<>(path));
        log.info("{}{} {} 收集子集: {}",
                indent,
                ColorUtils.step(++stepCounter),
                ColorUtils.success("✓"),
                ColorUtils.highlight(path.toString()));

        for (int i = start; i < nums.length; i++) {
            // 去重：跳过同层的重复元素
            if (i > start && nums[i] == nums[i - 1]) {
                skipCount++;
                log.info("{}├─ {} 跳过重复元素 {} (同层去重: nums[{}] == nums[{}])",
                        indent,
                        ColorUtils.error("✗"),
                        nums[i],
                        i,
                        i - 1);
                continue;
            }

            path.add(nums[i]);
            log.info("{}├─ {} 选择 {} → 进入子树",
                    indent,
                    ColorUtils.highlight("▶"),
                    ColorUtils.node(nums[i]));

            backtrack(nums, i + 1, depth + 1);

            path.remove(path.size() - 1);
            log.info("{}└─ {} 回溯，移除 {}",
                    indent,
                    ColorUtils.highlight("◀"),
                    nums[i]);
        }
    }

    private String arrayToString(int[] nums) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < nums.length; i++) {
            sb.append(ColorUtils.node(nums[i]));
            if (i < nums.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
