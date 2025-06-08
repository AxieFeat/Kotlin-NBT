package xyz.axie.nbt.util

import xyz.axie.nbt.*

/**
 * A utility that provides a mapping between the integer tag types (IDs) and
 * the tag type objects ([TagType]).
 */
object Types {

    private val TYPES: Array<TagType<out Tag>> = arrayOf(
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
     * @param type The type ID.
     *
     * @return The corresponding tag type.
     *
     * @throws IllegalArgumentException If the provided type ID is not valid.
     */
    @JvmStatic
    fun of(type: Int): TagType<out Tag> {
        return if (type >= 0 && type < TYPES.size) TYPES[type] else TagType.createInvalid(type)
    }

}