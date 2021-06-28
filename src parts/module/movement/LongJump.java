package client.module.movement;

import client.Client;
import client.Hero.settings.Setting;
import client.Notification.NotificationPublisher;
import client.Notification.NotificationType;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import client.values.NumberValue;
import net.minecraft.client.Minecraft;

import java.lang.reflect.Field;

public class LongJump  extends Module {

    private double onGroundTicks;
    private int offGroundTicks;
    private int ticks;

    public LongJump() {
        super("LongJump", 0, Category.MOVEMENT);


    }
    @Override
    public void setup() {
        Client.instance.settingsManager.rSetting(new Setting("Speed", this, 1, 0.1,10 , true));

    }
    @EventTarget
    public void onSOSISKA(EventUpdate eventUpdate ) {
        if(mc.thePlayer==null ) return;
        mc.timer.timerSpeed = (float) Client.instance.settingsManager.getSettingByName("Speed").getValDouble();
  //      setTimer((float) Client.instance.settingsManager.getSettingByName(this, "Speed").getValDouble());
        this.mc.thePlayer.jumpMovementFactor = Float.intBitsToFloat(Float.floatToIntBits(59.589134F) ^ 2131390858);
        mc.thePlayer = this.mc.thePlayer;
        mc.thePlayer.motionY += Double.longBitsToDouble(Double.doubleToLongBits(60.0) ^ 9218465483029720404L);
            this.mc.thePlayer.jump();
            this.mc.thePlayer.motionY = Double.longBitsToDouble(Double.doubleToLongBits(24.321342611111111111D) ^ 9216351017147835024L);
            this.ticks += (int) -437566350L ^ -437566349;
            if (this.mc.thePlayer.ticksExisted == ((int) -1062166672L ^ -1062166671)) {
//                setTimer((float) Client.instance.settingsManager.getSettingByName(this, "Speed").getValDouble());
            }

            if (this.mc.thePlayer.onGround) {
                this.offGroundTicks = (int) 1608342314L ^ 1608342314;
                this.onGroundTicks += Double.longBitsToDouble(Double.doubleToLongBits(4.534794585266332D) ^ 9214966962773766119L);
            } else {
                this.offGroundTicks += (int) -1435949553L ^ -1435949554;
                this.onGroundTicks = Double.longBitsToDouble(Double.doubleToLongBits(1.2928049668695546E308D) ^ 9216338734998320344L);
            }
        }



    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
        super.onDisable();
    }
    @Override
    public void onEnable() {
        NotificationPublisher.queue("LongJump", "LongJump was Enable", NotificationType.INFO);
        super.onEnable();
    }


}

