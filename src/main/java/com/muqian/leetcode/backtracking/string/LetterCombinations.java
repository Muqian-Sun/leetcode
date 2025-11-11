package com.muqian.leetcode.backtracking.string;

import com.muqian.leetcode.tree.ColorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 17. 电话号码的字母组合
 *
 * 题目描述：
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 *
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * 2: abc
 * 3: def
 * 4: ghi
 * 5: jkl
 * 6: mno
 * 7: pqrs
 * 8: tuv
 * 9: wxyz
 *
 * 示例 1:
 * 输入：digits = "23"
 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * 示例 2:
 * 输入：digits = ""
 * 输出：[]
 *
 * 示例 3:
 * 输入：digits = "2"
 * 输出：["a","b","c"]
 *
 * 解题思路：
 * 这是一个典型的字符串回溯问题：
 * 1. 每个数字对应若干个字母，需要从中选择一个
 * 2. 使用 index 表示当前处理第几个数字
 * 3. 每个数字的所有字母都要尝试
 *
 * 决策树分析（以 "23" 为例）：
 *                       ""
 *          /            |            \
 *        "a"          "b"           "c"
 *       /|\           /|\            /|\
 *     d e f         d e f          d e f
 *     | | |         | | |          | | |
 *    ad ae af      bd be bf       cd ce cf
 *
 * 关键点：
 * - 使用 Map 存储数字到字母的映射
 * - StringBuilder 优化字符串拼接
 * - index 表示当前处理第几个数字（不是字符在数组中的位置）
 * - 边界条件：digits 为空时返回空列表
 *
 * 时间复杂度：O(3^m × 4^n)，m 是对应3个字母的数字个数，n 是对应4个字母的数字个数
 * 空间复杂度：O(m + n)，递归栈深度
 *
 * @author muqian
 */
public class LetterCombinations {
    private static final Logger log = LoggerFactory.getLogger(LetterCombinations.class);

    // 数字到字母的映射
    private static final Map<Character, String> PHONE_MAP = new HashMap<>() {{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jkl");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
    }};

    private List<String> result;
    private StringBuilder path;
    private int stepCounter;

    /**
     * 生成所有字母组合（主方法）
     *
     * @param digits 数字字符串
     * @return 所有可能的字母组合
     */
    public List<String> letterCombinations(String digits) {
        log.info("\n========== 开始生成字母组合 ==========");
        log.info("输入数字: {}", ColorUtils.highlight("\"" + digits + "\""));

        result = new ArrayList<>();

        // 边界条件：空字符串
        if (digits == null || digits.length() == 0) {
            log.info("输入为空，返回空列表");
            log.info("\n========== 生成完成 ==========\n");
            return result;
        }

        // 显示数字到字母的映射
        log.info("\n数字映射：");
        for (char c : digits.toCharArray()) {
            log.info("  {} → {}", ColorUtils.node(c), ColorUtils.info(PHONE_MAP.get(c)));
        }

        path = new StringBuilder();
        stepCounter = 0;

        log.info("\n");
        backtrack(digits, 0, 0);

        log.info("\n最终结果（共 {} 个组合）：", result.size());
        for (int i = 0; i < result.size(); i++) {
            if (i % 10 == 0) log.info("");
            System.out.printf("  %-4s", "\"" + result.get(i) + "\"");
            if ((i + 1) % 10 == 0 || i == result.size() - 1) {
                System.out.println();
            }
        }

        log.info("\n========== 生成完成 ==========\n");
        return result;
    }

    /**
     * 回溯算法核心方法
     *
     * @param digits 数字字符串
     * @param index 当前处理第几个数字
     * @param depth 递归深度（用于日志缩进）
     */
    private void backtrack(String digits, int index, int depth) {
        String indent = "  ".repeat(depth);

        // 终止条件：处理完所有数字
        if (index == digits.length()) {
            String combination = path.toString();
            result.add(combination);
            log.info("{}{} {} 找到组合: {}",
                    indent,
                    ColorUtils.step(++stepCounter),
                    ColorUtils.success("✓"),
                    ColorUtils.highlight("\"" + combination + "\""));
            return;
        }

        // 获取当前数字对应的字母
        char digit = digits.charAt(index);
        String letters = PHONE_MAP.get(digit);

        log.info("{}处理数字 {} → 可选字母: {}",
                indent,
                ColorUtils.node(digit),
                ColorUtils.info(letters));

        // 遍历当前数字对应的所有字母
        for (char letter : letters.toCharArray()) {
            // 做选择
            path.append(letter);
            log.info("{}├─ {} 选择字母 {} → 当前路径: {}",
                    indent,
                    ColorUtils.highlight("▶"),
                    ColorUtils.node(letter),
                    "\"" + path.toString() + "\"");

            // 递归：处理下一个数字
            backtrack(digits, index + 1, depth + 1);

            // 撤销选择（回溯）
            path.deleteCharAt(path.length() - 1);
            log.debug("{}└─ 回溯，移除字母 {}", indent, letter);
        }
    }
}
