package client.module.movement;

import client.Client;
import client.Hero.settings.Setting;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import client.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.lwjgl.input.Keyboard;

public class FlyCubecraft extends Module {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private double dist;
    private int count;
    private double damage;
    private double startY;
    private double motionY;

    public FlyCubecraft() {
        super("FlyCubecraft", 0, Category.MOVEMENT);


    }
    @Override
    public void setup() {
        Client.instance.settingsManager.rSetting(new Setting("Speed", this, 0, 0.1, 4, true));

    }
    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (mc.thePlayer.onGround)
            damage();
        if (mc.thePlayer.onGround)
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.2, mc.thePlayer.posZ);
        mc.timer.timerSpeed = (float)Client.instance.settingsManager.getSettingByName("Speed").getValDouble();
        mc.thePlayer.motionY = 0.001203121;
        mc.thePlayer.cameraYaw = 0.11F;

    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
        mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
        mc.thePlayer.speedInAir = 0.02F;
        super.onDisable();
    }

    public static void damage() {
        double offset = 0.060100000351667404D;
        NetHandlerPlayClient netHandler = mc.getNetHandler();
        EntityPlayerSP player = mc.thePlayer;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;

        for (int i = 0; (double) i < (double) getMaxFallDist() / 0.05510000046342611D + 1.0D; ++i) {
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.060100000351667404D, z, false));
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 5.000000237487257E-4D, z, false));
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.004999999888241291D + 6.01000003516674E-8D, z, false));
        }

        netHandler.addToSendQueue(new C03PacketPlayer(true));
    }

    public static float getMaxFallDist() {
        PotionEffect potioneffect = mc.thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return (float) (mc.thePlayer.getMaxFallHeight() + f);
    }

}




