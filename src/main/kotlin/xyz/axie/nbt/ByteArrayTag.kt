package xyz.axie.nbt

import xyz.axie.nbt.impl.ByteArrayTagImpl
import xyz.axie.nbt.util.ByteConsumer

/**
 * A tag that holds a byte array.
 */
interface ByteArrayTag : ListCollectionTag<ByteTag>, ScopedTag<ByteArrayTag> {

    /**
     * Gets the backing data for this tag.
     *
     * @return The backing data.
     */
    var data: ByteArray

    override fun get(index: Int): ByteTag

    /**
     * Sets the element at the given index to the given value.
     *
     * @param index The index.
     *
     * @param value The value.
     */
    fun set(index: Int, value: Byte)

    override fun set(index: Int, element: ByteTag): ByteTag

    /**
     * Adds the given value to this tag.
     *
     * @param value The value to add.
     */
    fun add(value: Byte)

    /**
     * Adds the given value to this tag at the given index.
     *
     * @param index The index.
     *
     * @param value The value to add.
     */
    fun add(index: Int, value: Byte)

    override fun add(index: Int, element: ByteTag)

    override fun clear()

    /**
     * Performs the given action on every element in this tag.
     *
     * @param action The action to perform.
     */
    fun forEachByte(action: ByteConsumer)

    companion object {

        /**
         * Creates a new byte array tag with the given data.
         *
         * @param data The data.
         *
         * @return A new byte array tag.
         */
        @JvmStatic
        fun of(data: ByteArray): ByteArrayTag {
            return ByteArrayTagImpl(data)
        }

        /**
         * The ID of this type of tag.
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 7

        /**
         * The tag type for this tag.
         */
        @JvmStatic
        val TYPE: TagType<ByteArrayTag> = ByteArrayTagImpl.createType()

    }
}