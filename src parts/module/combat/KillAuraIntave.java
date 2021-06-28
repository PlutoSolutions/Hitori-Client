package client.module.combat;

import client.Client;
import client.Hero.settings.Setting;
import client.Notification.NotificationPublisher;
import client.Notification.NotificationType;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import client.utilites.EntityUtil;
import client.utilites.Timer;
import client.values.BooleanValue;
import client.values.NumberValue;
import com.sun.scenario.animation.shared.TimerReceiver;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;


public class KillAuraIntave extends Module {
    private float yaw, pitch;

    private Entity entity;
    private EntityUtil RayCastUtil;
    public Entity finalEntity;
    public double finalRange;
    private net.minecraft.item.ItemStack ItemStack;

    public KillAuraIntave() {
        super("KillAuraIntave", 0, Category.COMBAT);
//        this.severPitch = Float.intBitsToFloat(Float.floatToIntBits(3.134345E38f) ^ 0x7F6BCD4A);
//        this.addValue(this.MaxCps = new NumberValue("CpsMax", 10, 1, 20));
//        this.addValue(this.MinCps = new NumberValue("CpsMin", 10, 1, 20));
//        this.addOption(this.block1 = new BooleanValue("Rotate", false));
//        this.addValue(this.Rich = new NumberValue("Rich", 3.0, 3.0, 6.0));
//        this.addOption(this.block2 = new BooleanValue("AutoBlock", false));



        // this.MinCps = new NumberSetting("MinAps", Double.longBitsToDouble(Double.doubleToLongBits(0.47002421125381305) ^ 0x7FB714E06DEA73DFL), Double.longBitsToDouble(Double.doubleToLongBits(7.550767831638986E307) ^ 0x7FDAE1B23A2D992FL), Double.longBitsToDouble(Double.doubleToLongBits(8.604777948229427E-4) ^ 0x7FC37235FE6CE987L), Double.longBitsToDouble(Double.doubleToLongBits(0.12364835726060823) ^ 0x7FD6A76B32A37093L));
        // this.MaxCps = new NumberSetting("MaxAps", Double.longBitsToDouble(Double.doubleToLongBits(0.0034512845543459183) ^ 0x7FE545DE49488BA7L), Double.longBitsToDouble(Double.doubleToLongBits(9.802727560279204E307) ^ 0x7FE1730E1FCFBA53L), Double.longBitsToDouble(Double.doubleToLongBits(0.003701281257735448) ^ 0x7FE112263E919587L), Double.longBitsToDouble(Double.doubleToLongBits(0.16135605750456913) ^ 0x7FADA750B6FF2EEFL));
        ManagementAssertion.Setting[] settingArray = new ManagementAssertion.Setting[(int) 1191770762L ^ 0x4708FA88];
    }


    @Override
    public void setup() {
        Client.instance.settingsManager.rSetting(new Setting("MinCPS", this, 10, 1, 20, true));
        Client.instance.settingsManager.rSetting(new Setting("MaxCPS", this, 10, 1, 20, true));
    }
    @EventTarget
    public void onEvent(EventUpdate event) {
        if (event instanceof EventUpdate) {
            if (Client.instance.settingsManager.getSettingByName("MinCps").getValDouble() > Client.instance.settingsManager.getSettingByName("MaxCps").getValDouble()) {
                Client.instance.settingsManager.getSettingByName("MinCps").getValDouble();
                Client.instance.settingsManager.getSettingByName("MinCps").getValDouble();
            }
            if (event.isPre()) Client.instance.settingsManager.getSettingByName("MaxCps").getValBoolean();
            {
                if (event.isPre()) Client.instance.settingsManager.getSettingByName("MinCps").getValBoolean();
                {
                KeyBinding keyBindAttack = this.mc.gameSettings.keyBindAttack;
                KeyBinding.onTick(this.mc.gameSettings.keyBindAttack.getKeyCode());


            }
            }
        }

    }

    private boolean isInFOV(EntityLivingBase entity, double angle) {
        angle *= .5D;
        double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity.posX, entity.posY, entity.posZ)[0]);
        return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
    }

    private float[] getRotations(double posX, double posY, double posZ) {
        double d = entity.posX + (entity.posX - entity.lastTickPosX) - mc.thePlayer.posX;
        double d2 = entity.posY + (double) entity.getEyeHeight() - mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight() - 3.5;
        double d3 = entity.posZ + (entity.posZ - entity.lastTickPosZ) - mc.thePlayer.posZ;
        double d4 = Math.sqrt(Math.pow(d, 2.0) + Math.pow(d3, 2.0));
        float f = (float) Math.toDegrees(-Math.atan(d / d3));
        float f2 = (float) (-Math.toDegrees(Math.atan(d2 / d4)));
        if (d < 0.0 && d3 < 0.0) {
            f = (float) (90.0 + Math.toDegrees(Math.atan(d3 / d)));
        } else if (d > 0.0 && d3 < 0.0) {
            f = (float) (-90.0 + Math.toDegrees(Math.atan(d3 / d)));
        }
        return new float[]{f, f2};
    
    }

    private float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360F;
        float dist = f > 180F ? 360F - f : f;
        return dist;
    }
    @Override
    public void onEnable() {
        NotificationPublisher.queue("KillAuraIntave", "KillAuraIntave was Enable", NotificationType.INFO);
        super.onEnable();
    }
    @Override
    public void onDisable() {
        NotificationPublisher.queue("KillAuraIntave", "KillAuraIntave was Disabled", NotificationType.Disable);
        super.onDisable();
    }


}

