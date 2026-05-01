package net.chemthunder.foxglove.impl.cca.entity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.cantrip.Cantrip;
import net.chemthunder.foxglove.impl.Foxglove;
import net.chemthunder.foxglove.impl.index.FoxgloveCantripEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.List;

public class CantripComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<CantripComponent> KEY = MiscUtils.getOrCreateKey(Foxglove.id("cantrip"), CantripComponent.class);
    private final LivingEntity player;

    private int duration = 0;
    private String lastInflictor = "";

    private Cantrip heldCantrip = Cantrip.EMPTY;

    public CantripComponent(LivingEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void tick() {
        if (this.getDuration() > 0) {
            this.duration--;
            this.tickEffects();
            if (this.getDuration() == 0) {
                this.setHeldCantrip(Cantrip.EMPTY);
            }
        }
    }

    private void tickEffects() {
        if (this.getHeldCantrip().effect().equals(FoxgloveCantripEffects.CLOAK)) {
            List<LivingEntity> nearbyEntities = player.getWorld().getEntitiesByClass(LivingEntity.class, new Box(player.getBlockPos()).expand(3), entity -> entity != this.player);

            player.setInvisible(nearbyEntities.isEmpty());
        }
    }

    public void setDuration(int i) {
        this.duration = i;
        this.sync();
    }

    public void setHeldCantrip(Cantrip s) {
        this.heldCantrip = s;
        this.sync();
    }

    public void set(int duration, Cantrip cantrip, String lastInflictor) {
        this.duration = duration;
        this.heldCantrip = cantrip;
        this.lastInflictor = lastInflictor;

        if (this.player instanceof PlayerEntity playerEntity) {
            playerEntity.sendMessage(Text.literal("You have been inflicted with a " + cantrip.effect().type().asString() + "!").withColor(cantrip.effect().type().getColor()).formatted(Formatting.ITALIC), true);
            playerEntity.playSoundToPlayer(SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), SoundCategory.PLAYERS, 1, 1);
        }
        this.sync();
    }

    public int getDuration() {
        return this.duration;
    }

    public Cantrip getHeldCantrip() {
        return this.heldCantrip;
    }

    public String getLastInflictor() {
        return this.lastInflictor;
    }

    public void setLastInflictor(String s) {
        this.lastInflictor = s;
        this.sync();
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.duration = nbtCompound.getInt("Duration");
        this.lastInflictor = nbtCompound.getString("LastInflictor");

        if (nbtCompound.contains("HeldCantrip", NbtElement.COMPOUND_TYPE)) {
            NbtCompound compound = nbtCompound.getCompound("HeldCantrip");
            this.heldCantrip = Cantrip.CODEC.parse(wrapperLookup.getOps(NbtOps.INSTANCE), compound).resultOrPartial(Foxglove.LOGGER::error).orElseThrow();
        } else {
            this.heldCantrip = Cantrip.EMPTY;
        }
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("Duration", duration);
        nbtCompound.putString("LastInflictor", lastInflictor);

        if (this.heldCantrip != Cantrip.EMPTY) {
            nbtCompound.put("HeldCantrip", Cantrip.CODEC.encodeStart(wrapperLookup.getOps(NbtOps.INSTANCE), this.heldCantrip).getOrThrow());
        }
    }
}
