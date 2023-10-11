package mjt.lef.client.module.movement;

import org.lwjgl.input.Keyboard;
import mjt.lef.client.listener.bus.Listener;
import mjt.lef.client.listener.events.player.EventUpdate;
import mjt.lef.client.module.Category;
import mjt.lef.client.module.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Automatically sprints for you", Category.MOVEMENT);
        getBind().setKey(Keyboard.KEY_O);
    }

    @Listener
    public void onUpdate(EventUpdate event) {
        if (mc.getPlayer().isNull()) return;

        if (!mc.getPlayer().isSprinting()) mc.getPlayer().setSprinting(true);
    }
}
