package xyz.axie.nbt

import xyz.axie.nbt.impl.ByteArrayTagImpl
import xyz.axie.nbt.impl.ImmutableCompoundTagImpl
import xyz.axie.nbt.impl.IntArrayTagImpl
import xyz.axie.nbt.impl.LongArrayTagImpl
import xyz.axie.nbt.util.ObjByteConsumer
import xyz.axie.nbt.util.ObjFloatConsumer
import xyz.axie.nbt.util.ObjShortConsumer
import org.jetbrains.annotations.Contract
import org.pcollections.HashTreePMap
import java.util.function.*
import java.util.function.Function

/**
 * A tag that holds a map of keys to tags.
 */
interface CompoundTag : ScopedTag<CompoundTag> {

    /**
     * Gets the backing data for this compound tag.
     *
     * @return The backing data.
     */
    val data: Map<String, Tag>

    /**
     * Gets the size of this compound tag.
     *
     * @return The size.
     */
    val size: Int

    /**
     * Checks if this compound tag is a mutable compound tag.
     *
     * @return `true` if this compound tag is mutable, otherwise `false`.
     */
    fun isMutable(): Boolean = this is MutableCompoundTag

    /**
     * Checks if this compound tag is an immutable compound tag.
     *
     * @return `true` if this compound tag is immutable, otherwise `false`.
     */
    fun isImmutable(): Boolean = this is ImmutableCompoundTag

    /**
     * Checks if this compound tag is empty.
     *
     * @return `true` if this compound tag is empty, otherwise `false`.
     */
    fun isEmpty(): Boolean

    /**
     * Gets the keys in this compound tag.
     *
     * @return The keys.
     */
    fun keySet(): Set<String?>

    /**
     * Gets the values in this compound tag.
     *
     * @return The values.
     */
    fun values(): Collection<Tag>

    /**
     * Gets the type of the tag with the given name.
     *
     * @param name The name.
     *
     * @return The tag type ID.
     */
    fun type(name: String): Int

    /**
     * Checks if this compound tag contains the given name (key).
     *
     * @param name The name.
     *
     * @return `true` if this compound tag contains a tag with the given name, otherwise `false`.
     */
    fun contains(name: String): Boolean

    /**
     * Checks if this compound tag contains the given name (key), and that the
     * retrieved tag is of the given expected type.
     *
     * @param name The name.
     * @param type The ID of the expected tag type.
     *
     * @return `true` if this compound tag contains a tag with the given name and of the given type, otherwise `false`.
     */
    fun contains(name: String, type: Int): Boolean

    /**
     * Gets the tag with the given name from this compound, or returns null
     * if there is no tag with the given name in this compound.
     *
     * @param name The name of the tag.
     *
     * @return The tag, or null if not present.
     */
    fun get(name: String): Tag?

    /**
     * Gets the boolean value for the given name, or returns the
     * default value if there is no boolean value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The boolean value, or the default value if not present.
     */
    fun getBoolean(name: String, defaultValue: Boolean): Boolean

    /**
     * Gets the boolean value for the given name, or returns false
     * if there is no boolean value for the given name.
     *
     * @param name The name.
     *
     * @return The boolean value, or false if not present.
     */
    fun getBoolean(name: String): Boolean {
        return getBoolean(name, false)
    }

    /**
     * Gets the byte value for the given name, or returns the
     * default value if there is no byte value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The byte value, or the default value if not present.
     */
    fun getByte(name: String, defaultValue: Byte): Byte

    /**
     * Gets the byte value for the given name, or returns 0
     * if there is no byte value for the given name.
     *
     * @param name The name.
     *
     * @return The byte value, or 0 if not present.
     */
    fun getByte(name: String): Byte {
        return getByte(name, 0.toByte())
    }

    /**
     * Gets the short value for the given name, or returns the
     * default value if there is no short value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The short value, or the default value if not present.
     */
    fun getShort(name: String, defaultValue: Short): Short

    /**
     * Gets the short value for the given name, or returns 0
     * if there is no short value for the given name.
     *
     * @param name The name.
     *
     * @return The short value, or 0 if not present.
     */
    fun getShort(name: String): Short {
        return getShort(name, 0.toShort())
    }

    /**
     * Gets the int value for the given name, or returns the
     * default value if there is no int value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The int value, or the default value if not present.
     */
    fun getInt(name: String, defaultValue: Int): Int

