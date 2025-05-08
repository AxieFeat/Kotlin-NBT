package xyz.axie.nbt.visitor

import xyz.axie.nbt.*

/**
 * A visitor that can be used to visit tags.
 */
interface TagVisitor {

    /**
     * Visits the end tag.
     *
     * @param tag The tag to visit.
     */
    fun visitEnd(tag: EndTag)

    /**
     * Visits the given byte tag.
     *
     * @param tag The tag to visit.
     */
    fun visitByte(tag: ByteTag)

    /**
     * Visits the given short tag.
     *
     * @param tag The tag to visit.
     */
    fun visitShort(tag: ShortTag)

    /**
     * Visits the given integer tag.
     *
     * @param tag The tag to visit.
     */
    fun visitInt(tag: IntTag)

    /**
     * Visits the given long tag.
     *
     * @param tag The tag to visit.
     */
    fun visitLong(tag: LongTag)

    /**
     * Visits the given float tag.
     *
     * @param tag The tag to visit.
     */
    fun visitFloat(tag: FloatTag)

    /**
     * Visits the given double tag.
     *
     * @param tag The tag to visit.
     */
    fun visitDouble(tag: DoubleTag)

    /**
     * Visits the given string tag.
     *
     * @param tag The tag to visit.
     */
    fun visitString(tag: StringTag)

    /**
     * Visits the given byte array tag.
     *
     * @param tag The tag to visit.
     */
    fun visitByteArray(tag: ByteArrayTag)

    /**
     * Visits the given integer array tag.
     *
     * @param tag The tag to visit.
     */
    fun visitIntArray(tag: IntArrayTag)

    /**
     * Visits the given long array tag.
     *
     * @param tag The tag to visit.
     */
    fun visitLongArray(tag: LongArrayTag)

    /**
     * Visits the given list tag.
     *
     * @param tag The tag to visit.
     */
    fun visitList(tag: ListTag)

    /**
     * Visits the given compound tag.
     *
     * @param tag The tag to visit.
     */
    fun visitCompound(tag: CompoundTag)

}