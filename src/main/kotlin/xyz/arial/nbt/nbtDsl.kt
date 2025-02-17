@file:JvmSynthetic
package xyz.arial.nbt

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@DslMarker
internal annotation class NBTDsl

/**
 * Creates a new compound tag from the given [builder].
 *
 * @param builder the builder
 * @return a new compound tag
 */
@NBTDsl
@JvmSynthetic
inline fun compound(builder: CompoundTag.Builder.() -> Unit): CompoundTag = ImmutableCompoundTag.builder().apply(builder).build()

/**
 * Creates a new compound tag builder and applies the given [builder] to it.
 *
 * @param builder the builder
 * @return a new compound tag builder
 */
@NBTDsl
@JvmSynthetic
inline fun buildCompound(builder: CompoundTag.Builder.() -> Unit): CompoundTag.Builder = ImmutableCompoundTag.builder().apply(builder)

/**
 * Creates a new list tag from the given [builder].
 *
 * @param builder the builder
 * @return a new list tag
 */
@NBTDsl
@JvmSynthetic
inline fun list(builder: ListTag.Builder.() -> Unit): ListTag = ImmutableListTag.builder().apply(builder).build()

/**
 * Creates a new list tag builder and applies the given [builder] to it.
 *
 * @param builder the builder
 * @return a new list tag builder
 */
@NBTDsl
@JvmSynthetic
inline fun buildList(builder: ListTag.Builder.() -> Unit): ListTag.Builder = ImmutableListTag.builder().apply(builder)