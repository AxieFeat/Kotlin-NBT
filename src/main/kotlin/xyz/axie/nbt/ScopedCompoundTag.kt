package xyz.axie.nbt

import org.pcollections.TreePVector
import java.util.function.Consumer

interface ScopedCompoundTag<T : ScopedCompoundTag<T>> : CompoundTag {

    override fun remove(name: String): T

    override fun put(name: String, value: Tag): T

    override fun putBoolean(name: String, value: Boolean): T

    override fun putByte(name: String, value: Byte): T

    override fun putShort(name: String, value: Short): T

    override fun putInt(name: String, value: Int): T

    override fun putLong(name: String, value: Long): T

    override fun putFloat(name: String, value: Float): T

    override fun putDouble(name: String, value: Double): T

    override fun putString(name: String, value: String): T

    override fun putByteArray(name: String, value: ByteArray): T

    override fun putBytes(name: String, vararg values: Byte): T

    override fun putIntArray(name: String, value: IntArray): T

    override fun putInts(name: String, vararg values: Int): T

    override fun putLongArray(name: String, value: LongArray): T

    override fun putLongs(name: String, vararg values: Long): T

    override fun copy(): T

    interface Builder<B : Builder<B>> : CompoundTag.Builder {
        override fun put(name: String, value: Tag): B

        override fun putBoolean(name: String, value: Boolean): B {
            return this.put(name, ByteTag.of(value))
        }

        override fun putByte(name: String, value: Byte): B {
            return this.put(name, ByteTag.of(value))
        }

        override fun putShort(name: String, value: Short): B {
            return this.put(name, ShortTag.of(value))
        }

        override fun putInt(name: String, value: Int): B {
            return this.put(name, IntTag.of(value))
        }

        override fun putLong(name: String, value: Long): B {
            return this.put(name, LongTag.of(value))
        }

        override fun putFloat(name: String, value: Float): B {
            return this.put(name, FloatTag.of(value))
        }

        override fun putDouble(name: String, value: Double): B {
            return this.put(name, DoubleTag.of(value))
        }

        override fun putString(name: String, value: String): B {
            return this.put(name, StringTag.of(value))
        }

        override fun putByteArray(name: String, value: ByteArray): B {
            return this.put(name, ByteArrayTag.of(value))
        }

        override fun putBytes(name: String, vararg values: Byte): B {
            return this.put(name, ByteArrayTag.of(values))
        }

        override fun putIntArray(name: String, value: IntArray): B {
            return this.put(name, IntArrayTag.of(value))
        }

        override fun putInts(name: String, vararg values: Int): B {
            return this.put(name, IntArrayTag.of(values))
        }

        override fun putLongArray(name: String, value: LongArray): B {
            return this.put(name, LongArrayTag.of(value))
        }

        override fun putLongs(name: String, vararg values: Long): B {
            return this.put(name, LongArrayTag.of(values))
        }

        override fun putList(name: String, builder: Consumer<ListTag.Builder>): B {
            val list: ImmutableListTag.Builder = ImmutableListTag.builder()
            builder.accept(list)
            return this.put(name, list.build())
        }

        override fun putList(name: String, elementType: Int, vararg elements: Tag): B {
            return this.putList(name, elementType, elements.toList())
        }

        override fun putList(name: String, elementType: Int, elements: Collection<Tag>): B {
            return this.put(name, ImmutableListTag.of(TreePVector.from(elements), elementType))
        }

        override fun putCompound(name: String, builder: Consumer<CompoundTag.Builder>): B {
            val compound = ImmutableCompoundTag.builder()
            builder.accept(compound)
            return this.put(name, compound.build())
        }

        override fun remove(key: String): B

        override fun from(other: CompoundTag.Builder): B

        override fun from(other: CompoundTag): B
    }
}