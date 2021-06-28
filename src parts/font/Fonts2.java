package client.font;

import java.awt.*;
import java.io.File;

public class Fonts2 {
    public static MCFontRenderer font = new MCFontRenderer(font(26), true, true);
    public static MCFontRenderer fontFrame = new MCFontRenderer(font(20), false, true);
    public static MCFontRenderer fontButton = new MCFontRenderer(font(28), false, true);

    public static Font font(float s) {
        try {
//return Font.createFont(0, new File(Minecraft.getMinecraft().mcDataDir + "\\1.ttf")).deriveFont(s);
            return Font.createFont(0, new File("E:\\Catware\\run\\sosiska.ttf")).deriveFont(s);

        } catch (Exception e) {
            return null;
        }
    }


}

