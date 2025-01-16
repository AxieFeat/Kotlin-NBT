package xyz.arial.nbt

import xyz.arial.nbt.CompoundTag.Companion.ID
import xyz.arial.nbt.CompoundTag.Companion.TYPE
import xyz.arial.nbt.impl.ImmutableCompoundTagImpl
import xyz.arial.nbt.impl.StringTagImpl
import xyz.arial.nbt.util.ObjByteConsumer
import xyz.arial.nbt.util.ObjFloatConsumer
import xyz.arial.nbt.util.ObjShortConsumer
import xyz.arial.nbt.util.Types
import xyz.arial.nbt.visitor.StreamingTagVisitor
import xyz.arial.nbt.visitor.TagVisitor
import org.pcollections.HashTreePMap
import xyz.arial.nbt.*
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException
import java.util.function.*
import java.util.function.Function

@Suppress("UNCHECKED_CAST")
internal abstract class AbstractCompoundTag<T : CompoundTag> : CompoundTag {

    override val id: Int = ID

    override val type: TagType<CompoundTag> = TYPE

    override val size: Int
        get() = data.size

    override val isEmpty: Boolean
        get() = data.isEmpty()

    override fun keySet(): Set<String> {
        return data.keys
    }

    override fun values(): Collection<Tag> {
        return data.values
    }

    override fun type(name: String): Int {
        val tag = data[name]

        return tag?.id ?: EndTag.ID
    }

    override fun contains(name: String): Boolean {
        return data.containsKey(name)
    }

    override fun contains(name: String, typeId: Int): Boolean {
        val type = type(name)
        if (type == typeId) return true
        if (typeId != 99) return false

        return type == ByteTag.ID || type == ShortTag.ID || type == IntTag.ID || type == LongTag.ID || type == FloatTag.ID || type == DoubleTag.ID
    }

    override fun get(name: String): Tag? {
        return data[name]
    }

    override fun getBoolean(name: String, defaultValue: Boolean): Boolean {
        val tag: NumberTag? = if (contains(name, 99)) data[name] as NumberTag else null

        return if (tag == null) defaultValue else tag.toByte().toInt() != 0
    }

    override fun getByte(name: String, defaultValue: Byte): Byte {
        val tag: NumberTag? = if (contains(name, 99)) data[name] as NumberTag else null
        return tag?.toByte() ?: defaultValue
    }

    override fun getShort(name: String, defaultValue: Short): Short {
        val tag: NumberTag? = if (contains(name, 99)) data[name] as NumberTag else null
        return tag?.toShort() ?: defaultValue
    }

    override fun getInt(name: String, defaultValue: Int): Int {
        val tag: NumberTag? = if (contains(name, 99)) data[name] as NumberTag else null
        return tag?.toInt() ?: defaultValue
    }

    override fun getLong(name: String, defaultValue: Long): Long {
        val tag: NumberTag? = if (contains(name, 99)) data[name] as NumberTag else null
        return tag?.toLong() ?: defaultValue
    }

    override fun getFloat(name: String, defaultValue: Float): Float {
        val tag: NumberTag? = if (contains(name, 99)) data[name] as NumberTag else null
        return tag?.toFloat() ?: defaultValue
    }

    override fun getDouble(name: String, defaultValue: Double): Double {
        val tag: NumberTag? = if (contains(name, 99)) data[name] as NumberTag else null
        return tag?.toDouble() ?: defaultValue
    }

    override fun getString(name: String, defaultValue: String): String {
        return if (contains(name, StringTag.ID)) (data[name] as StringTag).value else defaultValue
    }

    override fun getByteArray(name: String, defaultValue: ByteArray): ByteArray {
        return if (contains(name, ByteArrayTag.ID)) (data[name] as ByteArrayTag).data else defaultValue
    }

    override fun getIntArray(name: String, defaultValue: IntArray): IntArray {
        return if (contains(name, IntArrayTag.ID)) (data[name] as IntArrayTag).data else defaultValue
    }

    override fun getLongArray(name: String, defaultValue: LongArray): LongArray {
        return if (contains(name, LongArrayTag.ID)) (data[name] as LongArrayTag).data else defaultValue
    }

    override fun getList(name: String, elementType: Int, defaultValue: ListTag): ListTag {
        if (type(name) != ListTag.ID) return defaultValue
        val tag: ListTag = data[name] as ListTag
        return if (!tag.isEmpty && tag.elementType() != elementType) defaultValue else tag
    }

