import enigma.console.TextAttributes;
import jdk.management.jfr.FlightRecorderMXBean;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.ReentrantLock;

import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import java.io.File;  // Import the File class

public class Engine  {
    public static void Start() throws InterruptedException {
        Init();
        while (true){
            Cursor.SyncCursor();
            if(Settings.keypr == 1){
                Middleware.Listener(); // fetch operation  type (insert , delete, move ex... )
                Middleware.Action(); // execute
            }
            Clear();
        }

    }
    public static void Init(){
        Frame.Outline();
    }

    public static void Clear() throws InterruptedException{
        Settings.keypr = 0;
        Settings.opcode = null;
        Settings.Insertchar = null;
        Thread.sleep(20);
    }

    public static void Move(int ordinal){
        MLLNode[] left_right = null;
        MLLNode target = null ;
        Helper.Log();
//        System.out.print(ordinal);
        if(ordinal == 0){
            if(Settings.EditorMode == Modes.OverWrite){
                DePainChar();
            }
            Cursor.Left();
            if(Settings.EditorMode == Modes.OverWrite){
                PaintChar();
            }
            if(Settings.EditorMode == Modes.Select){
                left_right = MLL.FindNear();
                if(left_right[0] == left_right[1] && left_right[0] != null){
                    int index = Cursor.px - left_right[0].startx;
                    SelectChar(index ,left_right[0]);
                }
            }
        }
        else if(ordinal == 1){
            if(Settings.EditorMode == Modes.OverWrite){
                DePainChar();
            }
            Cursor.Right();
            if(Settings.EditorMode == Modes.OverWrite){
                PaintChar();
            }
            if(Settings.EditorMode == Modes.Select){
                left_right = MLL.FindNear();
                if(left_right[0] == left_right[1] && left_right[0] != null){
                    int index = Cursor.px - left_right[0].startx-1;
                    SelectChar(index , left_right[0]);
                }
            }
        }
        else if(ordinal == 2){
            if(Settings.EditorMode == Modes.OverWrite){
                DePainChar();
            }
            Cursor.Up();
            if(Settings.EditorMode == Modes.OverWrite){
                PaintChar();
            }
            if(Settings.EditorMode == Modes.Select){
                left_right = MLL.FindNear();
                if(left_right[0] == left_right[1] && left_right[0] != null){
                    int index = Cursor.px - left_right[0].startx-1;
                    SelectChar(index , left_right[0]);
                }

            }
        }
        else if(ordinal == 3){
            if(Settings.EditorMode == Modes.OverWrite){
                DePainChar();
            }
            Cursor.Down();
            if(Settings.EditorMode == Modes.OverWrite){
                PaintChar();
            }
            if(Settings.EditorMode == Modes.Select){
                left_right = MLL.FindNear();
                if(left_right[0] == left_right[1] && left_right[0] != null){
                    int index = Cursor.px - left_right[0].startx-1;
                    SelectChar(index , left_right[0]);
                }

            }
        }
        else if(ordinal == 4){
            if(Settings.EditorMode == Modes.OverWrite){
                DePainChar();
            }
            // prev
            left_right = MLL.FindNear();

            if(left_right[0] == left_right[1] && left_right[0] != null)
                target = left_right[0].prev;
            else if(left_right[0] != left_right[1])
                target = left_right[0];

            if(target != null) Cursor.Span(target.starty , target.startx);

            if(Settings.EditorMode == Modes.OverWrite){
                PaintChar();
            }
            if(Settings.EditorMode == Modes.Select){
                left_right = MLL.FindNear();
                if(left_right[0] == left_right[1] && left_right[0] != null){
                    int index = Cursor.px - left_right[0].startx-1;
                    SelectChar(index ,left_right[0]);
                }

            }
        }
        else if(ordinal == 5){
            if(Settings.EditorMode == Modes.OverWrite){
                DePainChar();
            }
            // next
            left_right = MLL.FindNear();

            if(left_right[0] == left_right[1] && left_right[1] != null)
                target = left_right[1].next;
            else if(left_right[0] != left_right[1])
                target = left_right[1];

            if(target != null) Cursor.Span(target.starty , target.endx);

            if(Settings.EditorMode == Modes.OverWrite){
                PaintChar();
            }
            if(Settings.EditorMode == Modes.Select){
                left_right = MLL.FindNear();
                if(left_right[0] == left_right[1] && left_right[0] != null){
                    int index = Cursor.px - left_right[0].startx-1;
                    SelectChar(index , left_right[0]);
                }

            }
        }
        Cursor.SyncCursor();
    }

