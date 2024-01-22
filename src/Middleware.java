public class Middleware {

    public static void Listener(){
        KeyboardListener.ListenArrowKeys();
        boolean callback = KeyboardListener.ListenFuncitonKeys();
        if(callback){
            return;
        }
        KeyboardListener.ListenAlfaNumeric();
        KeyboardListener.ListenPushKeys();
        KeyboardListener.ListenPullKeys();
    }

    public static void Action(){
        if(Settings.opcode == null) return;
        int command = Settings.opcode.ordinal();
        // move command
        if(command <= 5){
            Engine.Move(command);
        }
        else if(command >= 14 && command <= 17){
            Engine.ChangeMode(command);
        }
        else if(command == 6){
            Engine.Insert();
        }
        else if(command == 10 || command == 11){
            Engine.Push(command);
        }
        else if(command >= 18 && command <= 20){
            Engine.Format(command);
        }
        else if(command == 12){
            Engine.Pull(OpCode.PullHorizontal.ordinal() , -1);
            if(Settings.EditorMode != Modes.Find && Settings.EditorMode != Modes.Replace) Engine.Move(OpCode.MoveLeft.ordinal());
        }
        else if(command == 9){
            Engine.Insert();
        }
        else if(command == 21){
            Engine.Escape();
        }
        else if(command == 22){
            Engine.ChangeMode(command);
        }
        else if(command == 23){
            Engine.Insert();
        }
        else if(command == 7){
            // overwrite
            Engine.Insert();
        }
        else if(command == 24){
            // copy
            Engine.Copy();
        }
        else if(command == 25){
            // paste
            Engine.Paste();
        }
        else if(command == 26){
            Engine.Load();
        }
        else if(command == 27){
            Engine.Save();
        }
    }

}
