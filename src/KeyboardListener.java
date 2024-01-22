import java.awt.event.KeyEvent;
import java.util.Set;

public class KeyboardListener {

        public static void ListenArrowKeys(){
                if(Settings.rkey == KeyEvent.VK_RIGHT){
                        if(Settings.EditorMode == Modes.Find  || Settings.EditorMode == Modes.Replace) return;
                        Settings.opcode = OpCode.MoveRight;
                }
                else if(Settings.rkey == KeyEvent.VK_LEFT){
                        if(Settings.EditorMode == Modes.Find || Settings.EditorMode == Modes.Replace) return;
                        Settings.opcode = OpCode.MoveLeft;
                }
                else if(Settings.rkey == KeyEvent.VK_DOWN){
                        if(Settings.EditorMode == Modes.Find || Settings.EditorMode == Modes.Replace) return;
                        Settings.opcode = OpCode.MoveDown;
                }
                else if(Settings.rkey == KeyEvent.VK_UP){
                        if(Settings.EditorMode == Modes.Find || Settings.EditorMode == Modes.Replace) return;
                        Settings.opcode = OpCode.MoveUp;
                }
        }

        public static boolean ListenFuncitonKeys(){
                if(Settings.rkey == KeyEvent.VK_F1){
                        if(Settings.EditorMode == Modes.Find) return true;
                        Settings.opcode = OpCode.Prev;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_F2){
                        if(Settings.EditorMode == Modes.Find) return true;
                        Settings.opcode = OpCode.Next;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_INSERT){
                        if(Settings.EditorMode == Modes.Insert) Settings.opcode = OpCode.ChangeOverWriteMode;
                        else if(Settings.EditorMode == Modes.OverWrite) Settings.opcode = OpCode.ChangeInsertMode;
                        else if(Settings.EditorMode == Modes.Select)Settings.opcode = OpCode.ChangeInsertMode;
                        else if(Settings.EditorMode == Modes.Find)Settings.opcode = OpCode.ChangeInsertMode;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_F3){
                        Settings.opcode = OpCode.ChangeSelectMode;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_F4){
                        Settings.opcode = OpCode.Refactor;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_F5){
                        Settings.opcode = OpCode.JustifyLeft;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_F6){
                        Settings.opcode = OpCode.JustifyCenter;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_F7){
                        Settings.opcode = OpCode.ChangeModeFind;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_ESCAPE){
                        Settings.opcode = OpCode.Escape;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_F8){
                        Settings.opcode = OpCode.ChangeModeReplace;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_F9){
                        Settings.opcode = OpCode.Copy;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_F10){
                        Settings.opcode = OpCode.Paste;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_F11){
                        Settings.opcode = OpCode.Load;
                        return  true;
                }
                else if(Settings.rkey == KeyEvent.VK_F12){
                        Settings.opcode = OpCode.Save;
                        return  true;
                }

                return  false;
        }

        public static void ListenAlfaNumeric(){
                Character rckey = Settings.capslock == 0 ? Character.toLowerCase((char)Settings.rkey) : Character.toUpperCase((char) Settings.rkey);
                Settings.Insertchar = rckey.toString();
                if(Helper.IsAlphaNumeric(rckey)){
                        if(Settings.EditorMode == Modes.Insert) Settings.opcode = OpCode.Insert;
                        else if(Settings.EditorMode == Modes.OverWrite) Settings.opcode = OpCode.OverWrite;
                        else if(Settings.EditorMode == Modes.Select) Settings.opcode = OpCode.Select;
                        else if(Settings.EditorMode == Modes.Find) Settings.opcode = OpCode.Find;
                        else if(Settings.EditorMode == Modes.Replace) Settings.opcode = OpCode.Replace;
                }
        }

        public static void ListenPushKeys(){
                if(Settings.rkey == KeyEvent.VK_SPACE){
                        Settings.opcode = OpCode.PushHorizontal;
                }
                else if(Settings.rkey == KeyEvent.VK_ENTER){
                        Settings.opcode = OpCode.PushVertical;
                }
        }

        public static void ListenPullKeys(){
                if(Settings.rkey == KeyEvent.VK_BACK_SPACE){
                        Settings.opcode = OpCode.PullHorizontal;
                }
        }


}
