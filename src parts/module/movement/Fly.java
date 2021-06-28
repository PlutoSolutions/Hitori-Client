package client.module.movement;


import client.Notification.NotificationPublisher;
import client.Notification.NotificationType;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import client.utilites.Timer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {
	private Timer timer = new Timer();
	/*     */   private int counter;
    private double alpha;
    private int p;
    private double y;
    private static Minecraft mc = Minecraft.getMinecraft();

    public Fly() {
		super("Fly", 0, Category.MOVEMENT);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
        if (this.mc.thePlayer.onGround) {
            damage();
            this.y = this.mc.thePlayer.posY;
        }
        if (this.mc.thePlayer.inventory.getCurrentItem() == null) {
            if (this.mc.gameSettings.keyBindJump.isKeyDown() && this.timer.hasReached(100L)) {
                this.timer.reset();
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.6, this.mc.thePlayer.posZ);
                this.y += 0.6;
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown() && this.timer.hasReached(100L)) {
                this.timer.reset();
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.6, this.mc.thePlayer.posZ);
                this.y -= 0.6;
            }
            this.alpha += 0.1;
            if (Keyboard.isKeyDown((int)78)) {
                this.mc.timer.timerSpeed = 20.0f;
            } else {
                this.mc.thePlayer.motionZ = 0.0;
                this.mc.thePlayer.motionX = 0.0;
                this.mc.timer.timerSpeed = 1.0f;
            }
            float f =getDirection();
            this.mc.thePlayer.motionX = - (double)(MathHelper.sin(f) * 0.4f);
            this.mc.thePlayer.motionZ = MathHelper.cos(f) * 0.4f;
            this.mc.thePlayer.posY = this.y + Math.sin(this.alpha * 15.0) / 15.0;
            this.mc.thePlayer.onGround = true;
            this.mc.thePlayer.motionY = 0.0;
            BlockPos underP = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.getEntityBoundingBox().minY - 1.0, this.mc.thePlayer.posZ);
            Vec3 vec = new Vec3(underP).addVector(0.4000000059604645, 0.4000000059604645, 0.4000000059604645).add(new Vec3(EnumFacing.UP.getDirectionVec()).multi(0.4000000059604645));
            this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), underP, EnumFacing.UP, vec);
        } else {
            this.mc.timer.timerSpeed = 1.0f;
        }



	}

		@Override
		public void onDisable () {
			mc.thePlayer.capabilities.isFlying = false;
			timer.reset();
			super.onDisable();

	}

	public double[] moveLooking(float yawOffset) {
		/* 376 */     float dir = mc.thePlayer.rotationYaw + yawOffset;
		/* 377 */     if (mc.thePlayer.moveForward < 0.0F) {
			/* 378 */       dir += 180.0F;
			/*     */     }
		/* 380 */     if (mc.thePlayer.moveStrafing > 0.0F) {
			/* 381 */       dir -= 90.0F * (mc.thePlayer.moveForward > 0.0F ? 0.5F : mc.thePlayer.moveForward < 0.0F ? -0.5F : 1.0F);
			/*     */     }
		/* 383 */     if (mc.thePlayer.moveStrafing < 0.0F) {
			/* 384 */       dir += 90.0F * (mc.thePlayer.moveForward > 0.0F ? 0.5F : mc.thePlayer.moveForward < 0.0F ? -0.5F : 1.0F);
			/*     */     }
		/*     */
		/* 387 */     float xD = (float)Math.cos((dir + 90.0F) * 3.141592653589793D / 180.0D);
		/* 388 */     float zD = (float)Math.sin((dir + 90.0F) * 3.141592653589793D / 180.0D);
		/* 389 */     return new double[] { xD, zD };
		/*     */   }
    public static float getDirection() {
        float yaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (mc.thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (mc.thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        return yaw *= 0.017453292f;
    }
    public static void damage() {
        double offset = 0.060100000351667404D;
        NetHandlerPlayClient netHandler = mc.getNetHandler();
        EntityPlayerSP player = mc.thePlayer;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;

        for(int i = 0; (double)i < (double)getMaxFallDist() / 0.05510000046342611D + 1.0D; ++i) {
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.060100000351667404D, z, false));
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 5.000000237487257E-4D, z, false));
            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.004999999888241291D + 6.01000003516674E-8D, z, false));
        }

        netHandler.addToSendQueue(new C03PacketPlayer(true));
    }
    public static float getMaxFallDist() {
        PotionEffect potioneffect = mc.thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return (float)(mc.thePlayer.getMaxFallHeight() + f);
    }

}

