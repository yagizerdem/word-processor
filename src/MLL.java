import java.util.ArrayList;
import java.util.List;

public class MLL {
    public static MLLNode head = null;
    public static MLLNode tail = null;

    public static  int size = 0;
    public static void AddHead(MLLNode newNode){
        if(head == null){
            head = tail = newNode;
            size++;
            return;
        }
        newNode.next = head;
        head.prev = newNode;

        head = newNode;
        size++;
    }
    public static void AdddTail(MLLNode newNode){
        if(tail == null){
            tail = head = newNode;
            size++;
            return;
        }
        newNode.prev = tail;
        tail.next = newNode;

        tail = newNode;
        size++;
    }
    public static void AddNode(MLLNode newNode , MLLNode left , MLLNode right){
        if(left == null){
            AddHead(newNode);
            return;
        }
        if(right == null){
            AdddTail(newNode);
            return;
        }

        newNode.prev = left;
        newNode.next = right;

        left.next = newNode;
        right.prev = newNode;
        size++;
    }

    public static void DeleteNode(MLLNode mllnode){
        if(mllnode.prev == null){
            // delte head
            head = mllnode.next;
            mllnode.next = null;
            if(head != null) head.prev = null;
            size--;
            return;
        }
        if(mllnode.next == null){
            // delte tail
             tail = mllnode.prev;
             tail.next = null;
             mllnode.prev = null;
             size--;
             return;
        }

        mllnode.prev.next = mllnode.next;
        mllnode.next.prev = mllnode.prev;

        mllnode.prev = null;
        mllnode.next = null;
        size--;
    }
    public static MLLNode[] FindNear(){
        MLLNode terminal = head;
        MLLNode[] left_right = new MLLNode[]{null , null};
        while (terminal != null){
            if(terminal.IsInBound(Frame.CurrentRow , Cursor.px)){
                left_right[0] = terminal;
                left_right[1] = terminal;
                return left_right;
            }
            else if(terminal.IsPrev(Frame.CurrentRow , Cursor.px)){
                left_right[0] = terminal;
            }
            else  if(terminal.IsNext(Frame.CurrentRow , Cursor.px)){
                left_right[1] = terminal;
                break;
            }
            terminal = terminal.next;
        }
        return  left_right;
    }

    public static void Refactor(){
        MLLNode first = MLL.head;
        int x = 1 ,y = 1;
        while (first != null){
            first.startx = x;
            first.starty = y;
            first.endx = first.startx + first.Data.size();
            if(first.endx >= 55){
                x = 1;
                y++;
                first.startx = x;
                first.starty = y;
                first.endx = first.startx + first.Data.size();
            }
            x = (first.endx + 1); // 1 unit space

            first = first.next;
        }
    }

    public  static void JustifyCenter(){
        List<MLLNode> list = new ArrayList<>();
        MLLNode node = MLL.head;
        while (node != null){
            list.add(node);
            if((node.next != null && node.next.starty > node.starty) || node.next == null){
                MLLNode first = list.getFirst();
                MLLNode last = list.getLast();

                int startcx = first.startx;
                int endcx = last.endx;

                int diff_left = startcx - 1;
                int diff_right = 54 - endcx;

                int total = (diff_right - diff_left)/2;

                for(MLLNode mllnode : list){
                    mllnode.startx += total;
                    mllnode.endx += total;
                }
                list = new ArrayList<>();
            }
            node = node.next;
        }

    }
    public  static void JustifyLeft(){
        MLLNode first = MLL.head;
        int cx = 1;
        while (first != null){
            first.startx = cx;
            first.endx = first.startx + first.Data.size();
            cx = first.endx + 1;
            if(first.next != null && first.next.starty > first.starty){
                cx = 1;
            }
            first = first.next;
        }
    }

    public static void Merge(MLLNode first , MLLNode second){
        // deepcopy
        List<String> mergedList = new ArrayList<>();
        for (String item : first.Data) {
            mergedList.add(new String(item));
        }
        for (String item : second.Data) {
            mergedList.add(new String(item));
        }

        first.Data = mergedList;
        first.endx = first.startx + first.Data.size();

        first.next = second.next;
        if(second.next != null) second.next.prev = first;

        if(second == MLL.tail){
            tail = first;
        }

        second.prev = null;
        second.next = null;

    }

}