    /**
     * Gets the int value for the given name, or returns 0
     * if there is no int value for the given name.
     *
     * @param name The name.
     *
     * @return The int value, or 0 if not present.
     */
    fun getInt(name: String): Int {
        return getInt(name, 0)
    }

    /**
     * Gets the long value for the given name, or returns the
     * default value if there is no long value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The long value, or the default value if not present.
     */
    fun getLong(name: String, defaultValue: Long): Long

    /**
     * Gets the long value for the given name, or returns 0
     * if there is no long value for the given name.
     *
     * @param name The name.
     *
     * @return The long value, or 0 if not present.
     */
    fun getLong(name: String): Long {
        return getLong(name, 0L)
    }

    /**
     * Gets the float value for the given name, or returns the
     * default value if there is no float value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The float value, or the default value if not present.
     */
    fun getFloat(name: String, defaultValue: Float): Float

    /**
     * Gets the float value for the given name, or returns 0
     * if there is no float value for the given name.
     *
     * @param name The name.
     *
     * @return The float value, or 0 if not present.
     */
    fun getFloat(name: String): Float {
        return getFloat(name, 0.0f)
    }

    /**
     * Gets the double value for the given name, or returns the
     * default value if there is no double value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The double value, or the default value if not present.
     */
    fun getDouble(name: String, defaultValue: Double): Double

    /**
     * Gets the double value for the given name, or returns 0
     * if there is no double value for the given name.
     *
     * @param name The name.
     *
     * @return The double value, or 0 if not present.
     */
    fun getDouble(name: String): Double {
        return getDouble(name, 0.0)
    }

    /**
     * Gets the string value for the given name, or returns the
     * default value if there is no string value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The string value, or the default value if not present.
     */
    fun getString(name: String, defaultValue: String): String

    /**
     * Gets the string value for the given name, or returns an empty string
     * if there is no string value for the given name.
     *
     * @param name The name.
     *
     * @return The string value, or an empty string if not present.
     */
    fun getString(name: String): String {
        return getString(name, "")
    }

    /**
     * Gets the byte array value for the given name, or returns the
     * default value if there is no byte array value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The byte array value, or the default value if not present.
     */
    fun getByteArray(name: String, defaultValue: ByteArray): ByteArray

    /**
     * Gets the byte array value for the given name, or returns an empty byte array
     * if there is no byte array value for the given name.
     *
     * @param name The name.
     *
     * @return The byte array value, or an empty byte array if not present.
     */
    fun getByteArray(name: String): ByteArray {
        return getByteArray(name, ByteArrayTagImpl.EMPTY_DATA)
    }

    /**
     * Gets the int array value for the given name, or returns the
     * default value if there is no int array value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The int array value, or the default value if not present.
     */
    fun getIntArray(name: String, defaultValue: IntArray): IntArray

    /**
     * Gets the int array value for the given name, or returns an empty int array
     * if there is no int array value for the given name.
     *
     * @param name The name.
     *
     * @return The int array value, or an empty int array if not present.
     */
    fun getIntArray(name: String): IntArray {
        return getIntArray(name, IntArrayTagImpl.EMPTY_DATA)
    }

    /**
     * Gets the long array value for the given name, or returns the
     * default value if there is no long array value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The long array value, or the default value if not present.
     */
    fun getLongArray(name: String, defaultValue: LongArray): LongArray

    /**
     * Gets the long array value for the given name, or returns an empty long array
     * if there is no long array value for the given name.
     *
     * @param name The name.
     *
     * @return The long array value, or an empty long array if not present.
     */
    fun getLongArray(name: String): LongArray {
        return getLongArray(name, LongArrayTagImpl.EMPTY_DATA)
    }

    /**
     * Gets the list tag value for the given name, or returns the
     * default value if there is no list tag value for the given name.
     *
     * @param name The name.
     * @param elementType The element type.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The list tag value, or the default value if not present.
     */
    fun getList(name: String, elementType: Int, defaultValue: ListTag): ListTag

    /**
     * Gets the list tag value for the given name, or returns an empty list tag
     * if there is no list tag value for the given name.
     *
     * @param name The name.
     * @param elementType The element type.
     *
     * @return The list tag value, or an empty list tag if not present.
     */
    fun getList(name: String, elementType: Int): ListTag {
        return getList(name, elementType, ListTag.EMPTY)
    }

