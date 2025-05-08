package xyz.axie.nbt

import xyz.axie.nbt.impl.ByteArrayTagImpl
import xyz.axie.nbt.impl.ImmutableListTagImpl
import xyz.axie.nbt.impl.IntArrayTagImpl
import xyz.axie.nbt.impl.LongArrayTagImpl
import xyz.axie.nbt.util.ByteConsumer
import xyz.axie.nbt.util.FloatConsumer
import xyz.axie.nbt.util.ShortConsumer
import org.jetbrains.annotations.Contract
import org.pcollections.TreePVector
import java.util.function.*

/**
 * A list tag.
 */
interface ListTag : CollectionTag<Tag>, ScopedTag<ListTag> {

    /**
     * Gets the backing data for this list tag.
     *
     * @return The backing data.
     */
    val data: List<Tag>

    /**
     * Checks if this list tag is a mutable list tag.
     *
     * @return `true` if this list tag is mutable, otherwise `false`.
     */
    fun isMutable(): Boolean = this is MutableListTag

    /**
     * Checks if this list tag is an immutable list tag.
     *
     * @return `true` if this list tag is immutable, otherwise `false`
     */
    fun isImmutable(): Boolean = this is ImmutableListTag

    /**
     * Checks if this list tag contains the given element.
     *
     * @param element The element.
     *
     * @return `true` if this list tag contains the element, otherwise `false`.
     */
    fun contains(element: Tag): Boolean

    /**
     * Checks if this list tag contains the all given elements.
     *
     * @param elements The elements.
     *
     * @return `true` if this list tag contains all the elements, otherwise `false`.
     */
    fun containsAll(elements: Collection<Tag?>): Boolean

    /**
     * Gets the tag at the given index.
     *
     * @param index The index.
     *
     * @return The tag.
     */
    fun get(index: Int): Tag

    /**
     * Gets the boolean value at the given index, or returns the
     * default value if there is no boolean value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The boolean value, or the default value if not present.
     */
    fun getBoolean(index: Int, defaultValue: Boolean): Boolean

    /**
     * Gets the boolean value at the given index, or returns false
     * if there is no boolean value at the given index.
     *
     * @param index The index.
     *
     * @return The boolean value, or false if not present.
     */
    fun getBoolean(index: Int): Boolean {
        return getBoolean(index, false)
    }

    /**
     * Gets the byte value at the given index, or returns the
     * default value if there is no byte value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The byte value, or the default value if not present.
     */
    fun getByte(index: Int, defaultValue: Byte): Byte

    /**
     * Gets the byte value at the given index, or returns 0
     * if there is no byte value at the given index.
     *
     * @param index The index.
     *
     * @return The byte value, or 0 if not present.
     */
    fun getByte(index: Int): Byte {
        return getByte(index, 0.toByte())
    }

    /**
     * Gets the short value at the given index, or returns the
     * default value if there is no short value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The short value, or the default value if not present.
     */
    fun getShort(index: Int, defaultValue: Short): Short

    /**
     * Gets the short value at the given index, or returns 0
     * if there is no short value at the given index.
     *
     * @param index The index.
     *
     * @return The short value, or 0 if not present.
     */
    fun getShort(index: Int): Short {
        return getShort(index, 0.toShort())
    }

    /**
     * Gets the int value at the given index, or returns the
     * default value if there is no int value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The int value, or the default value if not present.
     */
    fun getInt(index: Int, defaultValue: Int): Int

    /**
     * Gets the int value at the given index, or returns 0
     * if there is no int value at the given index.
     *
     * @param index The index.
     *
     * @return The int value, or 0 if not present.
     */
    fun getInt(index: Int): Int {
        return getInt(index, 0)
    }

    /**
     * Gets the long value at the given index, or returns the
     * default value if there is no long value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The long value, or the default value if not present.
     */
    fun getLong(index: Int, defaultValue: Long): Long

    /**
     * Gets the long value at the given index, or returns 0
     * if there is no long value at the given index.
     *
     * @param index The index.
     *
     * @return The long value, or 0 if not present.
     */
    fun getLong(index: Int): Long {
        return getLong(index, 0L)
    }

    /**
     * Gets the float value at the given index, or returns the
     * default value if there is no float value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The float value, or the default value if not present.
     */
    fun getFloat(index: Int, defaultValue: Float): Float

    /**
     * Gets the float value at the given index, or returns 0
     * if there is no float value at the given index.
     *
     * @param index The index.
     *
     * @return The float value, or 0 if not present.
     */
    fun getFloat(index: Int): Float {
        return getFloat(index, 0.0f)
    }

