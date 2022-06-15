/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package backtracking;

/**
 * 正则表达式匹配
 * ?-匹配0/1个任意字符
 * *-匹配多个任意字符
 *
 * @author linjie
 * @version : Pattern.java, v 0.1 2022年06月13日 7:49 下午 linjie Exp $
 */
public class Pattern {
    private boolean isMatch = false;

    public boolean isMatch(String s, String p) {
        bt_match(0, 0, s, p);
        return isMatch;
    }

    private void bt_match(int si, int pi, String s, String p) {
        if (isMatch) {
            return;
        }
        if (pi == p.length()) {
            if (si == s.length()) {
                //匹配到结尾
                isMatch = true;
            }
            return;
        }
        if (p.charAt(pi) == '?') {
            bt_match(si, pi + 1, s, p);
            bt_match(si + 1, pi + 1, s, p);
        } else if (p.charAt(pi) == '*') {
            for (int i = si; i <= s.length(); i++) {
                bt_match(i, pi + 1, s, p);
            }
        } else if ((si < s.length()) && (p.charAt(pi) == s.charAt(si))){
            bt_match(si + 1, pi + 1, s, p);
        }
    }

    public static void main(String[] args) {
        Pattern pattern = new Pattern();
        System.out.println(pattern.isMatch("ac", "a******b"));
    }
}