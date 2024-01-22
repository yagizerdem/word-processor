import java.util.ArrayList;
import java.util.List;

public class Settings {
    public static int mousepr; // mouse pressed?
    public static int mousex, mousey; // mouse text coords.
    public static int keypr; // key pressed?
    public static int rkey; // key (for press/release)
    public static int rkeymod; // key modifiers
    public static int capslock = 0; // 0:off 1:on

    public static OpCode opcode = null;

    public static Modes EditorMode = Modes.Insert; // default

    public static String Insertchar = null;


    public static String Regex = "";
    public static  String  ReplaceData = "";

    public static List<String> CopiedString = new ArrayList<>();

}
