/*
 * 83. Remove Duplicates from Sorted List
 *
 * Given the head of a sorted linked list, delete all duplicates
 * such that each element appears only once. Return the linked list sorted as well.
 *
 * Input: head = [1,1,2]
 * Output: [1,2]
 *
 * Input: head = [1,1,2,3,3]
 * Output: [1,2,3]
 *
 * Constraints:
 *     The number of nodes in the list is in the range [0, 300].
 *     -100 <= Node.val <= 100
 *     The list is guaranteed to be sorted in ascending order.
 *
 */

/*
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

package LeetCode;

public class RemoveDuplicatesFromSortedList {
    public static void main(String[] args) {
//  Testcase 1:
        ListNode list = new ListNode(1);
        ListNode head = list;
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);

//  Testcase2:
//        ListNode list = new ListNode(1);
//        ListNode head = list;
//        list.add(1);
//        list.add(2);

        ListNode newHead = deleteDuplicates(head);
        while (newHead != null) {
            System.out.print(newHead.getVal() + "   ");
            newHead = newHead.getNext();
        }
    }


    public static ListNode deleteDuplicates(ListNode head) {
        ListNode temp = head;
        if (temp == null){
            return head;
        }
        while (temp.getNext() != null){
            if(temp.getVal() != temp.getNext().getVal()){
                temp = temp.getNext();
            } else {
                temp.setNext(temp.getNext().getNext());
            }
        }

        return head;

    }


}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode getNext() {
        return this.next;
    }

    public int getVal() {
        return this.val;
    }

    public void setNext(ListNode node) {
        this.next = node;
    }

    public void add(int val) {
        if (this.getNext() != null && val <= this.getNext().getVal()) {
            ListNode node = new ListNode(val, this);
        } else {
            ListNode currentNode = this;
            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();
            }
            currentNode.setNext(new ListNode(val));
        }
    }

}
