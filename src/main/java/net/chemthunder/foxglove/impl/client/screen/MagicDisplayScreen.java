package net.chemthunder.foxglove.impl.client.screen;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.cantrip.Cantrip;
import net.chemthunder.foxglove.impl.cca.entity.CantripComponent;
import net.chemthunder.foxglove.impl.component.BarkComponent;
import net.chemthunder.foxglove.impl.index.FoxgloveDataComponents;
import net.chemthunder.foxglove.impl.index.FoxgloveItems;
import net.chemthunder.foxglove.impl.util.MagicUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringHelper;

public class MagicDisplayScreen extends Screen {
    public MagicDisplayScreen() {
        super(Text.empty());
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        final int baseX = 70;
        final int baseY = 40;

        if (this.client != null) {
            PlayerEntity player = this.client.player;

            if (player != null && client.world != null) {
                Cantrip cantrip = MagicUtils.getCantripComponent(player);
                CantripComponent cantripComponent = CantripComponent.KEY.get(player);

                ItemStack stack = new ItemStack(FoxgloveItems.CHARMED_BARK);
                stack.set(FoxgloveDataComponents.BARK, new BarkComponent(cantrip));

                context.drawItem(
                        stack,
                        baseX,
                        baseY
                );

                context.drawCenteredTextWithShadow(
                        this.textRenderer,
                        Text.literal("Active Magic"),
                        context.getScaledWindowWidth() / 2,
                        10,
                        0xffffff
                );

                context.drawHorizontalLine(
                        0,
                        context.getScaledWindowWidth(),
                        25,
                        0xFFffffff
                );

                context.drawTooltip(
                        this.textRenderer,
                        Text.literal("Current Duration").formatted(Formatting.DARK_GRAY),
                        baseX + 10,
                        baseY + 55 + (MagicUtils.getCantripEffectTranslationKey(cantrip.effect()) + ".desc").length() / 2
                );

                context.drawTooltip(
                        this.textRenderer,
                        Text.literal(StringHelper.formatTicks(cantripComponent.getDuration(), client.world.getTickManager().getTickRate())).formatted(Formatting.YELLOW),
                        baseX + 110,
                        baseY + 55 + (MagicUtils.getCantripEffectTranslationKey(cantrip.effect()) + ".desc").length() / 2
                );

                int whyDoINeedThis = baseX + 70 + cantrip.name().length();
                if (!cantrip.isEmpty()) {
                    context.drawTooltip(
                            this.textRenderer,
                            Text.literal(MiscUtils.formatString(cantrip.name())).withColor(cantrip.effect().type().getColor()),
                            baseX + 10,
                            baseY + 15
                    );

                    context.drawTooltip(
                            this.textRenderer,
                            Text.translatable(MagicUtils.getCantripEffectTranslationKey(cantrip.effect())).withColor(cantrip.effect().type().getColor()),
                            baseX + 180,
                            baseY + 15
                    );

                    context.drawTooltip(
                            this.textRenderer,
                            Text.translatable(MagicUtils.getCantripEffectTranslationKey(cantrip.effect()) + ".desc").formatted(Formatting.DARK_GRAY),
                            baseX + 10,
                            baseY + 35
                    );

                    context.drawTooltip(
                            this.textRenderer,
                            Text.literal("Inflicted by " + cantripComponent.getLastInflictor()).withColor(cantrip.effect().type().getColor()),
                            whyDoINeedThis,
                            baseY + 15
                    );
                } else {
                    context.drawTooltip(
                            this.textRenderer,
                            Text.translatable("foxglove.magic_display.empty.name").withColor(cantrip.effect().type().getColor()),
                            baseX + 10,
                            baseY + 15
                    );

                    context.drawTooltip(
                            this.textRenderer,
                            Text.translatable("foxglove.magic_display.empty.effect_name").withColor(cantrip.effect().type().getColor()),
                            baseX + 180,
                            baseY + 15
                    );

                    context.drawTooltip(
                            this.textRenderer,
                            Text.translatable("foxglove.magic_display.empty.effects").formatted(Formatting.DARK_GRAY),
                            baseX + 10,
                            baseY + 35
                    );

                    context.drawTooltip(
                            this.textRenderer,
                            Text.translatable("foxglove.magic_display.empty.inflictor").withColor(cantrip.effect().type().getColor()),
                            whyDoINeedThis,
                            baseY + 15
                    );
                }
            }
        }
        super.render(context, mouseX, mouseY, delta);
    }

    public boolean shouldPause() {
        return false;
    }

    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderDarkening(context);
        super.renderBackground(context, mouseX, mouseY, delta);
    }

    public void blur() {}
    protected void applyBlur(float delta) {}
}
