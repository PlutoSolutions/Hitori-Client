package client.module.render;


import client.Client;
import client.Hero.settings.Setting;
import client.module.Category;
import client.module.Module;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ClickGUI extends Module {

    public ClickGUI( ) {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
        ArrayList<String> options = new ArrayList<>();
        options.add("New");
        options.add("JellyLike");
        Client.instance.settingsManager.rSetting(new Setting("Design",this,"New",options));
        Client.instance.settingsManager.rSetting(new Setting("Sound",this,false));
        Client.instance.settingsManager.rSetting(new Setting("GuiRed",this,255,0,255,true));
        Client.instance.settingsManager.rSetting(new Setting("GuiGreen",this,26,0,255,true));
        Client.instance.settingsManager.rSetting(new Setting("GuiBlue",this,42,0,255,true));

    }
    @Override
    public void setup() {


    }
    @Override
    public void onEnable() {
        super.onEnable();

        mc.displayGuiScreen(Client.instance.clickGui);
        toggle();
    }
}