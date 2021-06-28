package client.module;



import client.Hud.BlockAnimatiomSummer;
import client.Hud.BlockAnimationExhibobo;
import client.Hud.BlockAnimationSlide;
import client.module.combat.*;
import client.module.movement.*;
import client.module.player.*;
import client.module.render.*;

import java.util.ArrayList;
import java.util.List;



public class ModuleManager {

    public static List<Module> modules = new ArrayList<>();

    public ModuleManager() {

        // COMBAT
       // modules.add(new KillAura());
      //  modules.add(new AntiBot());
//        modules.add(new AutoPot());
////
////        // MOVEMENT
//        modules.add(new Sprint());
//        modules.add(new Step());
//        modules.add(new Fly());
//        modules.add(new LongJump());
//        modules.add(new Eagle());
//        modules.add(new SpeedHypixel());
//        modules.add(new Scaffold());
//        modules.add(new MinePlexHop());
//        modules.add(new FlyCubecraft());
//        modules.add(new CraftHop());
//        modules.add(new PearlFly());
//        modules.add(new TargetStrafe());
   //     modules.add(new LongJumpBlockmc());
//
////        // PLAYER
//        modules.add(new AutoArmor());
//        modules.add(new NoSlow());
//        modules.add(new InventoryManagerMod());

//
//        // RENDER
        modules.add(new ClickGUI());
//        modules.add(new FullBright());
//        modules.add(new NoRender());
//        modules.add(new Hud());
//        modules.add(new ColorEnchants());
//        modules.add(new AutoBlock());
//        modules.add(new ChestStealer());
//        modules.add(new TargetHUD());
//        modules.add(new ChestESP());
//        modules.add(new BlockAnimatiomSummer());
//        modules.add(new BlockAnimationExhibobo());
//        modules.add(new BlockAnimationSlide());
////
//        modules.add(new Ambience());
//        modules.add(new OutlineESP());
//        modules.add(new Phase());
//        modules.add(new Speed());
//        modules.add(new Timer());
//        modules.add(new Criticals());
//        modules.add(new SlowMotionMod());
//        modules.add(new HighJump());
//        modules.add(new KillAuraIntave());
//        modules.add(new LongJumpMineplex());

    }
    public ArrayList<Module> getModules() {
        return (ArrayList<Module>) modules;
    }
    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

//
//    public Module getModule(String name) {
//        for (Module module : modules) {
//            if (module.name.equalsIgnoreCase(name)) {
//                return module;
//            }
//        }
//        return null;
//    }
//
//    public static ArrayList<Module> getModules() {
//        return (ArrayList<Module>) modules;
//    }
//
//}



