package client.module.player;

import client.Client;
import client.Hero.settings.Setting;
import client.Notification.NotificationPublisher;
import client.Notification.NotificationType;
import client.event.EventTarget;
import client.event.events.EventUpdate;
import client.module.Category;
import client.module.Module;
import client.values.NumberValue;

public class Timer extends Module {

    public Timer() {
        super("Timer", 0, Category.MISC);

    }
    @Override
    public void setup() {
        Client.instance.settingsManager.rSetting(new Setting("Delay", this, 1, 1, 20, true));

    }
    @EventTarget
    public void ondafj(EventUpdate eventUpdate )  {
         mc.timer.timerSpeed = (float)  Client.instance.settingsManager.getSettingByName("Delay").getValDouble();
        }


    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
        super.onDisable();
    }
    @Override
    public void onEnable() {
        NotificationPublisher.queue("Timer", "Timer was Enable", NotificationType.INFO);
        super.onEnable();
    }

}

