package client.module.movement;

import client.event.EventTarget;
import client.event.events.EventPacket;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class MinePlexHop extends Module {
    private double mineplex = 0, stage;
  private static Minecraft mc = Minecraft.getMinecraft();


    public MinePlexHop() {
        super("MinePlexHop", 0, Category.MOVEMENT);

    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
            EventUpdate em = (EventUpdate) eventUpdate;
                double speed = 0.15;
                if (mc.thePlayer.isCollidedHorizontally || !isMoving2()) {
                    mineplex = -2;
                }
                if (isOnGround(0.001) && isMoving2()) {
                    stage = 0;
                    mc.thePlayer.motionY = 0.42;
                    if (mineplex < 0)
                        mineplex++;
                    if (mc.thePlayer.posY != (int) mc.thePlayer.posY) {
                        mineplex = -1;
                    }
                    mc.timer.timerSpeed = 2.001f;
                } else {
                    if (mc.timer.timerSpeed == 2.001f)
                        mc.timer.timerSpeed = 1;
                    speed = 0.62 - stage / 300 + mineplex / 5;
                    stage++;

                }

                setMotion(speed);
                mineplex =-1.5;



    }
    public static boolean isMoving() {
        if ((!mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking())) {
            return ((mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F));
        }
        return false;
    }
    public static boolean isMoving2() {
        return ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F));
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

    public static boolean isOnGround(double height) {
        if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    }