public class Helper {
    public static void Log(){
        Cursor.prevpx = Cursor.px;
        Cursor.prevpy = Cursor.py;
        Frame.prevRow = Frame.CurrentRow;
    }
    public static boolean IsAlphaNumeric(Character character){
        if ((character >= '0' && character <= '9') || (character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z')){
            return  true;
        }else{
            return  false;
        }
    }
}
