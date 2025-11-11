package com.muqian.leetcode.backtracking.permutation;

import com.muqian.leetcode.tree.ColorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 47. 全排列 II
 *
 * 题目描述：
 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 *
 * 示例 1:
 * 输入：nums = [1,1,2]
 * 输出：
 * [[1,1,2],
 *  [1,2,1],
 *  [2,1,1]]
 *
 * 示例 2:
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * 解题思路：
 * 有重复元素的全排列，需要在全排列I的基础上加去重逻辑
 *
 * 去重策略：
 * 1. 排序，让相同元素相邻
 * 2. 使用 used 数组标记元素是否已使用
 * 3. 关键去重条件：nums[i] == nums[i-1] && !used[i-1]
 *
 * 去重条件理解：
 * - nums[i] == nums[i-1]：当前元素与前一个元素相同
 * - !used[i-1]：前一个元素未使用
 *   => 说明在"同一层"，需要去重
 *
 * 示例：nums = [1,1,2]
 * 决策树：
 *                      []
 *         /            |            \
 *       [1]           [1]❌         [2]
 *                   (跳过，同层重复)
 *      /   \
 *    [1,2] [1,1]❌
 *           (允许，不同层)
 *
 * 为什么用 !used[i-1] 而不是 used[i-1]：
 * - !used[i-1]：前一个相同元素在同一层，说明是横向遍历的重复
 * - used[i-1]：前一个相同元素在路径中，说明是纵向递归，不应跳过
 *
 * 时间复杂度：O(n × n!)
 * 空间复杂度：O(n)
 *
 * @author muqian
 */
public class PermutationsII {
    private static final Logger log = LoggerFactory.getLogger(PermutationsII.class);

    private List<List<Integer>> result;
    private List<Integer> path;
    private boolean[] used;
    private int stepCounter;
    private int skipCount;

    public List<List<Integer>> permuteUnique(int[] nums) {
        log.info("\n========== 开始生成全排列（含去重） ==========");
        log.info("输入数组: {}", arrayToString(nums));

        result = new ArrayList<>();
        path = new ArrayList<>();
        used = new boolean[nums.length];
        stepCounter = 0;
        skipCount = 0;

        // 排序是去重的前提
        Arrays.sort(nums);
        log.info("排序后: {}\n", arrayToString(nums));

        backtrack(nums, 0);

        log.info("\n{} 统计信息:", ColorUtils.phase(""));
        log.info("  找到排列数: {}", stepCounter);
        log.info("  跳过重复数: {}", ColorUtils.success(String.valueOf(skipCount)));

        log.info("\n最终结果（共 {} 个排列）：", result.size());
        for (int i = 0; i < result.size(); i++) {
            log.info("  [{}] {}", i + 1, result.get(i));
        }

        log.info("\n========== 生成完成 ==========\n");
        return result;
    }

    private void backtrack(int[] nums, int depth) {
        String indent = "  ".repeat(depth);

        // 终止条件
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            log.info("{}{} {} 找到排列: {}",
                    indent,
                    ColorUtils.step(++stepCounter),
                    ColorUtils.success("✓"),
                    ColorUtils.highlight(path.toString()));
            return;
        }

        // 显示决策树节点
        log.info("{}╔═══ 决策树节点 (深度:{}) ═══",
                indent, ColorUtils.info(String.valueOf(depth)));
        log.info("{}║ 当前路径: {} (长度:{})",
                indent, ColorUtils.highlight(path.toString()), path.size());
        log.info("{}║ 已使用: {}",
                indent, ColorUtils.info(usedToString(nums, used)));
        log.info("{}╚═══", indent);

        for (int i = 0; i < nums.length; i++) {
            // 跳过已使用的元素
            if (used[i]) {
                continue;
            }

            // 去重：跳过同层的重复元素
            // nums[i] == nums[i-1] && !used[i-1] 表示同一层的重复
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                skipCount++;
                log.info("{}├─ {} 跳过重复 {} (同层: nums[{}]==nums[{}] && !used[{}])",
                        indent,
                        ColorUtils.error("✗"),
                        nums[i],
                        i,
                        i - 1,
                        i - 1);
                continue;
            }

            path.add(nums[i]);
            used[i] = true;
            log.info("{}├─ {} 选择 {} → 进入子树",
                    indent,
                    ColorUtils.highlight("▶"),
                    ColorUtils.node(nums[i]));

            backtrack(nums, depth + 1);

            path.remove(path.size() - 1);
            used[i] = false;
            log.info("{}└─ {} 回溯，移除 {} 并标记未使用",
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

    private String usedToString(int[] nums, boolean[] used) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < used.length; i++) {
            if (used[i]) {
                if (!first) sb.append(",");
                sb.append(nums[i]);
                first = false;
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