    /**
     * Gets the double value at the given index, or returns the
     * default value if there is no double value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The double value, or the default value if not present.
     */
    fun getDouble(index: Int, defaultValue: Double): Double

    /**
     * Gets the double value at the given index, or returns 0
     * if there is no double value at the given index.
     *
     * @param index The index.
     *
     * @return The double value, or 0 if not present.
     */
    fun getDouble(index: Int): Double {
        return getDouble(index, 0.0)
    }

    /**
     * Gets the string value at the given index, or returns the
     * default value if there is no string value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The string value, or the default value if not present.
     */
    fun getString(index: Int, defaultValue: String): String

    /**
     * Gets the string value at the given index, or returns an empty string
     * if there is no string value at the given index.
     *
     * @param index The index.
     *
     * @return The string value, or an empty string if not present.
     */
    fun getString(index: Int): String {
        return getString(index, "")
    }

    /**
     * Gets the byte array value at the given index, or returns the
     * default value if there is no byte array value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The byte array value, or the default value if not present.
     */
    fun getByteArray(index: Int, defaultValue: ByteArray): ByteArray

    /**
     * Gets the byte array value at the given index, or returns an empty byte array
     * if there is no byte array value at the given index.
     *
     * @param index The index.
     *
     * @return The byte array value, or an empty byte array if not present.
     */
    fun getByteArray(index: Int): ByteArray {
        return getByteArray(index, ByteArrayTagImpl.EMPTY_DATA)
    }

    /**
     * Gets the int array value at the given index, or returns the
     * default value if there is no int array value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The int array value, or the default value if not present.
     */
    fun getIntArray(index: Int, defaultValue: IntArray): IntArray

    /**
     * Gets the int array value at the given index, or returns an empty int array
     * if there is no int array value at the given index.
     *
     * @param index The index.
     *
     * @return The int array value, or an empty int array if not present.
     */
    fun getIntArray(index: Int): IntArray {
        return getIntArray(index, IntArrayTagImpl.EMPTY_DATA)
    }

    /**
     * Gets the long array value at the given index, or returns the
     * default value if there is no long array value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The long array value, or the default value if not present.
     */
    fun getLongArray(index: Int, defaultValue: LongArray): LongArray

    /**
     * Gets the long array value at the given index, or returns an empty long array
     * if there is no long array value at the given index.
     *
     * @param index The index.
     *
     * @return The long array value, or an empty long array if not present.
     */
    fun getLongArray(index: Int): LongArray {
        return getLongArray(index, LongArrayTagImpl.EMPTY_DATA)
    }

    /**
     * Gets the list tag value at the given index, or returns the
     * default value if there is no list tag value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The list tag value, or the default value if not present.
     */
    fun getList(index: Int, defaultValue: ListTag): ListTag

    /**
     * Gets the list tag value at the given index, or returns an empty list tag
     * if there is no list tag value at the given index.
     *
     * @param index The index.
     *
     * @return The list tag value, or an empty list tag if not present.
     */
    fun getList(index: Int): ListTag {
        return getList(index, EMPTY)
    }

    /**
     * Gets the compound tag value at the given index, or returns the
     * default value if there is no compound tag value at the given index.
     *
     * @param index The index.
     * @param defaultValue The default value to return if the value was not present.
     *
     * @return The compound tag value, or the default value if not present.
     */
    fun getCompound(index: Int, defaultValue: CompoundTag): CompoundTag

    /**
     * Gets the compound tag value at the given index, or returns an empty compound tag
     * if there is no compound tag value at the given index.
     *
     * @param index The index.
     *
     * @return The compound tag value, or an empty compound tag if not present.
     */
    fun getCompound(index: Int): CompoundTag {
        return getCompound(index, CompoundTag.EMPTY)
    }

    /**
     * Adds the given tag to this list and returns the resulting list.
     *
     * If the ID of the given tag does not match the [elementType]
     * of this list tag, this method will throw an [UnsupportedOperationException].
     *
     * @param tag The tag to add.
     *
     * @return The resulting list tag.
     *
     * @throws UnsupportedOperationException If the given tag is not of the correct type for this list.
     */
    fun add(tag: Tag): ListTag

    /**
     * Adds all the given tags to this list and returns the resulting list.
     *
     * If the ID of any of the tags does not match the
     * [elementType] of this list tag, this method will throw an
     * [java.lang.UnsupportedOperationException].
     *
     * @param tags The tags to add.
     *
     * @return The resulting list tag.
     *
     * @throws UnsupportedOperationException if any of the given tags are not of the correct type for this list.
     */
    fun addAll(tags: Collection<Tag>): ListTag

