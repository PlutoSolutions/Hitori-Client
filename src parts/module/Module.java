package client.module;

import client.Client;
import net.minecraft.client.Minecraft;

public class Module {
    protected Minecraft mc = Minecraft.getMinecraft();

    public String name, displayName;
    private int key;
    private Category category;
    public boolean toggled;

    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
        toggled = false;

        setup();
    }

    public void onEnable() {
        Client.instance.eventManager.register(this);
    }
    public void onDisable() {
        Client.instance.eventManager.unregister(this);
    }
    public void onToggle() {}
    public void toggle() {
        toggled = !toggled;
        onToggle();
        if(toggled)
            onEnable();
        else
            onDisable();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public boolean isToggled() {
        return toggled;
    }
    public String getDisplayName() {
        return displayName == null ? name : displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setup() {}
    public void setToggled(boolean toggled) {
        this.toggled = toggled;

        if (toggled) {
            onEnable();
        }else
            onDisable();
    }

    public void onUpdate() {}
    public void onRender2D() {}
}
//import client.Client;
//import client.module.ModuleManager;
//import client.module.combat.KillAura;
//import client.settings.Setting;
//import client.values.BooleanValue;
//import client.values.NumberValue;
//import net.minecraft.client.Minecraft;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class Module {
//    public Minecraft mc = Minecraft.getMinecraft();
//
//    public String name;
//    public int bind;
//    public Category category;
//    public String displayname;
//
//    private ArrayList<BooleanValue> options;
//    private ArrayList<NumberValue> values;
//    private boolean state;
//    public List<Setting> settings = new ArrayList();
//
//    public boolean toggled;
//
//
//    public Module(String name, int bind, Category category) {
//        this.state = false;
//        this.name = name;
//        this.bind = bind;
//        this.category = category;
//        this.options = new ArrayList<BooleanValue>();
//        this.values = new ArrayList<NumberValue>();
//
//        setup();
//    }
//
//    public void setToggled(boolean toggled) {
//        this.toggled = toggled;
//
//        if (toggled) {
//            onEnable();
//        }else
//            onDisable();
//    }
//
//    public static ArrayList<Module> getCategoryModules(Category cat) {
//        ArrayList<Module> modsInCategory = new ArrayList<Module>();
//        for (Module mod : ModuleManager.getModules()) {
//            if (mod.getCategory() == cat) {
//                modsInCategory.add(mod);
//            }
//        }
//        return modsInCategory;
//    }
//
//    public void onUpdate() {}
//    public void onRender2D() {}
//    public void onEnable() {
//        Client.eventManager.register(this);
//    }
//    public void onDisable() {
//        Client.eventManager.unregister(this);
//    }
//    public void onNotify() {}
//
//
//    public ArrayList<NumberValue> getValues() {
//        return this.values;
//    }
//    public ArrayList<BooleanValue> getOptions() {
//        return this.options;
//    }
//    public boolean getState() {
//        return this.state;
//    }
//    public String getName() {
//        return this.name;
//    }
//    public Category getCategory() {
//        return this.category;
//    }
//    public int getKey() {
//        return this.bind;
//    }
//    public void setKey(int key) {
//        this.bind = key;
//    }
//    public void addValue(NumberValue value) {
//        this.values.add(value);
//    }
//    public void addOption(BooleanValue option) {
//        this.options.add(option);
//    }
//
//    public void setup() {
//
//    }
//
//    public enum Category {
//        COMBAT("COMBAT", 1),
//        MOVEMENT("MOVEMENT", 2),
//        RENDER("RENDER", 3),
//        PLAYER("PLAYER", 4),
//        MISC("MISC", 5);
//
//        private String name;
//        private int id;
//
//        private Category(String name, int id) {
//            this.name = name;
//            this.id = id;
//        }
//
//        public String getName() {
//            return this.name;
//        }
//        public int getID() {
//            return this.id;
//        }
//    }
//    public void setDisplayname(String var1) {
//        this.displayname = var1;
//    }
//
//    public void addSettings(Setting... var1) {
//        this.settings.addAll(Arrays.asList(var1));
//    }
//}
