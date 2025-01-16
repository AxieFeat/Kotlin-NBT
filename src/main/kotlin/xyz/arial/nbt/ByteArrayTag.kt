package xyz.arial.nbt

import xyz.arial.nbt.impl.ByteArrayTagImpl
import xyz.arial.nbt.util.ByteConsumer

/**
 * A tag that holds a byte array.
 */
interface ByteArrayTag : ListCollectionTag<ByteTag>, ScopedTag<ByteArrayTag> {

    /**
     * Gets the backing data for this tag.
     *
     * @return the backing data
     */
    var data: ByteArray

    override fun get(index: Int): ByteTag

    /**
     * Sets the element at the given index to the given value.
     *
     * @param index the index
     * @param value the value
     */
    fun set(index: Int, value: Byte)

    override fun set(index: Int, element: ByteTag): ByteTag

    /**
     * Adds the given value to this tag.
     *
     * @param value the value to add
     */
    fun add(value: Byte)

    /**
     * Adds the given value to this tag at the given index.
     *
     * @param index the index
     * @param value the value to add
     */
    fun add(index: Int, value: Byte)

    override fun add(index: Int, element: ByteTag)

    fun remove(index: Int): ByteTag

    override fun clear()

    /**
     * Performs the given action on every element in this tag.
     *
     * @param action the action to perform
     */
    fun forEachByte(action: ByteConsumer)

    companion object {
        /**
         * Creates a new byte array tag with the given data.
         *
         * @param data the data
         * @return a new byte array tag
         */
        fun of(data: ByteArray): ByteArrayTag {
            return ByteArrayTagImpl(data)
        }

        /**
         * The ID of this type of tag.
         *
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 7

        /**
         * The tag type for this tag.
         */
        val TYPE: TagType<ByteArrayTag> = ByteArrayTagImpl.createType()
    }
}