package com.moxi.interview.study.LeetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 找出重复的数
 *
 * @author: 陌溪
 * @create: 2020-06-26-15:17
 */
public class FindRepeatNumber_03 {
    public int findRepeatNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap();
        for(int a=0; a<nums.length; a++) {
            if(map.get(nums[a]) != null) {
                return nums[a];
            } else {
                map.put(nums[a], 1);
            }
        }
        return 0;
    }
}
