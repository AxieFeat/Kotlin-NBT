package xyz.axie.nbt

import xyz.axie.nbt.impl.IntArrayTagImpl
import java.util.function.IntConsumer

/**
 * A tag that holds an int array.
 */
interface IntArrayTag : ListCollectionTag<IntTag>, ScopedTag<IntArrayTag> {

    /**
     * Gets the backing data for this tag.
     *
     * @return The backing data.
     */
    var data: IntArray

    override fun get(index: Int): IntTag

    /**
     * Sets the element at the given index to the given value.
     *
     * @param index The index.
     *
     * @param value The value.
     */
    fun set(index: Int, value: Int)

    override fun set(index: Int, element: IntTag): IntTag

    /**
     * Adds the given value to this tag.
     *
     * @param value The value to add.
     */
    fun add(value: Int)

    /**
     * Adds the given value to this tag at the given index.
     *
     * @param index The index.
     * @param value The value to add.
     */
    fun add(index: Int, value: Int)

    override fun add(index: Int, element: IntTag)

    override fun clear()

    /**
     * Performs the given action on every element in this tag.
     *
     * @param action The action to perform.
     */
    fun forEachInt(action: IntConsumer)

    companion object {

        /**
         * Creates a new int array tag with the given data.
         *
         * @param data The data.
         *
         * @return A new int array tag.
         */
        @JvmStatic
        fun of(data: IntArray): IntArrayTag {
            return IntArrayTagImpl(data)
        }

        /**
         * The ID of this type of tag.
         *
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 11

        /**
         * The tag type for this tag.
         */
        @JvmStatic
        val TYPE: TagType<IntArrayTag> = IntArrayTagImpl.createType()

    }

}