    /**
     * Removes the tag at the given index from this list and returns the
     * resulting list tag.
     *
     * @param index The index of the tag to remove.
     *
     * @return The resulting list tag.
     */
    fun remove(index: Int): ListTag

    /**
     * Removes the given tag from this list and returns the resulting list tag.
     *
     * @param tag The tag to remove.
     *
     * @return The resulting list tag.
     */
    fun remove(tag: Tag): ListTag

    /**
     * Removes all the given tags from this list and returns the resulting
     * list.
     *
     * @param tags The tags to remove.
     *
     * @return The resulting list tag.
     */
    fun removeAll(tags: Collection<Tag>): ListTag

    /**
     * Removes all tags from this list that match the given predicate and
     * returns the resulting list.
     *
     * @param predicate The predicate to test tags against.
     *
     * @return The resulting list tag.
     */
    fun removeIf(predicate: Predicate<in Tag>): ListTag

    /**
     * Sets the tag at the given index in this list to the given tag and
     * returns the resulting list tag.
     *
     * @param index The index to set.
     * @param tag The new tag value.
     *
     * @return The resulting list tag.
     */
    fun set(index: Int, tag: Tag): ListTag

    /**
     * Sets the tag at the given index in this list to the given boolean value
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param value The new boolean value.
     *
     * @return The resulting list tag.
     */
    fun setBoolean(index: Int, value: Boolean): ListTag

    /**
     * Sets the tag at the given index in this list to the given byte value
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param value The new byte value.
     *
     * @return The resulting list tag.
     */
    fun setByte(index: Int, value: Byte): ListTag

    /**
     * Sets the tag at the given index in this list to the given short value
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param value The new short value.
     *
     * @return The resulting list tag.
     */
    fun setShort(index: Int, value: Short): ListTag

    /**
     * Sets the tag at the given index in this list to the given int value
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param value The new int value.
     *
     * @return The resulting list tag.
     */
    fun setInt(index: Int, value: Int): ListTag

    /**
     * Sets the tag at the given index in this list to the given long value
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param value The new long value.
     *
     * @return The resulting list tag.
     */
    fun setLong(index: Int, value: Long): ListTag

    /**
     * Sets the tag at the given index in this list to the given float value
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param value The new float value.
     *
     * @return The resulting list tag.
     */
    fun setFloat(index: Int, value: Float): ListTag

    /**
     * Sets the tag at the given index in this list to the given double value
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param value The new double value.
     *
     * @return The resulting list tag.
     */
    fun setDouble(index: Int, value: Double): ListTag

    /**
     * Sets the tag at the given index in this list to the given string value
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param value The new string value.
     *
     * @return The resulting list tag.
     */
    fun setString(index: Int, value: String): ListTag

    /**
     * Sets the tag at the given index in this list to the given byte array value
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param value The new byte array value.
     *
     * @return The resulting list tag.
     */
    fun setByteArray(index: Int, value: ByteArray): ListTag

    /**
     * Sets the tag at the given index in this list to the given byte values
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param values The new byte values.
     *
     * @return The resulting list tag.
     */
    fun setBytes(index: Int, vararg values: Byte): ListTag

    /**
     * Sets the tag at the given index in this list to the given int array value
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param value The new int array value.
     *
     * @return The resulting list tag.
     */
    fun setIntArray(index: Int, value: IntArray): ListTag

    /**
     * Sets the tag at the given index in this list to the given int values
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param values The new int values.
     *
     * @return The resulting list tag.
     */
    fun setInts(index: Int, vararg values: Int): ListTag

    /**
     * Sets the tag at the given index in this list to the given long array value
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param value The new long array value.
     *
     * @return The resulting list tag.
     */
    fun setLongArray(index: Int, value: LongArray): ListTag

    /**
     * Sets the tag at the given index in this list to the given long values
     * and returns the resulting list tag.
     *
     * @param index The index to set.
     * @param values The new long values.
     *
     * @return The resulting list tag.
     */
    fun setLongs(index: Int, vararg values: Long): ListTag

    /**
     * Performs the given action on every byte in this list.
     *
     * @param action The action to perform
     */
    fun forEachByte(action: ByteConsumer)

    /**
     * Performs the given action on every short in this list.
     *
     * @param action The action to perform.
     */
    fun forEachShort(action: ShortConsumer)

    /**
     * Performs the given action on every int in this list.
     *
     * @param action The action to perform.
     */
    fun forEachInt(action: IntConsumer)

    /**
     * Performs the given action on every long in this list.
     *
     * @param action The action to perform.
     */
    fun forEachLong(action: LongConsumer)

    /**
     * Performs the given action on every float in this list.
     *
     * @param action The action to perform.
     */
    fun forEachFloat(action: FloatConsumer)