    override fun getCompound(name: String, defaultValue: CompoundTag): CompoundTag {
        return if (contains(name, ID)) data[name] as CompoundTag else defaultValue
    }

    abstract override fun put(name: String, value: Tag): T

    override fun putBoolean(name: String, value: Boolean): T {
        return put(name, ByteTag.of(value))
    }

    override fun putByte(name: String, value: Byte): T {
        return put(name, ByteTag.of(value))
    }

    override fun putShort(name: String, value: Short): T {
        return put(name, ShortTag.of(value))
    }

    override fun putInt(name: String, value: Int): T {
        return put(name, IntTag.of(value))
    }

    override fun putLong(name: String, value: Long): T {
        return put(name, LongTag.of(value))
    }

    override fun putFloat(name: String, value: Float): T {
        return put(name, FloatTag.of(value))
    }

    override fun putDouble(name: String, value: Double): T {
        return put(name, DoubleTag.of(value))
    }

    override fun putString(name: String, value: String): T {
        return put(name, StringTag.of(value))
    }

    override fun putByteArray(name: String, value: ByteArray): T {
        return put(name, ByteArrayTag.of(value))
    }

    override fun putBytes(name: String, vararg values: Byte): T {
        return put(name, ByteArrayTag.of(values))
    }

    override fun putIntArray(name: String, value: IntArray): T {
        return put(name, IntArrayTag.of(value))
    }

    override fun putInts(name: String, vararg values: Int): T {
        return put(name, IntArrayTag.of(values))
    }

    override fun putLongArray(name: String, value: LongArray): T {
        return put(name, LongArrayTag.of(value))
    }

    override fun putLongs(name: String, vararg values: Long): T {
        return put(name, LongArrayTag.of(values))
    }

    override fun update(name: String, action: Function<CompoundTag, CompoundTag>): T {
        return put(name, action.apply(getCompound(name)))
    }

    override fun update(name: String, type: Int, action: Function<ListTag, ListTag>): T {
        return put(name, action.apply(getList(name, type)))
    }

