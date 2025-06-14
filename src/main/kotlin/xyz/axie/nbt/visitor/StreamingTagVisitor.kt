package xyz.axie.nbt.visitor

import xyz.axie.nbt.TagType

/**
 * A tag visitor designed for reading serialized tag data.
 *
 * It's called streaming because it works on a data stream, usually some
 * form of serialized data.
 */
interface StreamingTagVisitor {

    /**
     * Visits an end tag.
     *
     * @return The result.
     */
    fun visitEnd(): ValueResult

    /**
     * Visits a string value.
     *
     * @param value The value.
     *
     * @return The result.
     */
    fun visit(value: String): ValueResult

    /**
     * Visits a byte value.
     *
     * @param value The value.
     *
     * @return The result.
     */
    fun visit(value: Byte): ValueResult

    /**
     * Visits a short value.
     *
     * @param value The value.
     *
     * @return The result.
     */
    fun visit(value: Short): ValueResult

    /**
     * Visits an int value.
     *
     * @param value The value.
     *
     * @return The result.
     */
    fun visit(value: Int): ValueResult

    /**
     * Visits a long value.
     *
     * @param value The value.
     *
     * @return The result.
     */
    fun visit(value: Long): ValueResult

    /**
     * Visits a float value.
     *
     * @param value The value.
     *
     * @return The result.
     */
    fun visit(value: Float): ValueResult

    /**
     * Visits a double value.
     *
     * @param value The value.
     *
     * @return The result.
     */
    fun visit(value: Double): ValueResult

    /**
     * Visits a byte array value.
     *
     * @param value The value.
     *
     * @return The result.
     */
    fun visit(value: ByteArray): ValueResult

    /**
     * Visits an int array value.
     *
     * @param value The value.
     *
     * @return The result.
     */
    fun visit(value: IntArray): ValueResult

    /**
     * Visits a long array value.
     *
     * @param value The value.
     *
     * @return The result.
     */
    fun visit(value: LongArray): ValueResult

    /**
     * Visits the start of a list with the given type and size.
     *
     * @param type The type of the list.
     * @param size The size of the list.
     *
     * @return The result.
     */
    fun visitList(type: TagType<*>, size: Int): ValueResult

    /**
     * Visits an element in a list with the given type at the given index.
     *
     * @param type The type of the list.
     * @param index The index of the element within the list.
     *
     * @return The result.
     */
    fun visitElement(type: TagType<*>, index: Int): EntryResult

    /**
     * Visits an entry in a container structure with the given type.
     *
     * @param type The type of the entry.
     *
     * @return The result.
     */
    fun visitEntry(type: TagType<*>): EntryResult

    /**
     * Visits an entry in a container structure with the given type and name.
     *
     * @param type The type of the entry.
     * @param name The name of the entry.
     *
     * @return The result.
     */
    fun visitEntry(type: TagType<*>, name: String): EntryResult

    /**
     * Visits the root entry with the given type.
     *
     * This type of entry is different from the other two above, in that
     * this refers to any type of tag at the root of a hierarchy found when
     * reading data.
     *
     * @param type The type of the entry.
     *
     * @return The result.
     */
    fun visitRootEntry(type: TagType<*>): ValueResult

    /**
     * Visits the end of a container.
     *
     * @return The result.
     */
    fun visitContainerEnd(): ValueResult

    /**
     * Indicates the result of a visit to an entry.
     */
    enum class EntryResult {
        /**
         * We should enter the entry in to an underlying data type.
         */
        ENTER,

        /**
         * We should skip the entry.
         */
        SKIP,

        /**
         * We should skip the entry and all other entries after it in its
         * hierarchy level.
         *
         *
         * For example, if we have a compound that contains a list, and
         * visiting one of the list elements returns this, we will skip all
         * the list elements, but not all the elements in the parent
         * compound.
         */
        BREAK,

        /**
         * We should halt the visiting process entirely, usually due to an
         * error.
         */
        HALT
    }

    /**
     * Indicates the result of a visit to a value.
     */
    enum class ValueResult {
        /**
         * We should continue reading as normal.
         */
        CONTINUE,

        /**
         * We should ignore the value and continue reading.
         */
        BREAK,

        /**
         * We should halt the visiting process entirely, usually due to an
         * error.
         */
        HALT
    }
}