    /**
     * Gets the compound tag value for the given name, or returns the
     * default value if there is no compound tag value for the given name.
     *
     * @param name The name.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The compound tag value, or the default value if not present.
     */
    fun getCompound(name: String, defaultValue: CompoundTag): CompoundTag

    /**
     * Gets the compound tag value for the given name, or returns an empty compound tag
     * if there is no compound tag value for the given name.
     *
     * @param name The name.
     *
     * @return The compound tag value, or an empty compound tag if not present.
     */
    fun getCompound(name: String): CompoundTag {
        return getCompound(name, EMPTY)
    }

    /**
     * Sets the given name in this compound to the given value and returns the
     * resulting compound tag.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun put(name: String, value: Tag): CompoundTag

    /**
     * Removes the given name from this compound and returns the resulting
     * compound.
     *
     * @param name The name.
     *
     * @return The resulting compound.
     */
    fun remove(name: String): CompoundTag

    /**
     * Sets the given name in this compound to the given boolean value and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun putBoolean(name: String, value: Boolean): CompoundTag

    /**
     * Sets the given name in this compound to the given byte value and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun putByte(name: String, value: Byte): CompoundTag

    /**
     * Sets the given name in this compound to the given short value and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun putShort(name: String, value: Short): CompoundTag

    /**
     * Sets the given name in this compound to the given int value and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun putInt(name: String, value: Int): CompoundTag

    /**
     * Sets the given name in this compound to the given long value and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun putLong(name: String, value: Long): CompoundTag

    /**
     * Sets the given name in this compound to the given float value and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun putFloat(name: String, value: Float): CompoundTag

    /**
     * Sets the given name in this compound to the given double value and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun putDouble(name: String, value: Double): CompoundTag

    /**
     * Sets the given name in this compound to the given string value and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun putString(name: String, value: String): CompoundTag

    /**
     * Sets the given name in this compound to the given byte array value and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun putByteArray(name: String, value: ByteArray): CompoundTag

    /**
     * Sets the given name in this compound to the given byte values and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param values The values.
     *
     * @return The resulting compound.
     */
    fun putBytes(name: String, vararg values: Byte): CompoundTag

    /**
     * Sets the given name in this compound to the given int array value and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun putIntArray(name: String, value: IntArray): CompoundTag

    /**
     * Sets the given name in this compound to the given int values and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param values The values.
     *
     * @return The resulting compound.
     */
    fun putInts(name: String, vararg values: Int): CompoundTag

    /**
     * Sets the given name in this compound to the given long array value and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param value The value.
     *
     * @return The resulting compound.
     */
    fun putLongArray(name: String, value: LongArray): CompoundTag

    /**
     * Sets the given name in this compound to the given long values and
     * returns the resulting compound.
     *
     * @param name The name.
     * @param values The values.
     *
     * @return The resulting compound.
     */
    fun putLongs(name: String, vararg values: Long): CompoundTag

    /**
     * Gets the compound tag with the given name, applies the given action to
     * the compound tag, puts the resulting compound tag in to this compound,
     * and returns the resulting compound tag.
     *
     * @param name The name.
     * @param action The action to apply.
     *
     * @return The resulting compound tag.
     */
    fun update(name: String, action: Function<CompoundTag, CompoundTag>): CompoundTag

    /**
     * Gets the list tag with the given name, storing elements of the given
     * type, applies the given action to the list tag, puts the resulting list
     * tag in to this compound, and returns the resulting list tag.
     *
     * @param name The name.
     * @param type The element type.
     * @param action The action to apply.
     *
     * @return The resulting compound tag.
     */
    fun update(name: String, type: Int, action: Function<ListTag, ListTag>): CompoundTag

