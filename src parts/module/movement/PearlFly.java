package client.module.movement;


import client.event.EventTarget;
import client.event.events.EventPreMotionUpdate;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import client.utilites.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemEnderPearl;

public class PearlFly
        extends Module {
    /* synthetic */ float oldPitch;
    /* synthetic */ boolean hasPearled;
    /* synthetic */ Timer t;
    private static final Minecraft mc = Minecraft.getMinecraft();


    public PearlFly() {
        super("PearlFly", 0, Category.MOVEMENT);
        this.hasPearled = false;
        this.t = new Timer();
    }
    @EventTarget
    public void onEvent(EventUpdate event) {
        if (event instanceof EventUpdate ) {
            if (!this.hasPearled) {
                this.mc.thePlayer.rotationPitch = 90.0f;
                if (isHoldingPearl()) {
                    this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem());
                }
                this.hasPearled = true;
            }
            this.mc.thePlayer.motionY = 0.0;
            if (isMoving2()) {
                this.mc.thePlayer.setAIMoveSpeed(0.7f);
            }
            camerarot(4.0f, 4.0f);
        }

        }

    public static boolean isHoldingPearl() {
        return Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemEnderPearl;
    }
    public static void camerarot(float f2, float f3) {
        Minecraft minecraft = Minecraft.getMinecraft();
        Minecraft.getMinecraft().thePlayer.cameraYaw += f2 / 100.0f;
        Minecraft.getMinecraft().thePlayer.cameraPitch += f3 / 100.0f;
    }
    public static boolean isMoving2() {
        return ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F));
    }
}


