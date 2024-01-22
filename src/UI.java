import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class UI {

    // render nodes
    public static void DeleteNode(MLLNode mllNode){
        int y = mllNode.starty -Frame.CurrentRow + 1;
        Editor.cnt.setCursorPosition(mllNode.startx , y);
        System.out.print(" ".repeat(mllNode.Data.size()));
        Cursor.SyncCursor();
    }
    public static void Render(MLLNode mllNode){
        int y = mllNode.starty - Frame.FirstRow + 1;
        Editor.cnt.setCursorPosition(mllNode.startx, y);

        List<String> dataListCopy = new ArrayList<>(mllNode.Data);

        Iterator<String> iterator = dataListCopy.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            Character chr = item.charAt(1);

            if (item.charAt(0) == '1') Editor.cn.setTextAttributes(Editor.att1);
            else if (item.charAt(0) == '2') Editor.cn.setTextAttributes(Editor.att2);
            if(mllNode.isFind) Editor.cn.setTextAttributes(Editor.att2);

            System.out.print(chr);
            Editor.cn.setTextAttributes(Editor.att0);

            // Perform any modifications to Editor outside the loop if needed
        }

    }
    public static void ReRenderNode(MLLNode mllNode){
        DeleteNode(mllNode);
        Render(mllNode);
        Cursor.SyncCursor();
    }
    //
}
