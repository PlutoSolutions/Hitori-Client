package client.module.movement;

import client.Notification.NotificationPublisher;
import client.Notification.NotificationType;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import net.minecraft.client.Minecraft;

public class SpeedHypixel extends Module {
    private boolean boosted;
    private double speed;
    private double Motion;
    private static Minecraft mc = Minecraft.getMinecraft();
    public SpeedHypixel() {
        super("SpeedHypixel", 0, Category.MOVEMENT);
    }
    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
            if (this.mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindBack.pressed || this.mc.gameSettings.keyBindRight.pressed || this.mc.gameSettings.keyBindLeft.pressed) {

                this.mc.gameSettings.keyBindSprint.pressed = true;

                if (this.mc.thePlayer.onGround) {

                    this.mc.gameSettings.keyBindJump.pressed = false;
                    this.mc.thePlayer.jump();
                    this.boosted = false;

                } else {
                    speed = 1.0044D;
                    Motion = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
                    if (mc.thePlayer.hurtTime < 5) {
                        mc.thePlayer.motionX = -Math.sin((double) getDirection()) * speed * Motion;
                        mc.thePlayer.motionZ = Math.cos((double) getDirection()) * speed * Motion;

                        if (!this.boosted) {

                            this.mc.timer.timerSpeed = 1.05f;

                            double motionV = 0.15;

                            double x = this.getPosForSetPosX(motionV);
                            double z = this.getPosForSetPosZ(motionV);

                            this.mc.thePlayer.motionX = this.mc.thePlayer.motionX + x;
                            this.mc.thePlayer.motionZ = this.mc.thePlayer.motionZ + z;
                            this.boosted = true;
                        }
                    }

                }



        }
    }


    public static float getDirection () {
        float var1 = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0F) {
            var1 += 180.0F;
        }

        float forward = 1.0F;
        if (mc.thePlayer.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (mc.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
        }

        if (mc.thePlayer.moveStrafing > 0.0F) {
            var1 -= 90.0F * forward;
        }

        if (mc.thePlayer.moveStrafing < 0.0F) {
            var1 += 90.0F * forward;
        }

        var1 *= 0.017453292F;
        return var1;
    }

    public double getPosForSetPosX ( double value){
        double yaw = Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw);
        double x = -Math.sin(yaw) * value;
        return x;
    }

    public double getPosForSetPosZ ( double value){
        double yaw = Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw);
        double z = Math.cos(yaw) * value;
        return z;
    }
    @Override
    public void onEnable() {
        NotificationPublisher.queue("SpeedHypixel", "SpeedHypixel was Enable", NotificationType.INFO);
        super.onEnable();
    }
    @Override
    public void onDisable() {
        NotificationPublisher.queue("SpeedHypixel", "SpeedHypixel was Disabled", NotificationType.Disable);
        super.onDisable();
    }
}
