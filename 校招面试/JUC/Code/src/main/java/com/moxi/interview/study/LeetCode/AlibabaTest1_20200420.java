package com.moxi.interview.study.LeetCode;

import java.util.Scanner;

/**
 * @author: 陌溪
 * @create: 2020-04-20-18:56
 */
public class AlibabaTest1_20200420 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int n = sc.nextInt();
        int pow[] = new int[n];
        for(int i=0; i<n; i++) {
            pow[i] = sc.nextInt();
        }

        for(int i=0; i<pow.length; i++) {
            boolean flag = false;
            for(int j=0; j<pow.length; j++) {
                if(pow[i] < pow[j]) {
                    flag = true;
                    int temp = pow[j];
                    pow[j] = pow[i];
                    pow[i] = temp;
                }
            }
            if(flag) {
                break;
            }
        }

        int money = 0;
        int maxMoney = 0;
        int ability = a;
        while(true && pow.length > 0) {
            // 表示是否进行了战斗
            boolean flag = false;
            for(int i=0; i<pow.length; i++) {
                if(pow[i] == -1) {
                    continue;
                }
                if(pow[i] <= ability) {
                    flag = true;
                    pow[i] = -1;
                    money += 1;
                    if(money > maxMoney) {
                        maxMoney = money;
                    }
                }
            }
            // 最后一个被打败
            if(pow[pow.length -1] == -1) {
                break;
            }

            // 钱花完了，一个怪也没打过
            if(money == 0 && !flag) {
                break;
            }

            // 非负判断
            if(money > 0) {
                // 提升能力，再次战斗
                ability++;
                money --;
            }
        }
        System.out.println(maxMoney);
    }
}