    public static  void ChangeMode(int ordinal){
        if(ordinal == 14){
            DePaintCurrent();
            Settings.EditorMode = Modes.Insert;
            Settings.CopiedString = new ArrayList<>();
        }
        else if(ordinal == 15){
            Settings.EditorMode = Modes.OverWrite;
            PaintChar();
            Settings.CopiedString = new ArrayList<>();
        }
        else if(ordinal == 16){
            Settings.EditorMode = Modes.Select;
        }
        else if(ordinal == 17){
            Settings.EditorMode = Modes.Find;
            Cursor.px = 66;
            Cursor.py = 20;
            Cursor.SyncCursor();
            Settings.CopiedString = new ArrayList<>();
        }
        else if(ordinal == 22){
            Settings.EditorMode = Modes.Replace;
            Cursor.px = 66;
            Cursor.py = 21;
            Cursor.SyncCursor();
            Settings.CopiedString = new ArrayList<>();
        }
        Frame.PrintMode();
    }

    public static void Insert(){

        if(Settings.EditorMode == Modes.Insert){
            String data = "0" + Settings.Insertchar;
            MLLNode[] left_right = MLL.FindNear();
            if(left_right[0] == left_right[1] && left_right[0] != null) {
                int index = Cursor.px - left_right[0].startx;
                left_right[0].Insert(data , index);
                if(left_right[0].Data.size() >= 55){
                    // split node
                }
                if(left_right[0].next != null && left_right[0].starty == left_right[0].next.starty) PushHorizontal(left_right[0].next , 1);
            }
            else{
                // crate new node
                MLLNode newNode = MLLNode.Factory(data , Frame.CurrentRow , Cursor.px);
                MLL.AddNode(newNode , left_right[0] , left_right[1]);
                if(left_right[1] != null && newNode.starty == left_right[1].starty) PushHorizontal(left_right[1] , 1);
            }
            Frame.ReRender();
            Move(1);
        }
        else if(Settings.EditorMode == Modes.Find){
            String data =  Settings.Insertchar;
            Settings.Regex += data;
            Editor.cnt.setCursorPosition(66,20);
            Cursor.px++;
            System.out.print(Settings.Regex);
            Cursor.SyncCursor();
        }
        else if(Settings.EditorMode == Modes.Replace){
            String data =  Settings.Insertchar;
            Settings.ReplaceData += data;
            Editor.cnt.setCursorPosition(66,21);
            Cursor.px++;
            System.out.print(Settings.ReplaceData);
            Cursor.SyncCursor();
        }
        else if(Settings.EditorMode == Modes.OverWrite){
            DePainChar();
            String data = "0" + Settings.Insertchar;

            MLLNode[] left_right = MLL.FindNear();
            if(left_right[0] == left_right[1] && left_right[0] != null){
                if(left_right[0].endx == Cursor.px){
                    left_right[0].Data.add(data);
                    left_right[0].endx++;
                    if(left_right[0].next != null && left_right[0].next.starty == left_right[0].starty &&  left_right[0].endx == left_right[0].next.startx){
                        // merge
                        MLL.Merge(left_right[0] , left_right[0].next);
                    }
                }
                else{
                    // cahange value in node
                    int index = Cursor.px - left_right[0].startx;
                    left_right[0].ChangeData(index,data);
                }
            }
            else{
                MLLNode newNode = MLLNode.Factory(data , Frame.CurrentRow , Cursor.px);
                MLL.AddNode(newNode , left_right[0] , left_right[1]);
                if(left_right[1] != null && left_right[1].startx == newNode.endx){
                    MLL.Merge(newNode , left_right[1]);
                }
            }
            Frame.ReRender();
            PaintChar();
        }
    }

    public static void PushVertical(MLLNode mllNode , int amount){
        MLLNode terminal = mllNode;
        while (terminal != null){
            terminal.starty += amount;
            terminal = terminal.next;
        }
    }
    public static void PushHorizontal( MLLNode first, int amount){
        MLLNode temp = first;
        while (temp != null){
            temp.startx += amount;
            temp.endx = temp.startx + temp.Data.size();

            if(temp.endx >55){
                temp.startx = 1;
                temp.endx = temp.startx + temp.Data.size();
                temp.starty++;

                amount += temp.Data.size();
            }
            if(!(temp.next != null && temp.next.starty <= temp.starty)){
                break;
            }
            temp = temp.next;
        }
    }

