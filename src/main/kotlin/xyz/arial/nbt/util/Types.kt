package xyz.arial.nbt.util

import xyz.arial.nbt.*

/**
 * A utility that provides a mapping between the integer tag types (IDs) and
 * the tag type objects ([TagType]).
 */
object Types {

    private val TYPES: Array<TagType<out Tag>> = arrayOf<TagType<*>>(
        EndTag.TYPE,
        ByteTag.TYPE,
        ShortTag.TYPE,
        IntTag.TYPE,
        LongTag.TYPE,
        FloatTag.TYPE,
        DoubleTag.TYPE,
        ByteArrayTag.TYPE,
        StringTag.TYPE,
        ListTag.TYPE,
        CompoundTag.TYPE,
        IntArrayTag.TYPE,
        LongArrayTag.TYPE
    )

    /**
     * Gets the [TagType] for the given integer type ID.
     *
     * @param type the type ID
     * @return the corresponding tag type
     * @throws IllegalArgumentException if the provided type ID is not valid
     */
    fun of(type: Int): TagType<out Tag> {
        return if (type >= 0 && type < TYPES.size) TYPES[type] else TagType.createInvalid(type)
    }
}