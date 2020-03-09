package org.lzz.chat.test;

import org.lzz.chat.util.CommonUtils;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.util.*;

public class test2 {


    public String longestCommonPrefix(String[] strs) {
        String result = "";

        String s1 = strs[0];
        for(String s:strs) {


        }

        return  result;
    }

    public static void main(String[] args) {
        Date a = new Date(1573228740000L);
        System.out.println(CommonUtils.formatToChinese(a));
//        TreeNode root = new TreeNode(1);
//        root.left = new TreeNode(2);
//        root.left.left = new TreeNode(4);
//        root.right = new TreeNode(3);
//        root.right.right = new TreeNode(5);
//        zigzagLevelOrder2(root);
    }


    static class TreeNode {
      int val;
      TreeNode left;TreeNode right;
      TreeNode(int x) { val = x; }
    }

    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if(root==null) return new ArrayList<>();
        List<List<Integer>> result =new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        boolean change=false;
        while(!queue.isEmpty()){
            int count=queue.size();
            List<Integer> list = new ArrayList<Integer>();
            while(count>0) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if(change){
                    if(node.left!=null)
                        queue.offer(node.left);
                    if(node.right!=null)
                        queue.offer(node.right);
                } else{
                    if(node.right!=null)
                        queue.offer(node.right);
                    if(node.left!=null)
                        queue.offer(node.left);
                }
                count--;
            }
            change=!change;
            result.add(list);
        }
        return result;
    }

    public static List<List<Integer>> zigzagLevelOrder2(TreeNode root){
        List<List<Integer>> res = new ArrayList<>();
        Stack<TreeNode> st_right = new Stack<>();
        if(root==null) return res;
        st_right.push(root);
        boolean direction = true;
        while(!st_right.isEmpty()){
            List<Integer> list = new ArrayList<>();

            while(!st_right.isEmpty()){
                TreeNode cur = st_right.pop();
                list.add(cur.val);
                if(direction){
                    if(cur.left!=null) st_right.push(cur.left);
                    if(cur.right!=null) st_right.push(cur.right);
                }else{
                    if(cur.right!=null) st_right.push(cur.right);
                    if(cur.left!=null) st_right.push(cur.left);
                }
            }

            direction = !direction;
            res.add(list);
        }
        return res;
    }
}
