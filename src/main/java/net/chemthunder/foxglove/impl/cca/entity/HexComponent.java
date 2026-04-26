package net.chemthunder.foxglove.impl.cca.entity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.hex.Hex;
import net.chemthunder.foxglove.impl.Foxglove;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class HexComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<HexComponent> KEY = MiscUtils.getOrCreateKey(Foxglove.id("hex"), HexComponent.class);
    private final LivingEntity player;

    private int duration = 0;

    private Hex heldHex = Hex.EMPTY;

    public HexComponent(LivingEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void tick() {
        if (this.getDuration() > 0) {
            this.duration--;
            this.tickDebug();
            if (this.getDuration() == 0) {
                this.setHeldHex(Hex.EMPTY);
            }
        }
    }

    private void tickDebug() {
        if (this.player instanceof PlayerEntity p) {
            //  p.sendMessage(Text.literal(this.getDuration() + " " + this.getHeldCantrip().name() + " " + this.getHeldCantrip().effect().name() + " " + this.getHeldCantrip().effect().type().asString()), true);
        }
    }

    public void setDuration(int i) {
        this.duration = i;
        this.sync();
    }

    public void setHeldHex(Hex s) {
        this.heldHex = s;
        this.sync();
    }

    public void set(int duration, Hex hex) {
        this.duration = duration;
        this.heldHex = hex;
        this.sync();
    }

    public int getDuration() {
        return this.duration;
    }

    public Hex getHeldHex() {
        return this.heldHex;
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.duration = nbtCompound.getInt("Duration");

        if (nbtCompound.contains("HeldHex", NbtElement.COMPOUND_TYPE)) {
            NbtCompound compound = nbtCompound.getCompound("HeldHex");
            this.heldHex = Hex.CODEC.parse(wrapperLookup.getOps(NbtOps.INSTANCE), compound).resultOrPartial(Foxglove.LOGGER::error).orElseThrow();
        } else {
            this.heldHex = Hex.EMPTY;
        }
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("Duration", duration);

        if (this.heldHex != Hex.EMPTY) {
            nbtCompound.put("HeldHex", Hex.CODEC.encodeStart(wrapperLookup.getOps(NbtOps.INSTANCE), this.heldHex).getOrThrow());
        }
    }
}
