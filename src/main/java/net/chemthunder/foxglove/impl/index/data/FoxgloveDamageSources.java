package net.chemthunder.foxglove.impl.index.data;

import net.chemthunder.foxglove.impl.Foxglove;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.ArrayList;
import java.util.List;

public interface FoxgloveDamageSources {
    List<DamageSourceData> DATA = new ArrayList<>();

    RegistryKey<DamageType> FRAYING = register("fraying", 4.0f);

    private static RegistryKey<DamageType> register(String name, float exhaustion) {
        RegistryKey<DamageType> key = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Foxglove.id(name));
        DamageSourceData data = new DamageSourceData(key, name, exhaustion);

        DATA.add(data);
        return key;
    }

    static void bootstrap(Registerable<DamageType> registerable) {
        DATA.forEach(damageSourceData -> registerable.register(damageSourceData.key, new DamageType(damageSourceData.name, damageSourceData.exhaustion)));
    }

    record DamageSourceData(RegistryKey<DamageType> key, String name, float exhaustion) {}
}
