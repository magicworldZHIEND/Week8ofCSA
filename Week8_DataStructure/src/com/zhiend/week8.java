package com.zhiend;

import sun.text.normalizer.UCharacter;

import java.util.ArrayList;
import java.util.Random;

/**
 * @Classname : week8 //类名
 * @Description: 1. 字符串规律 2. 找出数组中重复的数字 3. 实现二分查找//描述
 * @Author : ZHIEND //作者
 * @Date : 2022/1/24 16:18//日期
 */
public class week8 {
    static Random random = new Random();
    //测试方法
    public static void test() {
        System.out.println("--------------------第一题--------------------");
        System.out.println(task1("abba", "dog dog dog dog"));
        System.out.println(task1("abba", "dog cat cat fish"));
        System.out.println(task1("aaaa", "dog cat cat dog"));
        System.out.println(task1("abba", "dog cat cat dog"));
        System.out.println("--------------------第二题--------------------");
        System.out.println(task2(new int[]{1, 2, 2, 3,3,0,4,4,4,4,}));
        System.out.println("--------------------第三题--------------------");
        for (int i = 0; i < 5; i++) {
            int target = random.nextInt(15) - 3;
            System.out.println("target:" + target + "\tresult: " + task3(new int[]{0, 4, 5, 6, 8}, target));
        }
    }
    // 程序入口
    public static void main(String[] args) {
        test();
    }
    // 第一题
    /**
     * 1. 字符串规律
     * 两个字符串: pattern 只包含小写字母， str 包含了由单个空格分隔的小写字母。判断 str 是否遵循
     * 相同的规律。这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 str 中的每个非空单词之
     * 间存在着双向连接的对应规律。
     * 输入: pattern = "abba", str = "dog dog dog dog"
     * 输出: false
     * 解释：dog dog dog dog属性aaaa型而非abba
     *
     * 输入:pattern = "abba", str = "dog cat cat fish"
     * 输出: false
     * 解释：dog cat cat fish 不符合 abba 型
     *
     * 思路:
     * 思路:
     * 1. 建立实体类, 即把每种 字符 与 字符串 按顺序一一对应
     * 2. 在输入的字符串中, 与实体类中的字符串相对比, 找到字符串对应的实体类后, 判断其字符 与 此时 在字符数组中对应位置的对应 字符是否正确, 如果不是则错误
     */

    public static boolean task1(String pattern, String str) {
        boolean res = true;
        String strArray[] = str.split(" ");//按照空格进行分离
        char index[] = pattern.toCharArray(); // 将匹配字符串转换为匹配字符数组
        ArrayList<Character> uniqueIndex = new ArrayList<>(); // 创建数组来存储的不同种类的字符
        ArrayList<String> uniqueStr = new ArrayList<>(); // 创建数组来存储的不同种类的字符串
        //情况1 : str 与 pattern 的长度不是相同的, 判定为不符合
        if(strArray.length!=pattern.length()){
            res = false;
        }else{
            for(int i = 0; i< index.length;++i){
                int temp = 1;
                for(int j = 0; j< uniqueIndex.size();++j){
                    if(index[i]==uniqueIndex.get(j)){
                        temp =0 ;
                        break;
                    }//如果在存储种类字符已经存在, 那么就跳出循环, 同时标签temp发生改变,说明这个字符已经存在
                }
                if(temp != 0 ){
                    //判定标签
                    uniqueIndex.add(index[i]);

                }
            }//将字符分类放置如uniqueIndex中
            for(int i = 0; i<strArray.length; ++i){
                int temp = 1;
                for(int j = 0; j< uniqueStr.size(); ++j){
                    if(strArray[i].equals(uniqueStr.get(j))){
                        temp =0 ;
                        break;
                    }//如果在存储种类字符串已经存在, 那么就跳出循环
                }
                if(temp != 0 ){
                    //判定标签, 放入字符串
                    uniqueStr.add(strArray[i]);
                }
            } // 将字符串分类放置如uniqueStr中
        //情况2 : str与 pattern 的元素种类 数量不同, 判定为不符合
            if(uniqueIndex.size()!=uniqueStr.size()){
                res = false;
            }else{
                Emp emp[] = new Emp[uniqueIndex.size()];
                for(int i =0 ; i<uniqueIndex.size();++i){
                    emp[i] = new Emp();//操作数组的对象之前必须进行初始化, 如果这里不进行初始化将会出现空指针报错
                    emp[i].Initialization(uniqueIndex.get(i),uniqueStr.get(i));
                }//将每个str 和对应的 Index 相互对应
                for(int i = 0 ; i < strArray.length; ++i ){
                    for(int j = 0; j < uniqueIndex.size(); ++j){
                        // 在emp数组中找到对应的emp后, 对比此时index 对应的id 如果合适, 那么说明相互匹配,反之为false
                        if(strArray[i].equals(emp[j].getStr())){
                            if(index[i] == emp[j].getId()){
                                continue;
                            }
                            else{
                                res = false;
                                break;
                            }
                        }
                    }
                }//寻找对应的emp并且与同序列位置的index进行比对
            }
        }
        return res;
    }
    // 创建实体类, 用于建立 字符与字符串之间的对应关系:
    public static class Emp{
        private Character id;
        private String str;
        public Emp(){
            id = ' ';
            str = "";
        }
        public void Initialization(Character id , String str){
            this.id = id;
            this.str = str;
        }

