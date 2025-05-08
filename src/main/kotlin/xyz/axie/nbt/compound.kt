package xyz.axie.nbt

@JvmSynthetic
inline fun CompoundTag.Builder.list(name: String, builder: ListTag.Builder.() -> Unit): CompoundTag.Builder =
    put(name, ImmutableListTag.builder().apply(builder).build())

@JvmSynthetic
inline fun CompoundTag.Builder.compound(name: String, builder: CompoundTag.Builder.() -> Unit): CompoundTag.Builder =
    put(name, ImmutableCompoundTag.builder().apply(builder).build())