    public static void Push(int ordinal){

        if(ordinal == 10){
            if(Settings.EditorMode == Modes.Find || Settings.EditorMode == Modes.Replace ) return;
            if(Settings.EditorMode == Modes.Insert){
                // horizontal
                MLLNode[] left_right = MLL.FindNear();
                if(left_right[0] == left_right[1] && left_right[0] != null){
                    if(left_right[0].startx == Cursor.px){
                        Engine.PushHorizontal(left_right[0] , 1);
                    }
                    else if(left_right[0].endx == Cursor.px){
                        if(!(left_right[0].next != null && left_right[0].next.starty == Frame.CurrentRow)) {
                            Engine.Move(OpCode.MoveRight.ordinal());
                            return;
                        };
                        Engine.PushHorizontal(left_right[0].next , 1);
                    }
                    else {
                        // split
                        int splitIndex = Cursor.px  - left_right[0].startx;
                        left_right[0].Split(splitIndex);
                        Engine.PushHorizontal(left_right[0].next , 1);
                    }

                    Frame.ReRender();
                }
                else{
                    if(!(left_right[1] != null && left_right[1].starty == Frame.CurrentRow)) {
                        Engine.Move(OpCode.MoveRight.ordinal());
                        return;
                    };
                    Engine.PushHorizontal(left_right[1] , 1);
                    Frame.ReRender();
                }
                Engine.Move(OpCode.MoveRight.ordinal());
            }
        }
        else if(ordinal == 11){

            if(Settings.EditorMode == Modes.Insert){
                // vertical
                MLLNode[] left_right = MLL.FindNear();
                if(left_right[0] == left_right[1] && left_right[0] != null){
                    if(left_right[0].startx == Cursor.px){
                        PushVertical(left_right[0],1);
                    }
                    else if(left_right[0].endx == Cursor.px){
                        PushVertical(left_right[0].next,1);
                    }
                    else{
                        int splitIndex = Cursor.px - left_right[0].startx;
                        left_right[0].Split(splitIndex);
                        PushVertical(left_right[0].next,1);
                    }
                }
                else{
                    PushVertical(left_right[1],1);
                }

                Frame.ReRender();
            }
            else if(Settings.EditorMode == Modes.Find){
                // execute regex
                String regex = Settings.Regex;
                Settings.Regex = "";
                MLLNode terminal = MLL.head;
                while (terminal != null){
                    StringBuilder builder = new StringBuilder();
                    for(String item : terminal.Data){
                        builder.append(item.charAt(1));
                    }
                    if(builder.toString().equals(regex)){
                        terminal.isFind = true;
                    }
                    terminal = terminal.next;
                }
                Cursor.px = Cursor.prevpx;
                Cursor.py = Cursor.prevpy;
                Frame.CurrentRow = Frame.prevRow;
                Settings.EditorMode = Modes.Insert;
                Frame.PrintMode();
                Frame.ReRender();
            }
            else if(Settings.EditorMode == Modes.Replace){
                // execute replace command
                String replaceData = Settings.ReplaceData;

                MLLNode terminal = MLL.head;
                while (terminal != null){
                    if(terminal.isFind){
                        int oldsize = terminal.Data.size();
                        ArrayList<String> newData = new ArrayList<>();
                        for(int i =0  ; i < replaceData.length(); i++){
                            String data = "0" + replaceData.charAt(i);
                            newData.add(data);
                        }
                        terminal.Data = newData;
                        terminal.endx = terminal.startx + terminal.Data.size();
                        terminal.isFind = false;

                        if(oldsize < terminal.Data.size()){
                            Engine.PushHorizontal(terminal.next , terminal.Data.size() - oldsize);
                        }
                    }
                    terminal = terminal.next;
                }
                Settings.ReplaceData = "";
                Editor.cnt.setCursorPosition(66,21);
                System.out.print(" ".repeat(30));
                Escape();
                Cursor.px = Cursor.prevpx;
                Cursor.py = Cursor.prevpy;
                Frame.CurrentRow = Frame.prevRow;
                Settings.EditorMode = Modes.Insert;
                Frame.PrintMode();
                Frame.ReRender();
            }
        }
    }

