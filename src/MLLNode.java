import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MLLNode {
    public int startx;
    public int endx;
    public int starty;
    public MLLNode next;
    public MLLNode prev;
    public List<String> Data = new ArrayList<>();

    public boolean isFind = false;

    public  boolean IsInBound(int y , int x){
        return starty == y && startx <= x && endx >= x;
    }
    public boolean IsNext(int y , int x){
        return starty > y || (starty == y && x < startx);
    }
    public boolean IsPrev(int y , int x){
        return starty < y || (starty == y && x > endx);
    }
    public static MLLNode Factory(String data , int y , int x){
        MLLNode newNode = new MLLNode();
        newNode.startx = x;
        newNode.Data.add(data);
        newNode.starty = y;
        newNode.endx = newNode.startx + newNode.Data.size();

        return  newNode;
    }
    public  void Insert(String charData , int index){
        Data.add(index, charData);
        endx = startx + Data.size(); // update
    }
    public void Split(int splitindex){
        if (splitindex == 0 || splitindex == Data.size()) return;

// Use subList to split the ArrayList
        List<String> firstPart = Data.subList(0, splitindex);
        List<String> secondPart = Data.subList(splitindex, Data.size());

// Deep copy secondPart
        List<String> secondPartCopy = new ArrayList<>(secondPart);

        this.Data = firstPart;
        this.endx = this.startx + Data.size();

        MLLNode newNode = new MLLNode();
        newNode.startx = this.endx;
        newNode.starty = this.starty;
        newNode.Data = secondPartCopy; // Assign the deep copy
        newNode.endx = newNode.startx + newNode.Data.size();

        newNode.prev = this;
        newNode.next = this.next;
        if (this.next != null) this.next.prev = newNode;
        this.next = newNode;

// Update tail
        if (this == MLL.tail) {
            MLL.tail = this.next;
        }

    }

    public void  DeleteChar(int index){
        Data.remove(index);
        endx--;
    }

    public void ChangeData(int index , String data){
        Data.set(index, data);
    }

}
