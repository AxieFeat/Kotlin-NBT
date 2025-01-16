package xyz.arial.nbt.impl

import xyz.arial.nbt.ByteTag
import xyz.arial.nbt.TagType
import xyz.arial.nbt.visitor.StreamingTagVisitor
import xyz.arial.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException

internal data class ByteTagImpl(
    override val value: Byte
) : ByteTag {

    override val id: Int = ByteTag.ID

    override val type: TagType<ByteTag> = ByteTag.TYPE

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        output.writeByte(value.toInt())
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitByte(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        return visitor.visit(value)
    }

    override fun copy(): ByteTag {
        return this
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun asNumber(): Number {
        return value
    }

    override fun toDouble(): Double {
        return value.toDouble()
    }

    override fun toFloat(): Float {
        return value.toFloat()
    }

    override fun toLong(): Long {
        return value.toLong()
    }

    override fun toInt(): Int {
        return value.toInt()
    }

    override fun toShort(): Short {
        return value.toShort()
    }

    override fun toByte(): Byte {
        return value
    }

    override fun hashCode(): Int {
        var result = value
        result = (31 * result + id).toByte()
        result = (31 * result + type.hashCode()).toByte()
        return result.toInt()
    }

    private object Cache {
        const val LOWER_LIMIT: Int = -128
        val CACHE: Array<ByteTag?> = arrayOfNulls(256)

        init {
            for (i in CACHE.indices) {
                CACHE[i] = ByteTagImpl((i + LOWER_LIMIT).toByte())
            }
        }
    }

    companion object {

        fun of(value: Byte): ByteTag {
            return Cache.CACHE[value - Cache.LOWER_LIMIT]!!
        }

        fun createType(): TagType<ByteTag> {
            return object : TagType.StaticSize<ByteTag> {

                override val name: String = "BYTE"

                override val prettyName: String = "TAG_Byte"

                override val isValue: Boolean
                    get() = true

                override val size: Int = 1

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): ByteTag {
                    return of(input.readByte())
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    return visitor.visit(input.readByte())
                }
            }
        }
    }
}