    override fun forEachByte(action: ObjByteConsumer<String>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is ByteTag) action.accept(entry.key, value.value)
        }
    }

    override fun forEachShort(action: ObjShortConsumer<String>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is ShortTag) action.accept(entry.key, value.value)
        }
    }

    override fun forEachInt(action: ObjIntConsumer<String>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is IntTag) action.accept(entry.key, value.value)
        }
    }

    override fun forEachLong(action: ObjLongConsumer<String>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is LongTag) action.accept(entry.key, value.value)
        }
    }

    override fun forEachFloat(action: ObjFloatConsumer<String>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is FloatTag) action.accept(entry.key, value.value)
        }
    }

    override fun forEachDouble(action: ObjDoubleConsumer<String>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is DoubleTag) action.accept(entry.key, value.value)
        }
    }

    override fun forEachString(action: BiConsumer<String, String>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is StringTag) action.accept(entry.key, value.value)
        }
    }

    override fun forEachByteArray(action: BiConsumer<String, ByteArray>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is ByteArrayTag) action.accept(entry.key, value.data)
        }
    }

    override fun forEachIntArray(action: BiConsumer<String, IntArray>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is IntArrayTag) action.accept(entry.key, value.data)
        }
    }

    override fun forEachLongArray(action: BiConsumer<String, LongArray>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is LongArrayTag) action.accept(entry.key, value.data)
        }
    }

    override fun forEachList(action: BiConsumer<String, ListTag>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is ListTag) action.accept(entry.key, value)
        }
    }

    override fun forEachCompound(action: BiConsumer<String, CompoundTag>) {
        for (entry in data.entries) {
            val value = entry.value

            if (value is CompoundTag) action.accept(entry.key, value)
        }
    }

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        for (entry in data.entries) {
            output.writeByte(entry.value.id)
            if (entry.value.id == EndTag.ID) continue
            output.writeUTF(entry.key)
            entry.value.write(output)
        }
        output.writeByte(EndTag.ID)
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitCompound(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        for (entry in data.entries) {
            val tag: Tag = entry.value
            val entryType: TagType<*> = tag.type
            when (visitor.visitEntry(entryType)) {
                StreamingTagVisitor.EntryResult.HALT -> return StreamingTagVisitor.ValueResult.HALT
                StreamingTagVisitor.EntryResult.BREAK -> return visitor.visitContainerEnd()
                StreamingTagVisitor.EntryResult.SKIP -> {}
                else -> when (visitor.visitEntry(entryType, entry.key)) {
                    StreamingTagVisitor.EntryResult.HALT -> return StreamingTagVisitor.ValueResult.HALT
                    StreamingTagVisitor.EntryResult.BREAK -> return visitor.visitContainerEnd()
                    StreamingTagVisitor.EntryResult.SKIP -> {}
                    else -> return when (tag.visit(visitor)) {
                        StreamingTagVisitor.ValueResult.HALT -> {
                            StreamingTagVisitor.ValueResult.HALT
                        }

                        StreamingTagVisitor.ValueResult.BREAK -> {
                            visitor.visitContainerEnd()
                        }

                        StreamingTagVisitor.ValueResult.CONTINUE -> continue
                    }
                }
            }
        }
        return visitor.visitContainerEnd()
    }

    override fun equals(other: Any?): Boolean {
        return this === other || (other is AbstractCompoundTag<*> && data == other.data)
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }

    internal abstract class Builder<B : Builder<B, T>, T : CompoundTag> protected constructor(
        protected val data: MutableMap<String, Tag>
    ) : CompoundTag.Builder {

        override fun put(name: String, value: Tag): B {
            data[name] = value
            return this as B
        }

        override fun remove(key: String): B {
            data.remove(key)
            return this as B
        }

        override fun from(other: CompoundTag.Builder): B {
            data.putAll((other as Builder<*, *>).data)
            return this as B
        }

        override fun from(other: CompoundTag): B {
            data.putAll(other.data)
            return this as B
        }

        abstract override fun build(): T
    }

    companion object {
        fun createType(): TagType<CompoundTag> {
            return object : TagType.VariableSize<CompoundTag> {
                override val name: String = "COMPOUND"

                override val prettyName: String = "TAG_Compound"

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): CompoundTag {
                    if (depth > 512) throw RuntimeException("Depth too high! Given depth $depth is higher than maximum depth 512!")
                    val result: MutableMap<String, Tag> = HashMap()
                    var type = input.readByte().toInt()
                    while (type != EndTag.ID) {
                        val name = input.readUTF()
                        val tag: Tag = Types.of(type).load(input, depth + 1)
                        result[name] = tag
                        type = input.readByte().toInt()
                    }
                    return ImmutableCompoundTagImpl(HashTreePMap.from(result))
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    while (true) {
                        val id = input.readByte()
                        if (id.toInt() == 0) return visitor.visitContainerEnd()

                        val type: TagType<*> = Types.of(id.toInt())
                        when (visitor.visitEntry(type)) {
                            StreamingTagVisitor.EntryResult.HALT -> {
                                return StreamingTagVisitor.ValueResult.HALT
                            }

                            StreamingTagVisitor.EntryResult.BREAK -> {
                                StringTagImpl.skipString(input)
                                type.skip(input)
                                skipAllNamesAndTypes(input)
                                return visitor.visitContainerEnd()
                            }

                            StreamingTagVisitor.EntryResult.SKIP -> {
                                StringTagImpl.skipString(input)
                                type.skip(input)
                            }

                            else -> {
                                val name = input.readUTF()
                                when (visitor.visitEntry(type, name)) {
                                    StreamingTagVisitor.EntryResult.HALT -> {
                                        return StreamingTagVisitor.ValueResult.HALT
                                    }

                                    StreamingTagVisitor.EntryResult.BREAK -> {
                                        type.skip(input)
                                        skipAllNamesAndTypes(input)
                                        return visitor.visitContainerEnd()
                                    }

                                    StreamingTagVisitor.EntryResult.SKIP -> type.skip(input)

                                    else -> {
                                        if (type.parse(input, visitor) === StreamingTagVisitor.ValueResult.HALT) {
                                            return StreamingTagVisitor.ValueResult.HALT
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                @Throws(IOException::class)
                private fun skipAllNamesAndTypes(input: DataInput) {
                    var id = input.readByte()
                    while (id.toInt() != EndTag.ID) {
                        StringTagImpl.skipString(input)
                        Types.of(id.toInt()).skip(input)
                        id = input.readByte()
                    }
                }

                @Throws(IOException::class)
                override fun skip(input: DataInput) {
                    skipAllNamesAndTypes(input)
                }
            }
        }
    }
}