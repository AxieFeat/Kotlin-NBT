package xyz.axie.nbt

import java.util.function.Predicate

interface ScopedListTag<T : ScopedListTag<T>> : ListTag {

    override fun add(tag: Tag): T

    override fun addAll(tags: Collection<Tag?>): T

    override fun remove(index: Int): T

    override fun remove(tag: Tag): T

    override fun removeAll(tags: Collection<Tag?>): T

    override fun removeIf(predicate: Predicate<in Tag?>): T

    override fun set(index: Int, tag: Tag): T

    override fun setBoolean(index: Int, value: Boolean): T

    override fun setByte(index: Int, value: Byte): T

    override fun setShort(index: Int, value: Short): T

    override fun setInt(index: Int, value: Int): T

    override fun setLong(index: Int, value: Long): T

    override fun setFloat(index: Int, value: Float): T

    override fun setDouble(index: Int, value: Double): T

    override fun setString(index: Int, value: String): T

    override fun setByteArray(index: Int, value: ByteArray): T

    override fun setBytes(index: Int, vararg values: Byte): T

    override fun setIntArray(index: Int, value: IntArray): T

    override fun setInts(index: Int, vararg values: Int): T

    override fun setLongArray(index: Int, value: LongArray): T

    override fun setLongs(index: Int, vararg values: Long): T

    override fun copy(): T

    interface Builder<B : Builder<B>> : ListTag.Builder {
        override fun add(tag: Tag): B

        override fun addBoolean(value: Boolean): B {
            return add(ByteTag.of(value))
        }

        override fun addByte(value: Byte): B {
            return add(ByteTag.of(value))
        }

        override fun addShort(value: Short): B {
            return add(ShortTag.of(value))
        }

        override fun addInt(value: Int): B {
            return add(IntTag.of(value))
        }

        override fun addLong(value: Long): B {
            return add(LongTag.of(value))
        }

        override fun addFloat(value: Float): B {
            return add(FloatTag.of(value))
        }

        override fun addDouble(value: Double): B {
            return add(DoubleTag.of(value))
        }

        override fun addString(value: String): B {
            return add(StringTag.of(value))
        }

        override fun addByteArray(value: ByteArray): B {
            return add(ByteArrayTag.of(value))
        }

        override fun addBytes(vararg values: Byte): B {
            return add(ByteArrayTag.of(values))
        }

        override fun addIntArray(value: IntArray): B {
            return add(IntArrayTag.of(value))
        }

        override fun addInts(vararg values: Int): B {
            return add(IntArrayTag.of(values))
        }

        override fun addLongArray(value: LongArray): B {
            return add(LongArrayTag.of(value))
        }

        override fun addLongs(vararg values: Long): B {
            return add(LongArrayTag.of(values))
        }

        override fun remove(element: Tag): B

        override fun from(other: ListTag.Builder): B

        override fun from(other: ListTag): B
    }
}