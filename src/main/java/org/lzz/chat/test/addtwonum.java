package org.lzz.chat.test;

public class addtwonum {

     static class ListNode {
         int val;
         ListNode next;
         ListNode(int x) { val = x; }
     }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int i=0;
        ListNode root=new ListNode(0);
        ListNode curr= root;
        int x,y,sum;
        while(l1!=null || l2!=null || i!=0){

            x=l1!=null?l1.val:0;
            y=l2!=null?l2.val:0;
            sum=x+y+i;
            i=sum/10;
            curr.next=new ListNode(sum%10);
            curr=curr.next;
            l1=l1!=null?l1.next:null;
            l2=l2!=null?l2.next:null;

            System.out.println();
        }

        return root.next;
    }

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
//        n1.next=new ListNode(2);
//        n1.next.next=new ListNode(3);
//        n1.next.next.next=new ListNode(4);


//        ListNode n2 = new ListNode(5);
//        n2.next=new ListNode(6);
//        n2.next.next=new ListNode(4);

        ListNode r = swapPairs(n1);


        while (r!=null){
            System.out.print(r.val+",");
            r=r.next;
        }
        System.out.println();
    }


    public static ListNode swapPairs(ListNode head) {
        if(head==null ){
            return null;
        } else if(head.next!=null) {

            ListNode temp = head.next;
            ListNode temp2 = head.next.next;
            temp.next = head;

            head.next=swapPairs(temp2);

            return temp;
        }else {
            return head;
        }
    }
}
