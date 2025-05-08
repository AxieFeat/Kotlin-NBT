package xyz.axie.nbt

import java.util.stream.Stream

/**
 * A tag that holds other types of tags.
 *
 * @param T The type of tag held by this collection tag.
 */
interface CollectionTag<T : Tag> : Iterable<T>, Tag {

    /**
     * Gets the type of the tags that are stored by this collection tag.
     *
     * @return The type of the stored elements.
     */
    val elementType: Int

    /**
     * Gets the number of elements in this collection tag.
     *
     * @return The size of this collection tag.
     */
    val size: Int

    /**
     * Checks if this collection tag contains no elements.
     *
     * @return `true` if this collection tag contains no elements, otherwise `false`.
     */
    fun isEmpty(): Boolean

    /**
     * Attempts to add the given tag to this collection at the given index.
     *
     * Note: This is an optional method that is not supported by all
     * collection tags. Specifically, all immutable collections will always
     * return false.
     *
     * @param index The index to add the tag at.
     * @param tag The tag to add.
     *
     * @return Whether the tag was added.
     */
    fun tryAdd(index: Int, tag: T): Boolean

    /**
     * Attempts to add the given tag to this collection.
     *
     * Note: This is an optional method that is not supported by all
     * collection tags. Specifically, all immutable collections will always
     * return false.
     *
     * @param tag The tag to add.
     *
     * @return Whether the tag was added.
     */
    fun tryAdd(tag: T): Boolean

    override fun iterator(): Iterator<T>

    /**
     * Creates a new stream of the elements in this collection.
     *
     * @return A stream.
     */
    fun stream(): Stream<T>

}