package xyz.arial.nbt

import java.util.stream.Stream

/**
 * A tag that holds other types of tags.
 *
 * @param T the type of tag held by this collection tag
 */
interface CollectionTag<T : Tag> : Iterable<T>, Tag {

    /**
     * Gets the type of the tags that are stored by this collection tag.
     *
     * @return the type of the stored elements
     */
    val elementType: Int

    /**
     * Gets the number of elements in this collection tag.
     *
     * @return the size of this collection tag
     */
    val size: Int

    /**
     * Checks if this collection tag contains no elements.
     *
     * @return true if this collection tag contains no elements, false
     * otherwise
     */
    val empty: Boolean

    /**
     * Attempts to add the given tag to this collection at the given index.
     *
     * @param index the index to add the tag at
     * @param tag the tag to add
     * @return whether the tag was added
     * @apiNote This is an optional method that is not supported by all
     * collection tags. Specifically, all immutable collections will always
     * return false.
     */
    fun tryAdd(index: Int, tag: T): Boolean

    /**
     * Attempts to add the given tag to this collection.
     *
     * @param tag the tag to add
     * @return whether the tag was added
     * @apiNote This is an optional method that is not supported by all
     * collection tags. Specifically, all immutable collections will always
     * return false.
     */
    fun tryAdd(tag: T): Boolean

    override fun iterator(): Iterator<T>

    /**
     * Creates a new stream of the elements in this collection.
     *
     * @return a stream
     */
    fun stream(): Stream<T>
}