        public Character getId() {
            return id;
        }

        public String getStr() {
            return str;
        }
    }
    // 第二题
    /**
     * 2. 找出数组中重复的数字
     * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不
     * 知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
     * 输入： [2, 3, 1, 0, 2, 5, 3] 输出：2
     *
     * 思路:
     * 1. 建立动态数组用于存储元素种类 和 重复的元素种类
     * 2. 然后数组中的每个元素 与动态数组相互比较
     */
    public static int task2(int[] nums) {
        ArrayList<Integer> index = new ArrayList<>(); // 创建动态数组, 用于存储元素种类
        ArrayList<Integer> repeat = new ArrayList<>(); // 创建动态数组, 用于存储重复的元素
        for(int i= 0;i< nums.length;++i){
            int temp = 0; // 标签, 用于确实nums[i]是否是独一无二的数字
            for(int j = 0; j< index.size();++j){
                if(nums[i]==index.get(j)){
                    repeat.add(nums[i]);
                    temp = 1;
                    break;
                }
            } // 遍历动态数组, 如果找到重复,那么可以确实这就是一一个重复的数字
            if(temp==0){
                index.add(nums[i]);
            }//如果temp 并没有变成1 说明还没有重复, 加入index中
        }

        if( repeat.size() != 0){
            Random random = new Random();
            int randomNumber = random.nextInt(repeat.size());//生成的数字为:[0,repeat.size()]
            return repeat.get(randomNumber);
        }else {
            return 0;
        }
    }
    // 第三题
    /**
     * 3. 实现二分查找
     * 给定一个已经排好序的数组nums，和一个目标数字target,请使用二分查找的思路找到target在nums
     * 中的下标，如不不存在，返回target应存放的位置的相反值-1。
     * 输入： [2, 3, 1, 0, 2, 5, 3] 输出：2
     *
     * 时间复杂度: O(log n)
     * 思路:
     * 1. 二分查找又称折半查找、二分搜索、折半搜索是在分治算法基础上设计出来的查找算法
     * 2. 初始状态下，将整个序列作为搜索区域（假设为 [B, E]）；
     * 3. 找到搜索区域内的中间元素（假设所在位置为 M），和目标元素进行比对。如果相等，则搜索成功；如果中间元素大于目标元素，表明目标元素位于中间元素的左侧，将 [B, M-1] 作为新的搜素区域；
     * 反之，若中间元素小于目标元素，表明目标元素位于中间元素的右侧，将 [M+1, E] 作为新的搜素区域；
     * 4. 重复执行第二步，直至找到目标元素。如果搜索区域无法再缩小，且区域内不包含任何元素，表明整个序列中没有目标元素，查找失败。
     */
    public static int task3(int[] nums, int target) {
        int begin = 0;
        int end = nums.length-1;
        int mid = (begin+end)/2;
        int res = binary_search(nums, begin, mid, end, target);
        return res;
    }
    public static int binary_search(int[] nums, int begin , int mid, int end, int target){
        if(begin > end){
            return -1;
        } else {
            int newBegin = 0;
            int newMid = 0;
            int newEnd = 0;
            if(target<nums[mid]){
                newBegin= begin;
                newEnd = mid-1;
                newMid=(newBegin+newEnd)/2;
                return binary_search(nums,newBegin,newMid,newEnd,target);
            }else if(target>nums[mid]){
                newBegin= mid+1;
                newEnd = end;
                newMid=(newBegin+newEnd)/2;
                return binary_search(nums,newBegin,newMid,newEnd,target);
            }else{
                return mid;
            }
        }
    }
}


