package xyz.arial.nbt

import xyz.arial.nbt.impl.LongArrayTagImpl
import java.util.function.LongConsumer

/**
 * A tag that holds a long array.
 */
interface LongArrayTag : ListCollectionTag<LongTag>, ScopedTag<LongArrayTag> {
    /**
     * Gets the backing data for this tag.
     *
     * @return the backing data
     */
    var data: LongArray

    override fun get(index: Int): LongTag

    /**
     * Sets the element at the given index to the given value.
     *
     * @param index the index
     * @param value the value
     */
    fun set(index: Int, value: Long)

    override fun set(index: Int, element: LongTag): LongTag

    /**
     * Adds the given value to this tag.
     *
     * @param value the value to add
     */
    fun add(value: Long)

    /**
     * Adds the given value to this tag at the given index.
     *
     * @param index the index
     * @param value the value to add
     */
    fun add(index: Int, value: Long)

    override fun add(index: Int, element: LongTag)

    fun remove(index: Int): LongTag

    override fun clear()

    /**
     * Performs the given action on every element in this tag.
     *
     * @param action the action to perform
     */
    fun forEachLong(action: LongConsumer)

    companion object {
        /**
         * Creates a new long array tag with the given data.
         *
         * @param data the data
         * @return a new long array tag
         */
        fun of(data: LongArray): LongArrayTag {
            return LongArrayTagImpl(data)
        }

        /**
         * The ID of this type of tag.
         *
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 12

        /**
         * The tag type for this tag.
         */
        val TYPE: TagType<LongArrayTag> = LongArrayTagImpl.createType()
    }
}