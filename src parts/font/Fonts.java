package client.font;

import java.awt.*;
import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;


public class Fonts {
    public static MCFontRenderer font = new MCFontRenderer(font(18), true, true);
    public static MCFontRenderer fontFrame = new MCFontRenderer(font(15), false, true);
    public static MCFontRenderer fontButton = new MCFontRenderer(font(28), false, true);
    public int FONT_HEIGHT;

    public static Font font(float s) {
    	try {
//return Font.createFont(0, new File(Minecraft.getMinecraft().mcDataDir + "\\1.ttf")).deriveFont(s);
return Font.createFont(0, new File("E:\\Catware\\run\\1.ttf")).deriveFont(s);

		} catch (Exception e) {
			return null;
		}
    }


}
