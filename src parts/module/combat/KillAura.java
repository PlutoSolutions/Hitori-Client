package client.module.combat;

import client.Client;
import client.Hero.settings.Setting;
import client.Notification.NotificationPublisher;
import client.Notification.NotificationType;
import client.event.EventTarget;
import client.event.events.EventPostMotionUpdate;
import client.event.events.EventPreMotionUpdate;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import client.utilites.EntityUtil;
import client.values.BooleanValue;
import client.values.NumberValue;
import client.values.Value;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class KillAura extends Module {
    public static EntityLivingBase target;

    private Value<String> mode;
    private long current, last;
    //private int delay = 8;
    private float yaw, pitch;
    private boolean others;

    private BooleanValue block1, autosword;
    private NumberValue Rich, delay;
    private Object EntityPlayerSP;


    private Object EntityArmorStand;
    private Entity finalEntity;


    public KillAura() {
        super("KillAura", Keyboard.KEY_R, Category.COMBAT);
        this.mode = new Value<String>("KillAura","Mode",0);
        this.mode.mode.add("Hypixel");
//        this.addOption(this.block1 = new BooleanValue("AutoBlockSmart", false));
//        this.addOption(this.autosword = new BooleanValue("AutoSword", false));
//        this.addOption(this.blo123 = new BooleanValue("AutoBlockSilent", false));
//        this.addValue(this.Rich = new NumberValue("Rich", 3.0, 3.0, 6.0));
//        this.addValue(this.delay = new NumberValue("Cps", 10, 1, 20));
//        this.addOption(this.aurahyo = new BooleanValue("HypixelAura", false));
//        this.addValue(this.Fov = new NumberValue("F0V", 180, 50, 360));
    }

@Override
public void setup() {
    Client.instance.settingsManager.rSetting(new Setting("Rich", this, 3.0, 3.0, 15, true));
    Client.instance.settingsManager.rSetting(new Setting("Cps", this, 10, 10, 50, true));
    Client.instance.settingsManager.rSetting(new Setting("Fov", this, 360, 0, 360, true));
    Client.instance.settingsManager.rSetting(new Setting("AutoBlockSmart", this, true));
    Client.instance.settingsManager.rSetting(new Setting("AutoBlockSilent", this, false));
    Client.instance.settingsManager.rSetting(new Setting("HypixelAura", this, true));
}

    @EventTarget
    public void onPre(EventPreMotionUpdate event) {
        target = getClosest(this.Rich.getValue());
        if (target == null)
            return;
        updateTime();
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
        boolean block = target != null && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
        if (block && target.getDistanceToEntity(mc.thePlayer) < this.Rich.getValue() && this.block1.isEnabled())
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());

        if (current - last > 1000 / this.delay.getValue()) {
            attack(target);
            resetTime();
        }
    }


    @EventTarget
    public void onPost(EventPostMotionUpdate event) {
        if (target == null)
            return;
        mc.thePlayer.rotationYaw = yaw;
        mc.thePlayer.rotationPitch = pitch;
    }

    private void attack(Entity entity) {
        if(!isValid(finalEntity))
                    if (this.autosword.isEnabled())
                        mc.thePlayer.inventory.currentItem = EntityUtil.getSwordAtHotbar();
            mc.playerController.attackEntity(mc.thePlayer, entity);
            mc.thePlayer.swingItem();
            boolean block = target != null && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
            if (block && target.getDistanceToEntity(mc.thePlayer) < this.Rich.getValue() &&Client.instance.settingsManager.getSettingByName("AutoBlockSilent").getValBoolean() )
                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());


    }


    private void updateTime() {
        current = (System.nanoTime() / 1000000L);
    }

    private void resetTime() {
        last = (System.nanoTime() / 1000000L);
    }

    private EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target = null;
        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
            mc.playerController.attackEntity(mc.thePlayer, mc.objectMouseOver.entityHit);
            for (Object object : mc.theWorld.loadedEntityList) {
                Entity entity = (Entity) object;
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase player = (EntityLivingBase) entity;
                    if (player != mc.thePlayer)
                        if (canAttack(player)) {
                            double currentDist = mc.thePlayer.getDistanceToEntity(player);
                            if (currentDist <= dist) {
                                dist = currentDist;
                                target = player;
                            }
                        }
                }
            }
        }

        return target;
    }


    private boolean canAttack(EntityLivingBase player) {
        if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
            //if (player instanceof EntityPlayer)
            //    return false;
            if (player instanceof EntityAnimal)
                return false;
            if (player instanceof EntityVillager)
                return false;
        }


        if(!isInFOV(player,  Client.instance.settingsManager.getSettingByName("Fov").getValDouble()) )
            return true;


        return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= mc.playerController.getBlockReachDistance() && mc.thePlayer.ticksExisted > 30;
    }

    private boolean isInFOV(EntityLivingBase entity, double angle) {
        angle *= .5D;
        double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity.posX, entity.posY, entity.posZ)[0]);
        return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
    }

    private float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360F;
        float dist = f > 180F ? 360F - f : f;
        return dist;
    }

    private float[] getRotations(double x, double y, double z) {
        double diffX = x + .5D - mc.thePlayer.posX;
        double diffY = (y + .5D) / 2D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double diffZ = z + .5D - mc.thePlayer.posZ;

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180D / Math.PI);

        return new float[]{yaw, pitch};
    }


    public boolean checkEntityID(Entity entity) {
        boolean check;
        if (entity.getEntityId() <= 1070000000 && entity.getEntityId() > -1) {
            check = true;
        } else {
            check = false;
        }

        return check;
    }

    public boolean isPlayerValid(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            Collection<NetworkPlayerInfo> playerInfo = mc.thePlayer.sendQueue.getPlayerInfoMap();
            Iterator var3 = playerInfo.iterator();

            while (var3.hasNext()) {
                NetworkPlayerInfo info = (NetworkPlayerInfo) var3.next();
                return true;
            }
        }

        return false;
    }
    public boolean isValid(Entity entity) {
        if (entity == null)
            return false;
        if (!(entity instanceof EntityLivingBase))
            return false;
        if (entity.getDistanceToEntity(mc.thePlayer) > (this.Rich.getValue() + 1.0D))
            return false;
        if (entity.isInvisible())
            return false;
        if (entity.isDead || ((EntityLivingBase) entity).deathTime != 0)
            return false;
        if (entity == mc.thePlayer)
            return false;
        return true;
    }
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if ( Client.instance.settingsManager.getSettingByName("HypixelAura").getValBoolean() ) {
            if (mc.thePlayer == null) return;
            float range = (float) this.Rich.getValue();

            EntityPlayer target = mc.theWorld.playerEntities.stream().filter(entityPlayer -> entityPlayer != mc.thePlayer).min(Comparator.comparing(entityPlayer -> entityPlayer.getDistanceToEntity(mc.thePlayer))).filter(entityPlayer -> entityPlayer.getDistanceToEntity(mc.thePlayer) <= range).orElse(null);

            if (target != null) {
                mc.thePlayer.rotationYaw = rotations(target)[0];
                mc.thePlayer.rotationPitch = rotations(target)[1];

                mc.playerController.attackEntity(mc.thePlayer, target);
                mc.thePlayer.swingItem();
            }
        }

    }
    public float[] rotations(Entity entity) {
        double d0 = entity.posX - mc.thePlayer.posX;
        double d1 = entity.posY - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double d2 = entity.posZ - mc.thePlayer.posZ;
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        float f = (float)(Math.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
    float f1 = (float)(-(Math.atan2(d1, d3) * (180D / Math.PI)));

        return new float[]{f,f1 };
    }

    @Override
    public void onEnable() {
        NotificationPublisher.queue("KillAura", "KillAura was Enable", NotificationType.INFO);
        super.onEnable();
    }
    @Override
    public void onDisable() {
        NotificationPublisher.queue("KillAura", "KillAura was Disabled", NotificationType.Disable);
        super.onDisable();
    }

}



