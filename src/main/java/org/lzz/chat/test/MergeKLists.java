package org.lzz.chat.test;

import java.util.Date;

public class MergeKLists {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        Date date1 = new Date();
        ListNode node1=new ListNode(1);
        node1.next=new ListNode(4);
        node1.next.next=new ListNode(5);
        ListNode node2=new ListNode(1);
        node2.next=new ListNode(3);
        node2.next.next=new ListNode(6);
        ListNode res = mergeTwoNode(node1,node2);
        while (res!=null){
            System.out.print(res.val+",");
            res=res.next;
        }
        System.out.println();
        Date date2 = new Date();
        System.out.println((date2.getTime()-date1.getTime())/1000);
    }

    public static ListNode mergeKLists2(ListNode[] lists) {
        if(lists.length==0){
            return null;
        }
        ListNode list1 = lists[0];
        for(int i=1;i<lists.length;i++) {
            list1=mergeTwoNode(list1,lists[i]);
        }
        return list1;
    }


    private static ListNode mergeTwoNode(ListNode node1,ListNode node2) {
        ListNode res = new ListNode(-1);
        ListNode temp = res;
        while (node1!=null || node2!=null) {
            if(node1==null) {
                temp.next = node2;
                node2=node2.next;
            } else if(node2==null) {
                temp.next = node1;
                node1=node1.next;
            } else if(node1!=null&&node2!=null){
                if(node1.val>node2.val) {
                    temp.next = node2;
                    node2=node2.next;
                } else {
                    temp.next = node1;
                    node1=node1.next;
                }
            }
            temp = temp.next;
        }
        return res.next;
    }

    private static ListNode mergeTwoNode2(ListNode l1,ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode head = null;
        if (l1.val <= l2.val){
            head = l1;
            head.next = mergeTwoNode2(l1.next, l2);
        } else {
            head = l2;
            head.next = mergeTwoNode2(l1, l2.next);
        }
        return head;
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        ListNode res = new ListNode(-1);

        ListNode temp = res;
        while (!isNull(lists)) {
            ListNode listNode = null;
            int j = 0;
            for (int i = 0;i<lists.length;i++) {
                if(lists[i]!=null) {
                    listNode = lists[i];
                    j = i;
                    break;
                }
            }

            for (int i = 1;i<lists.length-1;i++) {
                if(lists[i+1]!=null && listNode.val > lists[i+1].val) {
                    listNode = lists[i+1];
                    j = i + 1;
                }
            }
            temp.next=lists[j];
            temp = temp.next;
            lists[j] = lists[j].next;
        }

        return res.next;
    }


    private static boolean isNull(ListNode[] lists) {
        for (ListNode listNode : lists)
            if(listNode!=null)
                return false;
        return true;
    }


}
