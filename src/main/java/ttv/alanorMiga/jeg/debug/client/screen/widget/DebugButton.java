package ttv.alanorMiga.jeg.debug.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import ttv.alanorMiga.jeg.debug.IDebugWidget;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class DebugButton extends Button implements IDebugWidget {
    private final Supplier<Boolean> enabled;

    public DebugButton(Component label, OnPress onPress) {
        super(0, 0, 0, 20, label, onPress, DEFAULT_NARRATION);
        this.enabled = () -> true;
    }

    public DebugButton(Component label, OnPress onPress, Supplier<Boolean> enabled) {
        super(0, 0, 0, 20, label, onPress, DEFAULT_NARRATION);
        this.enabled = enabled;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.active = this.enabled.get();
        super.render(pGuiGraphics, mouseX, mouseY, partialTicks);
    }
}