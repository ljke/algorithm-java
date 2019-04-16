package list;

/**
 * 包含随机指针的单向链表
 * 链表的深拷贝方法
 */
public class RandomList {
    static class RandomListNode {
        public int val;
        public RandomListNode next;
        public RandomListNode random;

        public RandomListNode(int val) {
            this.val = val;
        }

        /**
         * 重写toString方法用于打印列表信息
         * 注意判断这里的random指针可能为null
         * @return
         */
        @Override
        public String toString() {
            if (next == null) {
                return "(" + null + "," +
                        (random != null?Integer.toString(random.val):null) + ")";
            } else {
                return "(" + this.hashCode() + "," + val + "," +
                        (random != null?Integer.toString(random.val):null) + ")" +
                        "->" + next.toString();
            }
        }
    }

    /**
     * 随机指针链表的深拷贝方法
     * 新建重复结点交叉插入链表中，然后进行拆分
     * @param head
     * @return
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        RandomListNode cur = head;
        RandomListNode node;
        while (cur != null) {
            node = new RandomListNode(cur.val);
            node.next = cur.next;
            cur.next = node;
            cur = node.next;
        }

        cur = head;
        while (cur != null) {
            node = cur.next;
            if (cur.random != null) {
                node.random = cur.random.next;
            }
            cur = cur.next.next;
        }

        RandomListNode dummy = new RandomListNode(-1);
        RandomListNode new_cur = dummy;
        cur = head;
        while (cur != null) {
            new_cur.next = cur.next;
            new_cur = new_cur.next;
            cur.next = cur.next.next;
            cur = cur.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        RandomListNode head = new RandomListNode(1);
        RandomListNode head1 = new RandomListNode(2);
        RandomListNode head2 = new RandomListNode(3);
        RandomListNode head3 = new RandomListNode(4);
        RandomListNode head4 = new RandomListNode(5);
        head.next = head1;
        head1.next = head2;
        head2.next = head3;
        head3.next = head4;
        head2.random = head1;
        head4.random = head3;
        System.out.println(head);
        RandomList list = new RandomList();
        head = list.copyRandomList(head);
        System.out.println(head);
    }
}
