package client.module.combat;


import client.Client;
import client.Hero.settings.Setting;
import client.event.Event;
import client.event.EventTarget;
import client.event.events.Event3D;
import client.event.events.EventPreMotionUpdate;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import client.values.BooleanValue;
import client.values.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.stream.Collectors;


public final class TargetStrafe extends Module {
    private static int DOUBLE_PI;
    //    private final NumberSetting radius = new NumberSetting("Radius", 2.0D, 0.1D, 4.0D, 0.1D);
//    private final BooleanSetting render = new BooleanSetting("Render", true);
//    private final BooleanSetting space = new BooleanSetting("Hold Space", true);
    //KillAura killAura = new KillAura();
    private int direction = -1;
    private boolean pravda;
    private int chislo;
    private Entity targetValidator;
    private Object render;
    private Timer timer;
    private int clr;


    public TargetStrafe() {
        super("TargetStrafe", Keyboard.KEY_U, Category.MOVEMENT);
//        this.addValue(this.Radious = new NumberValue("Radius", 1, 1, 4));
//        this.addValue(this.Render = new NumberValue("RenderHeight", 1, 0.005D, 1.0D));
//        this.addValue(this.Speed = new NumberValue("Speed", 0.1, 0.1, 5));
//        this.addOption(this.block1 = new BooleanValue("Render", false));
//        this.addValue(this.summer = new NumberValue("Hue", 1, 0.0, 1.0));
//        this.addOption(this.space = new BooleanValue("DamageBoosting", false));
//        this.addOption(this.sprint = new BooleanValue("Sprint", true));
//        this.addOption(this.autojump = new BooleanValue("AutoJump", true));
    }
@Override
public void setup() {
    Client.instance.settingsManager.rSetting(new Setting("Radius", this, 1, 1, 4, true));
    Client.instance.settingsManager.rSetting(new Setting("RenderHeight", this, 1, 0.005D, 1.0D, true));
    Client.instance.settingsManager.rSetting(new Setting("Speed", this, 0.1, 0.1, 5, true));
    Client.instance.settingsManager.rSetting(new Setting("Render", this, true));
    Client.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
    Client.instance.settingsManager.rSetting(new Setting("DamageBoosting", this, true));
    Client.instance.settingsManager.rSetting(new Setting("Sprint", this, false));
    Client.instance.settingsManager.rSetting(new Setting("AutoJump", this, false));
    Client.instance.settingsManager.rSetting(new Setting("Hue", this, 1, 0.0, 1.0, true));

}
    @EventTarget
    public void onEvent(EventUpdate e) {
        boolean damageBoost =Client.instance.settingsManager.getSettingByName("DamageBoosting").getValBoolean();
        boolean noSprint = Client.instance.settingsManager.getSettingByName("Sprint").getValBoolean();
        boolean autojump =Client.instance.settingsManager.getSettingByName("AutoJump").getValBoolean();
        double range = Client.instance.settingsManager.getSettingByName("Radius").getValDouble();
        double speed = Client.instance.settingsManager.getSettingByName("Speed").getValDouble();
        boolean damageSpeed = Client.instance.settingsManager.getSettingByName("Sprint").getValBoolean();
        KillAura killAura = (KillAura) Client.moduleManager.getModuleByName("KillAura");
        if (noSprint) {
            mc.thePlayer.setSprinting(false);
        }
        if (Client.moduleManager.getModuleByName("KillAura").toggled) {

            if (KillAura.target != null) {
                if (KillAura.target.getDistanceToEntity(mc.thePlayer) <= range) {
                    if (mc.gameSettings.keyBindLeft.isPressed()) {
                        chislo = 2;
                    }
                    if (mc.gameSettings.keyBindRight.isPressed()) {
                        chislo = 1;
                    }
                    Switch();
                    if (autojump) {
                        if (mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                        }
                    }
                    setSkid(damageBoost && mc.thePlayer.hurtTime > 0 ? speed : speed, 0, !pravda ? -1 : 1, rotations(KillAura.target)[0]);
                } else {
                    setSkid(damageBoost && mc.thePlayer.hurtTime > 0 ? speed : speed, 1, 1, rotations(KillAura.target)[0]);


                }
            }
        }
    }
    @EventTarget
    public void onRender(Event3D er) {
            if (Client.instance.settingsManager.getSettingByName("Render").getValBoolean() )
                this.drawCircle((Entity) KillAura.target, 1, Client.instance.settingsManager.getSettingByName("Radious").getValDouble() );

    }
    public void Switch() {
        if (mc.thePlayer.isCollidedHorizontally) {
            chislo += 1;
        }
        if (chislo % 2 == 0) {
            pravda = true;
        } else {
            pravda = false;
        }

    }





    public static float[] rotations(Entity entity) {
        double d0 = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double d1 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double d2 = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        float f = (float) (Math.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
        float f1 = (float) (-(Math.atan2(d1, d3) * (180D / Math.PI)));

        return new float[]{Minecraft.getMinecraft().thePlayer.rotationYaw + wrapAngleTo180_float(f - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + wrapAngleTo180_float(f1 - Minecraft.getMinecraft().thePlayer.rotationPitch)};
    }

    public static final float wrapAngleTo180_float(float angle) {
        float value = angle % 360.0F;
        if (value >= 180.0F)
            value -= 360.0F;
        if (value < -180.0F)
            value += 360.0F;
        return value;
    }

    public void setSkid(double speed, double forward, double strafe, float yaw) {
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += (forward > 0.0D ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += (forward > 0.0D ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1;
            } else if (forward < 0.0D) {
                forward = -1;
            }
        }

        final double sin = Math.sin(Math.toRadians(yaw + 90.0F));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0F));
        mc.thePlayer.motionX = forward * speed * cos + strafe * speed * sin;
        mc.thePlayer.motionZ = forward * speed * sin - strafe * speed * cos;
    }



    public static void setSpeed(final EventPreMotionUpdate moveEvent, final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;

        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? -45 : 45);
            } else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? 45 : -45);
            }
            strafe = 0.0F;
            if (forward > 0.0) {
                forward = 1F;
            } else if (forward < 0.0) {
                forward = -1F;
            }
        }

        if (strafe > 0.0) {
            strafe = 1F;
        } else if (strafe < 0.0) {
            strafe = -1F;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        moveEvent.x = (forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.z = (forward * moveSpeed * mz - strafe * moveSpeed * mx);

    }

    public static float[] getRotations(double posX, double posY, double posZ) {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        double x = posX - player.posX;
        double y = posY - (player.posY + player.getEyeHeight());
        double z = posZ - player.posZ;

        double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static boolean isMoving() {
        return Minecraft.getMinecraft().thePlayer.movementInput.moveForward != 0.0F || Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe != 0.0F;
    }

    private void drawCircle(Entity entity, float partialTicks, double rad) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.0F);
        GL11.glBegin(3);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - mc.getRenderManager().viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - mc.getRenderManager().viewerPosZ;
        float r = 0.003921569F * (float)Color.WHITE.getRed();
        float g = 0.003921569F * (float)Color.WHITE.getGreen();
        float b = 0.003921569F * (float)Color.WHITE.getBlue();
        double pix2 = 6.283185307179586D;

        for(int i = 0; i <= 90; ++i) {
            GL11.glColor3f(r, g, b);
            GL11.glVertex3d(x + rad * Math.cos((double)i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin((double)i * 6.283185307179586D / 45.0D));
        }

        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
     public static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }

    public static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
    }
}



