package client.module.movement;

import client.Client;
import client.event.EventTarget;
import client.event.events.EventPreMotionUpdate;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import net.minecraft.client.Minecraft;

public class LongJumpMineplex extends Module {
    private float air, groundTicks;
    double motionY;
    int count;
    boolean collided, half;
    private static Minecraft mc = Minecraft.getMinecraft();

    public LongJumpMineplex() {


        super("LongJumpMineplex", 0, Category.MOVEMENT);

    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (!mc.thePlayer.onGround ) {
            double speed = half ? 0.5 - air / 100 : 0.658 - air / 100;
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
            motionY -= 0.04000000000001;
            if (air > 24) {
                motionY -= 0.02;
            }
            if (air == 12) {
                motionY = -0.005;
            }
            if (speed < 0.3)
                speed = 0.3;
            if (collided)
                speed = 0.2873;
            mc.thePlayer.motionY = motionY;
            setMotion(speed);

            }
        if (mc.thePlayer.onGround && isOnGround(0.01) && mc.thePlayer.isCollidedVertically && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
            double groundspeed = 0;
            collided = mc.thePlayer.isCollidedHorizontally;
            groundTicks++;
            mc.thePlayer.motionX *= groundspeed;
            mc.thePlayer.motionZ *= groundspeed;

            half = mc.thePlayer.posY != (int) mc.thePlayer.posY;
            mc.thePlayer.motionY = 0.4299999;
            air = 1;
            motionY = mc.thePlayer.motionY;
        }
    }

    public static boolean isOnGround(double height) {
        if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static void setMotion(double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        } else {
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
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }
    @Override
    public void onEnable() {
       super.onEnable();

    }
    @Override
    public void onDisable() {
        super.onDisable();
    }
}