    public static void Format(int ordinal){
        if(ordinal == 18){
            MLL.Refactor();
        }
        if(ordinal == 19){
            MLL.JustifyLeft();
        }
        if(ordinal == 20){
            MLL.JustifyCenter();
        }
        Frame.ReRender();
    }

    public static void Pull(int ordinal , int amount){
        if(ordinal == 12){
            // pull horizontal
            PullHorizontal(amount);
            Frame.ReRender();
        }
    }
    public static void PullHorizontal(int amount){
        if(Settings.EditorMode == Modes.Insert){
            if(Cursor.px == 1) return;
            MLLNode[] left_right = MLL.FindNear();
            if(left_right[0] == left_right[1] && left_right[0] != null){
                if(left_right[0].startx == Cursor.px){
                    PullRow(left_right[0] , amount);
                    if(left_right[0].prev != null && left_right[0].prev.endx == left_right[0].startx){
                        // merge nodes
                        MLL.Merge(left_right[0].prev, left_right[0]);
                    }
                }
                else if(left_right[0].endx == Cursor.px){
                    int index = Cursor.px - left_right[0].startx -1;
                    left_right[0].DeleteChar(index);
                    if(left_right[0].next != null && left_right[0].next.starty == left_right[0].starty) PullRow(left_right[0].next , amount);
                    if(left_right[0].Data.isEmpty()){
                        MLL.DeleteNode(left_right[0]);
                    }
                }
                else {
                    int index = Cursor.px - left_right[0].startx -1;
                    left_right[0].DeleteChar(index);
                    if(left_right[0].next != null && left_right[0].next.starty == left_right[0].starty) PullRow(left_right[0].next , amount);
                }
            }
            else{
                // pull
                if(left_right[1] != null && left_right[1].starty == Frame.CurrentRow) PullRow(left_right[1] , amount);
            }
        }
        else if(Settings.EditorMode == Modes.Find){
            if(!Settings.Regex.isEmpty()){
                Cursor.px--;
                Settings.Regex = Settings.Regex.substring(0 , Settings.Regex.length()-1);
                Editor.cnt.setCursorPosition(66,20);
                System.out.print(Settings.Regex + "                         ");
                Cursor.SyncCursor();
            }
        }
        else if(Settings.EditorMode == Modes.Replace){
            if(!Settings.ReplaceData.isEmpty()){
                Cursor.px--;
                Settings.ReplaceData = Settings.ReplaceData.substring(0 , Settings.ReplaceData.length()-1);
                Editor.cnt.setCursorPosition(66,21);
                System.out.print(Settings.ReplaceData + "                         ");
                Cursor.SyncCursor();
            }
        }
    }
    // helper function
    public static void PullRow(MLLNode terminal , int amount){
        while (terminal != null){
            terminal.startx += amount;
            terminal.endx += amount;

            if(terminal.next != null && terminal.next.starty > terminal.starty) break;
            terminal = terminal.next;
        }
    }

    public static void Escape(){
        Editor.cnt.setCursorPosition(66,20);
        System.out.print(" ".repeat(30));
        Cursor.SyncCursor();
        MLLNode terminal = MLL.head;
        while(terminal != null){
            terminal.isFind = false;

            // clear selected chars
            for(int i = 0 ; i < terminal.Data.size() ; i++){
                Character chr = terminal.Data.get(i).charAt(1);
                String newCell = "0"+chr;
                terminal.ChangeData(i ,newCell);
            }

            terminal = terminal.next;
        }
        Frame.ReRender();
        if(Settings.EditorMode == Modes.Select){
            Settings.EditorMode = Modes.Insert;
            Settings.CopiedString = new ArrayList<>();
            Frame.PrintMode();
        }
    }

    public static Character  FindChar(){
        MLLNode terminal = MLL.head;
        Character chr = ' ';
        while (terminal!= null){
            if(terminal.IsInBound(Frame.CurrentRow , Cursor.px)){
                int index = Cursor.px - terminal.startx;
                if(index != terminal.Data.size()){
                    chr = terminal.Data.get(index).charAt(1);
                }
            }
            terminal = terminal.next;
        }
        return chr;
    }