    /**
     * Performs the given action on every byte in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachByte(action: ObjByteConsumer<String>)

    /**
     * Performs the given action on every short in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachShort(action: ObjShortConsumer<String>)

    /**
     * Performs the given action on every int in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachInt(action: ObjIntConsumer<String>)

    /**
     * Performs the given action on every long in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachLong(action: ObjLongConsumer<String>)

    /**
     * Performs the given action on every float in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachFloat(action: ObjFloatConsumer<String>)

    /**
     * Performs the given action on every double in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachDouble(action: ObjDoubleConsumer<String>)

    /**
     * Performs the given action on every string in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachString(action: BiConsumer<String, String>)

    /**
     * Performs the given action on every byte array in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachByteArray(action: BiConsumer<String, ByteArray>)

    /**
     * Performs the given action on every int array in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachIntArray(action: BiConsumer<String, IntArray>)

    /**
     * Performs the given action on every long array in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachLongArray(action: BiConsumer<String, LongArray>)

    /**
     * Performs the given action on every list tag in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachList(action: BiConsumer<String, ListTag>)

    /**
     * Performs the given action on every compound tag in this compound.
     *
     * @param action The action to perform.
     */
    fun forEachCompound(action: BiConsumer<String, CompoundTag>)

    /**
     * Converts this compound tag to its mutable equivalent.
     *
     * If this tag is already mutable, it will simply return itself.
     * It will **not** create a defensive copy.
     *
     * @return This tag in its mutable form.
     */
    fun asMutable(): MutableCompoundTag

    /**
     * Converts this compound tag to its immutable equivalent.
     *
     * If this tag is already immutable, it will simply return itself.
     *
     * @return This tag in its immutable form.
     */
    fun asImmutable(): ImmutableCompoundTag

    /**
     * Converts this compound tag in to a builder with the [data]
     * of this tag already applied, allowing for easy bulk changes.
     *
     * @return This compound tag as a builder.
     */
    fun toBuilder(): Builder

    /**
     * A builder for building compound tags.
     */
    interface Builder {

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun put(name: String, value: Tag): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putBoolean(name: String, value: Boolean): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putByte(name: String, value: Byte): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putShort(name: String, value: Short): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putInt(name: String, value: Int): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putLong(name: String, value: Long): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putFloat(name: String, value: Float): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putDouble(name: String, value: Double): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putString(name: String, value: String): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putByteArray(name: String, value: ByteArray): Builder

        /**
         * Sets the value of the tag with the given name to the given values.
         *
         * @param name The name.
         * @param values The values.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putBytes(name: String, vararg values: Byte): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putIntArray(name: String, value: IntArray): Builder

        /**
         * Sets the value of the tag with the given name to the given values.
         *
         * @param name The name.
         * @param values The values.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putInts(name: String, vararg values: Int): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param value The value.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putLongArray(name: String, value: LongArray): Builder

        /**
         * Sets the value of the tag with the given name to the given values.
         *
         * @param name The name.
         * @param values The values.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putLongs(name: String, vararg values: Long): Builder

        /**
         * Sets the value of the tag with the given name to the result of
         * applying the given builder function to a new builder.
         *
         * @param name The name.
         * @param builder The builder.
         *
         * @return This builder.
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        fun putList(name: String, builder: Consumer<ListTag.Builder>): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param elementType The element type.
         * @param elements The elements.
         *
         * @return This builder.
         */
        @Contract(value = "_, _, _ -> this", mutates = "this")
        fun putList(name: String, elementType: Int, vararg elements: Tag): Builder

        /**
         * Sets the value of the tag with the given name to the given value.
         *
         * @param name The name.
         * @param elementType The element type.
         * @param elements The elements.
         *
         * @return This builder.
         */
        @Contract(value = "_, _, _ -> this", mutates = "this")
        fun putList(name: String, elementType: Int, elements: Collection<Tag>): Builder

        /**
         * Removes the value for the given key from this builder.
         *
         * @param key The key to remove.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun remove(key: String): Builder

        /**
         * Adds all the tags from the given other builder to this builder.
         *
         * @param other The other builder.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun from(other: Builder): Builder

        /**
         * Adds all the values from the other tag to this builder.
         *
         * @param other The other tag.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun from(other: CompoundTag): Builder

        /**
         * Builds this builder into a compound tag.
         *
         * @return The built compound tag.
         */
        @Contract(value = "-> new", pure = true)
        fun build(): CompoundTag

    }

    companion object {

        /**
         * The empty compound tag.
         */
        @JvmStatic
        val EMPTY: ImmutableCompoundTag = ImmutableCompoundTagImpl(HashTreePMap.empty())

        /**
         * The ID of this type of tag.
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 10

        /**
         * The tag type for this tag.
         */
        @JvmStatic
        val TYPE: TagType<CompoundTag> = AbstractCompoundTag.createType()

    }

}