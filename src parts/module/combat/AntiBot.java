//package client.module.combat;
//
//
//import client.Client;
//import client.Hero.settings.Setting;
//import client.Notification.NotificationPublisher;
//import client.Notification.NotificationType;
//import client.event.EventTarget;
//import client.event.events.EventUpdate;
//import client.module.Category;
//import client.module.Module;
//import client.values.BooleanValue;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.EntityPlayer;
//import org.lwjgl.input.Keyboard;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//
//public class AntiBot extends Module {
//
//
//    public AntiBot() {
//        super("AntiBot", Keyboard.KEY_M, Category.COMBAT);
////        this.addOption(this.inos = new BooleanValue("Invis", false));
////        this.addOption(this.mine = new BooleanValue("MinePlex", false));
//    }
//
//    @Override
//    public void setup() {
//        Client.instance.settingsManager.rSetting(new Setting("Invis", this, false));
//        Client.instance.settingsManager.rSetting(new Setting("Mineplex", this, false));
//
//    }
//    @EventTarget
//    public void onUpdate(EventUpdate event) {
//        if (Client.instance.settingsManager.getSettingByName("Invis").getValBoolean()) {
//            for (Object entity : mc.theWorld.loadedEntityList)
//                if (((Entity) entity).isInvisible() && entity != mc.thePlayer)
//                    mc.theWorld.removeEntity((Entity) entity);
//        }
//    }
//
//    @EventTarget
//    public void onAntiBot(EventUpdate eventUpdate) {
//        if (Client.instance.settingsManager.getSettingByName("Mineplex").getValBoolean()) {
//            Iterator var2 = mc.theWorld.getLoadedEntityList().iterator();
//
//            while (var2.hasNext()) {
//                Entity e = (Entity) var2.next();
//                if (e instanceof EntityPlayer) {
//                    EntityPlayer bot = (EntityPlayer) e;
//                    if (e.ticksExisted < 2 && bot.getHealth() < 20.0F && bot.getHealth() > 0.0F && e != mc.thePlayer) {
//                        mc.theWorld.removeEntity(e);
//                    }
//                }
//            }
//            }
//        }
//        @Override
//        public void onEnable () {
//            NotificationPublisher.queue("AntiBot", "AntiBot was Enable", NotificationType.INFO);
//            super.onEnable();
//        }
//        @Override
//        public void onDisable () {
//            NotificationPublisher.queue("AntiBot", "Antibot was Disabled", NotificationType.Disable);
//            super.onDisable();
//
//        }
//}
