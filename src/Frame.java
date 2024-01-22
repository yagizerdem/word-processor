import java.security.PublicKey;

public class Frame {

    public static int FirstRow = 1;
    public static int LastRow = 24;
    public static int CurrentRow = 1;

    public static int prevRow = 1;

    public static void Outline(){
        Editor.cnt.setCursorPosition(0,0);
        for (int i = 0 ; i < 26 ; i++){
            for(int j = 0 ; j < 56 ; j++){
                if(i == 0 || i == 25){
                    if(j % 5 == 0){
                        System.out.print("+");
                    }
                    else{
                        System.out.print("-");
                    }
                }
                else{
                    if(j == 0 || j == 55){
                        if(i % 5 == 0){
                            System.out.print("+");
                        }else                   System.out.print("|");
                    }
                    else {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
        PrintRightBar();
        Cursor.SyncCursor();
    }

    public static void PrintRightBar(){
        PrintInfo();
        PrintMode();
        Editor.cnt.setCursorPosition(57,4);
        System.out.print("F1 : Previous");
        Editor.cnt.setCursorPosition(57,5);
        System.out.print("F2 : Next");
        Editor.cnt.setCursorPosition(57,6);
        System.out.print("F3 : SelectMode");
        Editor.cnt.setCursorPosition(57,7);
        System.out.print("Ins : Change Mode");
        Editor.cnt.setCursorPosition(57,8);
        System.out.print("F4 : Refactor");
        Editor.cnt.setCursorPosition(57,9);
        System.out.print("F5 : JustifyLeft");
        Editor.cnt.setCursorPosition(57,10);
        System.out.print("F6 : JustifyCenter");
        Editor.cnt.setCursorPosition(57,11);
        System.out.print("F7 : Find (Press Enter to execute)");
        Editor.cnt.setCursorPosition(57,12);
        System.out.print("ESC : Unselect & Unfind");
        Editor.cnt.setCursorPosition(57,13);
        System.out.print("F8 : Replace");
        Editor.cnt.setCursorPosition(57,14);
        System.out.print("F9 : Copy");
        Editor.cnt.setCursorPosition(57,15);
        System.out.print("F10 : Paste");
        Editor.cnt.setCursorPosition(57,16);
        System.out.print("F11 : Load");
        Editor.cnt.setCursorPosition(57,17);
        System.out.print("F12 : Save");

        Editor.cnt.setCursorPosition(57,20);
        System.out.print("Regex : ");
        Editor.cnt.setCursorPosition(57,21);
        System.out.print("Replace : ");
    }
    public static void  PrintInfo(){
        Editor.cnt.setCursorPosition(57 , 1);
        System.out.print("First Frame Row : " + FirstRow +"       ");
        Editor.cnt.setCursorPosition(57 , 2);
        System.out.print("Current Frame Row : " + CurrentRow+"       ");
        Editor.cnt.setCursorPosition(57 , 3);
        System.out.print("Last Frame Row : " + LastRow+"       ");
    }

    public static void PrintMode(){
        Editor.cnt.setCursorPosition(57,18);
        System.out.print("Editor Mode : " + Settings.EditorMode + "              ");
    }
    public static void Move(int amount){
        // positive amount move down  , negative move up
        FirstRow += amount;
        LastRow += amount;
        CurrentRow += amount;
        Frame.ReRender();
    }

    public static void Clear(){
        for(int i = 1 ; i <= 24 ; i++){
            Editor.cnt.setCursorPosition(1,i);
            System.out.print(" ".repeat(54));
        }
        Cursor.SyncCursor();
    }
    public static void Render(){
        MLLNode terminal = MLL.head;
        while(terminal != null){
            if(terminal.starty >= FirstRow && terminal.starty <= LastRow ){
                // print
                UI.Render(terminal);
            }
            if(terminal.starty > LastRow){
                break;
            }
            terminal = terminal.next;
        }
    }

    public static void ReRender(){
        Clear();
        Render();
    }

}
