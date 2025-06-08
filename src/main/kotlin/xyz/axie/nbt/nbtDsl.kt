@file:JvmSynthetic
package xyz.axie.nbt

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@DslMarker
internal annotation class NBTDsl

/**
 * Creates a new compound tag from the given [builder].
 *
 * @param builder The builder.
 *
 * @return A new compound tag.
 */
@NBTDsl
@JvmSynthetic
inline fun compound(builder: CompoundTag.Builder.() -> Unit): CompoundTag = ImmutableCompoundTag.builder().apply(builder).build()

/**
 * Creates a new compound tag builder and applies the given [builder] to it.
 *
 * @param builder The builder.
 *
 * @return A new compound tag builder.
 */
@NBTDsl
@JvmSynthetic
inline fun buildCompound(builder: CompoundTag.Builder.() -> Unit): CompoundTag.Builder = ImmutableCompoundTag.builder().apply(builder)

/**
 * Creates a new list tag from the given [builder].
 *
 * @param builder The builder.
 *
 * @return A new list tag.
 */
@NBTDsl
@JvmSynthetic
inline fun list(builder: ListTag.Builder.() -> Unit): ListTag = ImmutableListTag.builder().apply(builder).build()

/**
 * Creates a new list tag builder and applies the given [builder] to it.
 *
 * @param builder The builder.
 *
 * @return A new list tag builder.
 */
@NBTDsl
@JvmSynthetic
inline fun buildList(builder: ListTag.Builder.() -> Unit): ListTag.Builder = ImmutableListTag.builder().apply(builder)

/**
 * Creates a new list tag builder and add it to the compound tag builder.
 *
 * @param builder The builder.
 *
 * @return This instance of a compound tag builder.
 */
@JvmSynthetic
inline fun CompoundTag.Builder.list(name: String, builder: ListTag.Builder.() -> Unit): CompoundTag.Builder =
    put(name, list(builder))

/**
 * Adds new compound tag to list.
 *
 * @param builder The builder.
 *
 * @return This instance of list builder.
 */
@JvmSynthetic
inline fun ListTag.Builder.compound(builder: CompoundTag.Builder.() -> Unit): ListTag.Builder =
    add(xyz.axie.nbt.compound(builder))

/**
 * Creates a new compound tag builder and add it to compound tag builder.
 *
 * @param builder The builder.
 *
 * @return This instance of a compound tag builder.
 */
@JvmSynthetic
inline fun CompoundTag.Builder.compound(name: String, builder: CompoundTag.Builder.() -> Unit): CompoundTag.Builder =
    put(name, compound(builder))