    /**
     * Performs the given action on every double in this list.
     *
     * @param action The action to perform.
     */
    fun forEachDouble(action: DoubleConsumer)

    /**
     * Performs the given action on every string in this list.
     *
     * @param action The action to perform.
     */
    fun forEachString(action: Consumer<String?>)

    /**
     * Performs the given action on every byte array in this list.
     *
     * @param action The action to perform.
     */
    fun forEachByteArray(action: Consumer<ByteArray?>)

    /**
     * Performs the given action on every int array in this list.
     *
     * @param action The action to perform.
     */
    fun forEachIntArray(action: Consumer<IntArray?>)

    /**
     * Performs the given action on every long array in this list.
     *
     * @param action The action to perform.
     */
    fun forEachLongArray(action: Consumer<LongArray?>)

    /**
     * Performs the given action on every list tag in this list.
     *
     * @param action The action to perform.
     */
    fun forEachList(action: Consumer<ListTag?>)

    /**
     * Performs the given action on every compound tag in this list.
     *
     * @param action The action to perform.
     */
    fun forEachCompound(action: Consumer<CompoundTag?>)

    /**
     * Converts this list tag to its mutable equivalent.
     *
     * If this tag is already mutable, it will simply return itself.
     * It will **not** create a defensive copy.
     *
     * @return This tag in its mutable form.
     */
    fun asMutable(): MutableListTag

    /**
     * Converts this list tag to its immutable equivalent.
     *
     * If this tag is already immutable, it will simply return itself.
     *
     * @return This tag in its immutable form.
     */
    fun asImmutable(): ImmutableListTag

    /**
     * Converts this list tag in to a builder with the [data][.getData]
     * of this tag already applied, allowing for easy bulk changes.
     *
     * @return This list tag as a builder.
     */
    fun toBuilder(): Builder

    /**
     * A builder for building list tags.
     */
    interface Builder {

        /**
         * Adds the given tag to this builder.
         *
         * @param tag The tag to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun add(tag: Tag): Builder

        /**
         * Adds the given compound tag to this builder.
         *
         * @param builder The compound tag to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addCompound(builder: Consumer<CompoundTag.Builder>): Builder

        /**
         * Adds the given boolean value to this builder.
         *
         * @param value The value to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addBoolean(value: Boolean): Builder

        /**
         * Adds the given byte value to this builder.
         *
         * @param value The value to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addByte(value: Byte): Builder

        /**
         * Adds the given short value to this builder.
         *
         * @param value The value to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addShort(value: Short): Builder

        /**
         * Adds the given int value to this builder.
         *
         * @param value The value to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addInt(value: Int): Builder

        /**
         * Adds the given long value to this builder.
         *
         * @param value The value to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addLong(value: Long): Builder

        /**
         * Adds the given float value to this builder.
         *
         * @param value The value to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addFloat(value: Float): Builder

        /**
         * Adds the given double value to this builder.
         *
         * @param value The value to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addDouble(value: Double): Builder

        /**
         * Adds the given string value to this builder.
         *
         * @param value The value to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addString(value: String): Builder

        /**
         * Adds the given byte array value to this builder.
         *
         * @param value The value to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addByteArray(value: ByteArray): Builder

        /**
         * Adds the given byte values to this builder.
         *
         * @param values The values to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addBytes(vararg values: Byte): Builder

        /**
         * Adds the given int array value to this builder.
         *
         * @param value The value to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addIntArray(value: IntArray): Builder

        /**
         * Adds the given int values to this builder.
         *
         * @param values The values to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addInts(vararg values: Int): Builder

        /**
         * Adds the given long array value to this builder.
         *
         * @param value The value to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addLongArray(value: LongArray): Builder

        /**
         * Adds the given long values to this builder.
         *
         * @param values The values to add.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun addLongs(vararg values: Long): Builder

        /**
         * Removes the given element from this builder.
         *
         * @param element The element to remove.
         *
         * @return This builder.
         */
        @Contract(value = "_ -> this", mutates = "this")
        fun remove(element: Tag): Builder

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
        fun from(other: ListTag): Builder

        /**
         * Builds this builder into a list tag.
         *
         * @return The built list tag.
         */
        @Contract(value = "-> new", pure = true)
        fun build(): ListTag
    }

    companion object {

        /**
         * The empty list tag.
         */
        @JvmStatic
        val EMPTY: ImmutableListTag = ImmutableListTagImpl(TreePVector.empty(), EndTag.ID)

        /**
         * The ID of this type of tag.
         *
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 9

        /**
         * The tag type for this tag.
         */
        @JvmStatic
        val TYPE: TagType<ListTag> = AbstractListTag.createType()

    }

}