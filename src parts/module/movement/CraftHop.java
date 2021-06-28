package client.module.movement;


import client.Client;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class CraftHop extends Module {
    public Minecraft mc = Minecraft.getMinecraft();
    private double speed;

    public CraftHop() {
        super("CraftHop" +
                "", 0, Category.MOVEMENT);
    }

    @EventTarget
    public void OnUda(EventUpdate eventUpdate) {
//        if (mc.thePlayer.isMoving()) {
//            if (!isMoving())
//                return;
//            final double yaw = getDirection();
//            mc.thePlayer.motionX = -Math.sin(yaw) * speed;
//            mc.thePlayer.motionZ = Math.cos(yaw) * speed;
        if (eventUpdate instanceof EventUpdate) {
                if (this.mc.thePlayer.onGround && isMoving() && !this.mc.thePlayer.isSneaking() && !this.mc.thePlayer.capabilities.isFlying && !this.mc.thePlayer.isInWater()) {
                    if (mc.thePlayer.hurtTime == 0) {
                        mc.thePlayer.setSprinting(true);
                        if (mc.thePlayer.onGround) {
                            this.mc.thePlayer.jumpMovementFactor = 0.02f;
                            mc.timer.timerSpeed = 1.0F;
                            mc.thePlayer.motionY = 0.4;
                            mc.thePlayer.motionX *= 1.30;
                            mc.thePlayer.motionZ *= 1.30;
                            if (mc.thePlayer.hurtTime >
                                    0) {
                                mc.thePlayer.jumpMovementFactor = (float) 0.2;

                                if (mc.thePlayer.isAirBorne) ;
                                mc.timer.timerSpeed = 1.8f;
                        } else {
                                mc.thePlayer.onGround = true;

                            }
                        }
                    }
                }
            }


    }





    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
        super.onDisable();
    }
    public static boolean isMoving() {
        return Minecraft.getMinecraft().thePlayer != null
                && (Minecraft.getMinecraft().thePlayer.movementInput.moveForward != 0.0f ||Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe != 0.0f);
    }
    public static double getDirection() {
        float f = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
            f += 180.0f;
        }
        float f2 = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
            f2 = -0.5f;
        } else if (Minecraft.getMinecraft().thePlayer.moveForward > 0.0f) {
            f2 = 0.5f;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0f) {
            f -= 90.0f * f2;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0f) {
            f += 90.0f * f2;
        }
        return Math.toRadians(f);
    }

}
