package client.module.movement;

import client.Client;
import client.Hero.settings.Setting;
import client.Notification.NotificationPublisher;
import client.Notification.NotificationType;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import client.values.BooleanValue;
import client.values.NumberValue;
import com.sun.xml.internal.ws.protocol.soap.ClientMUTube;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;

public class Speed extends Module {
    private static Minecraft mc = Minecraft.getMinecraft();
    private boolean boosted;
    private double speed;
    private double Motion;
    private double mineplex = 0, stage;

    public Speed() {
        super("Speed", 0, Category.MOVEMENT);


    }
    @Override
    public void setup() {
        Client.instance.settingsManager.rSetting(new Setting("Delay", this, 1, 0.1, 5, true));

    }
    @EventTarget
    public void onSpeedOOWNYOU(EventUpdate eventUpdate) {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();

            mc.timer.timerSpeed = (float)  Client.instance.settingsManager.getSettingByName("Delay").getValDouble();
            {
                setMotion(speed);
                    if (mc.thePlayer.posY != (int) mc.thePlayer.posY) {
                        mineplex = -1;
                        mc.thePlayer.jumpMovementFactor = 10;

                        mc.thePlayer.getAir();

                        mc.thePlayer.jumpMovementFactor = (float) 1.8;

                        mc.thePlayer.posZ = 0;

                        if (mc.thePlayer.hurtTime >
                                0) {
                            mc.thePlayer.jumpMovementFactor = (float) 0.2;

                            if (mc.thePlayer.isAirBorne) ;
                            mc.timer.timerSpeed = 1.8f;
                            setMotion(speed);
                            mineplex =+3;

                        }
                    }

                }

            }





    }
    @Override
    public void onDisable () {
        mc.timer.timerSpeed = 1;
        super.onDisable();

    }
    @Override
    public void onEnable () {
        NotificationPublisher.queue("Speed", "Speed was Enable", NotificationType.INFO);
        super.onEnable();
    }

    public static boolean isMoving2() {
        return ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F));
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
        }}
}