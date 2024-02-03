package ttv.alanorMiga.jeg.client.render.gun.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import ttv.alanorMiga.jeg.client.SpecialModels;
import ttv.alanorMiga.jeg.client.render.gun.IOverrideModel;
import ttv.alanorMiga.jeg.client.util.RenderUtil;
import ttv.alanorMiga.jeg.common.Gun;
import ttv.alanorMiga.jeg.init.ModItems;
import ttv.alanorMiga.jeg.item.attachment.IAttachment;

/**
 * Since we want to have an animation for the charging handle, we will be overriding the standard model rendering.
 * This also allows us to replace the model for the different stocks.
 */
public class BoltActionRifleModel implements IOverrideModel {

    @SuppressWarnings("resource")
    @Override
    public void render(float partialTicks, ItemDisplayContext transformType, ItemStack stack, ItemStack parent, LivingEntity entity, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {

        //Renders the static parts of the model.
        RenderUtil.renderModel(SpecialModels.BOLT_ACTION_RIFLE_MAIN.getModel(), stack, matrixStack, buffer, light, overlay);

        if (entity.equals(Minecraft.getInstance().player)) {

            //Always push.
            matrixStack.pushPose();
            //Don't touch this, it's better to use the display options in Blockbench.
            matrixStack.translate(0, -5.8 * 0.0625, 0);
            //Gets the cooldown tracker for the item. Items like swords and enderpearls also have this.
            ItemCooldowns tracker = Minecraft.getInstance().player.getCooldowns();
            float cooldown = tracker.getCooldownPercent(stack.getItem(), Minecraft.getInstance().getFrameTime());
            cooldown = (float) ease(cooldown);
            /**
             * We are moving whatever part is moving.
             * X,Y,Z, use Z for moving back and forth.
             * The higher the number, the shorter the distance.
             */
            matrixStack.translate(0, 0, cooldown / 16);
            matrixStack.translate(0, 5.8 * 0.0625, 0);
            if (cooldown != 0)
            {
                matrixStack.translate(-0.237, -0.094, cooldown/10);
                matrixStack.mulPose(Axis.ZN.rotationDegrees(-45F));
            }
            //Renders the moving part of the gun.
            RenderUtil.renderModel(SpecialModels.BOLT_ACTION_RIFLE_BOLT.getModel(), stack, matrixStack, buffer, light, overlay);
            //Always pop
            matrixStack.popPose();

        }

    }

    private double ease(double x) {

        return 1 - Math.pow(1 - (2 * x), 4);

    }

}