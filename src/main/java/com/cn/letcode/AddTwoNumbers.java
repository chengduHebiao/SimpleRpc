/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.letcode;

/**
 * @author hebiao
 * @version $Id:AddTwoNumbers.java, v0.1 2018/11/16 10:17 hebiao Exp $$
 */

/**
 * --------------------------------------------
 *
 * 给定两个非空链表来表示两个非负整数。位数按照逆序方式存储，它们的每个节点只存储单个数字。将两数相加返回一个新的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4) 输出：7 -> 0 -> 8 原因：342 + 465 = 807
 *
 * --------------------------------------------
 */
public class AddTwoNumbers {

    public static class ListNode {

        private int x;

        private ListNode next;//下一节点

        public ListNode() {
        }

        public ListNode(int x) {
            this.x = x;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        @Override
        public String toString() {

            return this.getX() + "->" + (this.getNext() != null ? this.getNext().getX() : null);
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.x : 0;
            int y = (q != null) ? q.x : 0;
            int sum = carry + x + y;
            carry = sum / 10; //carry必定是0或者1 ，因为两个数相加的最大值最多为9+9+1 = 19
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) {
                p = p.next;
            }
            if (q != null) {
                q = q.next;
            }
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead;
    }
}
