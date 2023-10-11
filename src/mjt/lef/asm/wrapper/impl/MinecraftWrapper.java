package mjt.lef.asm.wrapper.impl;

import mjt.lef.asm.wrapper.Wrapper;
import mjt.lef.asm.wrapper.impl.entity.EntityPlayerSPWrapper;
import mjt.lef.asm.wrapper.impl.render.EntityRendererWrapper;
import mjt.lef.asm.wrapper.impl.render.FontRendererWrapper;
import mjt.lef.asm.wrapper.impl.render.RenderManagerWrapper;
import mjt.lef.asm.wrapper.impl.setting.GameSettingsWrapper;
import mjt.lef.asm.wrapper.impl.world.WorldClientWrapper;
import mapping.Mappings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MinecraftWrapper extends Wrapper {
    private static final String CLASS = "net/minecraft/client/Minecraft";

    private static MinecraftWrapper instance;

    private final Object minecraftObj;

    private Object currentScreenObj;

    private final EntityPlayerSPWrapper thePlayer = new EntityPlayerSPWrapper();
    private final WorldClientWrapper theWorld = new WorldClientWrapper();

    private EntityRendererWrapper entityRenderer;
    private RenderManagerWrapper renderManager;
    private GameSettingsWrapper gameSettings;

    private FontRendererWrapper fontRendererWrapper;

    private MinecraftWrapper(Object obj) {
        super(CLASS);
        this.minecraftObj = obj;
    }

    public GameSettingsWrapper getGameSettings() {
        if (gameSettings == null) {
            try {
                // FD: ave/t net/minecraft/client/Minecraft/field_71474_y
                Field field = getClazz().getField(Mappings.seargeToNotchField("field_71474_y")); // gameSettings
                gameSettings = new GameSettingsWrapper(field.get(minecraftObj));
            } catch (Exception ignored) {

            }
        }

        return gameSettings;
    }

    public RenderManagerWrapper getRenderManager() {
        if (renderManager == null) {
            try {
                // FD: ave/aa net/minecraft/client/Minecraft/field_175616_W
                Field field = getClazz().getDeclaredField(Mappings.seargeToNotchField("field_175616_W"));
                field.setAccessible(true);
                renderManager = new RenderManagerWrapper(field.get(minecraftObj));
            } catch (Exception ignored) {

            }
        }

        return renderManager;
    }

    public EntityRendererWrapper getEntityRenderer() {
        if (entityRenderer == null) {
            try {
                // FD: ave/o net/minecraft/client/Minecraft/field_71460_t
                Field field = getClazz().getField(Mappings.seargeToNotchField("field_71460_t")); // entityRenderer
                entityRenderer = new EntityRendererWrapper(field.get(minecraftObj));
            } catch (Exception ignored) {

            }
        }

        return entityRenderer;
    }

    public HitResultWrapper getHitResult() {

        try {
            // FD: ave/s net/minecraft/client/Minecraft/field_71476_x
            String notch = Mappings.seargeToNotchField("field_71476_x"); // objectMouseOver
            Field field = getClazz().getField(notch);
            Object value = field.get(minecraftObj);

            if (value == null) return null;

            return new HitResultWrapper(value);
        } catch (Exception ignored) {

        }

        return null;
    }

    public EntityPlayerSPWrapper getPlayer() {

        try {
            // FD: ave/h net/minecraft/client/Minecraft/field_71439_g
            String notch = Mappings.seargeToNotchField("field_71439_g"); // thePlayer
            Field field = getClazz().getField(notch);

            Object value = field.get(minecraftObj);
            thePlayer.setPlayerObj(value);
        } catch (Exception ignored) {

        }

        return thePlayer;
    }

    public WorldClientWrapper getWorld() {
        try {
            // FD: ave/h net/minecraft/client/Minecraft/field_71441_e
            String notch = Mappings.seargeToNotchField("field_71441_e"); // theWorld
            Field field = getClazz().getField(notch);

            Object value = field.get(minecraftObj);
            theWorld.setWorldObj(value);
        } catch (Exception ignored) {

        }

        return theWorld;
    }

    public Object getCurrentScreen() {
        return currentScreenObj;
    }

    public void setCurrentScreenHook(Object screen) {

    }

    public void setCurrentScreen(Object currentScreenObj) {
        this.currentScreenObj = currentScreenObj;
    }

    public FontRendererWrapper getFontRenderer() {
        if (fontRendererWrapper == null) {
            try {
                String notch = Mappings.seargeToNotchField("field_71466_p");
                Field field = getClazz().getField(notch);
                fontRendererWrapper = new FontRendererWrapper(field.get(minecraftObj));
            } catch (Exception ignored) {

            }
        }

        return fontRendererWrapper;
    }

    public void setLeftClickCounter(int delay) {
        // FD: ave/ag net/minecraft/client/Minecraft/field_71429_W

        try {
            String notch = Mappings.seargeToNotchField("field_71429_W"); // leftClickCounter
            Field field = getClazz().getDeclaredField(notch);
            field.setAccessible(true);
            field.set(minecraftObj, delay);
        } catch (Exception ignored) {

        }
    }

    public void clickMouse() {
        try {
            String notch = Mappings.seargeToNotchMethod("func_147116_af"); // clickMouse
            Method method = getClazz().getDeclaredMethod(notch);
            method.setAccessible(true);
            method.invoke(minecraftObj);
        } catch (Exception ignored) {

        }
    }

    public void rightClickMouse() {
        try {
            String notch = Mappings.seargeToNotchMethod("func_147121_ag"); // rightClickMouse
            Method method = getClazz().getDeclaredMethod(notch);
            method.setAccessible(true);
            method.invoke(minecraftObj);
        } catch (Exception ignored) {

        }
    }

    public static MinecraftWrapper get() {
        if (instance == null) {
            try {
                String notchClass = Mappings.getUnobfClass(CLASS);
                String notch = Mappings.seargeToNotchMethod("func_71410_x");

                Class<?> clazz = Class.forName(notchClass);
                Object obj = clazz.getDeclaredMethod(notch).invoke(null);

                instance = new MinecraftWrapper(obj);
            } catch (Exception ignored) {

            }
        }

        return instance;
    }
}