    public static void PaintChar()
    {
        Character chr = FindChar();
        int y = Frame.CurrentRow - Frame.FirstRow + 1;
        Editor.cnt.setCursorPosition(Cursor.px,y );
        Editor.cn.setTextAttributes(Editor.att2);
        System.out.print(chr);
        Editor.cn.setTextAttributes(Editor.att0);
    }
    public static void DePainChar(){
        Character chr = FindChar();
        int y = Frame.prevRow - Frame.FirstRow + 1;
        Editor.cnt.setCursorPosition(Cursor.prevpx,y );
        Editor.cn.setTextAttributes(Editor.att0);
        System.out.print(chr);
    }
    public static void DePaintCurrent(){
        Character chr = FindChar();
        int y = Frame.CurrentRow - Frame.FirstRow + 1;
        Editor.cnt.setCursorPosition(Cursor.px, y);
        Editor.cn.setTextAttributes(Editor.att0);
        System.out.print(chr);
    }

    //
    public static void SelectChar(int index , MLLNode mllNode){
            if(index < mllNode.Data.size() && index >= 0){
                String  data = mllNode.Data.get(index);
                String newData = "1" + data.charAt(1);

                mllNode.ChangeData(index , newData);
                UI.ReRenderNode(mllNode);
        }
    }

    public static void Copy(){
        MLLNode terminal = MLL.head;
        while (terminal != null){
            for(int i = 0 ; i < terminal.Data.size(); i++){
                String data = terminal.Data.get(i);
                if(data.charAt(0) == '1'){
                    String cell = "0"+data.charAt(1);
                    Settings.CopiedString.add(cell);
                }
            }
            terminal = terminal.next;
        }
    }

    public static void Paste(){
        MLLNode[] left_right = MLL.FindNear();
        if(left_right[0] == left_right[1] && left_right[0] != null){
            int index = Cursor.px - left_right[0].startx;
            left_right[0].Data.addAll(index, Settings.CopiedString);
            Engine.PushHorizontal(left_right[0].next , Settings.CopiedString.size());
            left_right[0].endx = left_right[0].startx + left_right[0].Data.size();
            if(left_right[0].endx >= 55){
                Engine.PushHorizontal(left_right[0] , 1);
            }
        }
        else{
            MLLNode newNode = new MLLNode();
            newNode.Data = Settings.CopiedString;
            newNode.startx = Cursor.px;
            newNode.starty = Frame.CurrentRow;
            newNode.endx = newNode.startx + newNode.Data.size();

            if(left_right[1] != null  && newNode.starty == left_right[1].starty) Engine.PushHorizontal(left_right[1] , newNode.Data.size());
            MLL.AddNode(newNode , left_right[0] , left_right[1]);

            if(left_right[0] == MLL.tail){
                MLL.tail = newNode;
            }
            else if(left_right[1] == MLL.head){
                MLL.head = newNode;
            }
            if(newNode.endx >= 55){
                Engine.PushHorizontal(newNode , 1);
            }
            // deep copy of array in settsins
            List<String> deepCopy = new ArrayList<>();
            for (int i = 0; i < Settings.CopiedString.size(); i++) {

                String value = String.copyValueOf(Settings.CopiedString.get(i).toCharArray() , 0 , 2);
                deepCopy.add(value);
            }
            Settings.CopiedString = deepCopy;
        }
        Frame.ReRender();
    }

    public static void Load(){
        MLL.head = null; // clear list
        Frame.Clear();
        try {
            File myObj = new File("Input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] wordList = data.split(" ");
                for(int i = 0 ; i < wordList.length ; i++){
                    MLLNode newNode = new MLLNode();
                    if(wordList[i].length() > 30) continue;
                    for(int j = 0 ; j < wordList[i].length(); j++){
                        String chardata= "0"+ wordList[i].charAt(j);
                        newNode.Data.add(chardata);
                    }
                    MLL.AdddTail(newNode);
                }

            }
            myReader.close();
            MLL.Refactor();
            Frame.Render();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void Save(){
        try {
            File myObj = new File("Output.txt");
            if (myObj.createNewFile()) {

            }
            try {
                FileWriter myWriter = new FileWriter("Output.txt");
                MLLNode terminal = MLL.head;
                while (terminal != null){
                    StringBuilder builder = new StringBuilder();
                    String appendix = " ";
                    for(int i = 0 ; i < terminal.Data.size(); i++){
                        builder.append(terminal.Data.get(i).charAt(1));
                    }
                    if(terminal.next != null && terminal.next.starty > terminal.starty) appendix = "\n";
                    myWriter.write(builder + appendix);
                    terminal = terminal.next;
                }

                myWriter.close();

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
