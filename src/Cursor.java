import java.util.zip.CheckedInputStream;

public class Cursor{

    static int px = 1;
    static  int py = 1;

    static int prevpy = 1;
    static int prevpx = 1;

    public static void SyncCursor(){
        Editor.cnt.setCursorPosition(px , py);
    }

    public static void Left(){
        px--;
        if(px <= 0){
            Up();
            px = 54;
            if(Frame.CurrentRow == 1) px = 1;
        }
    }
    public static void Right(){
        px++;
        if(px >= 55){
            px = 1;
            Down();
        }
    }
    public static void Up(){
        if(Frame.FirstRow > 1 && py == 1 ){
            Frame.Move(-1);
        }else Frame.CurrentRow--;
        py--;
        if(py <= 0) py = 1;
        if(Frame.CurrentRow < 1) Frame.CurrentRow = 1;
        Frame.PrintInfo();
    }
    public static void Down(){
        py++;
        if(py > 24) {
            py = 24;
            Frame.Move(1);
        }
        else Frame.CurrentRow++;
        Frame.PrintInfo();
    }

    public static void Span(int row , int x){
        if(row < Frame.FirstRow){
            int amount = row - Frame.FirstRow;
            Frame.Move(amount);
        }
        else if(row > Frame.LastRow){
            int amount  = row - Frame.LastRow;
            Frame.Move(amount);
        }
        int y = row - Frame.FirstRow + 1;
        py = y;
        px = x;
        Frame.CurrentRow = row;
        Frame.PrintInfo();
        SyncCursor();
    }
}
