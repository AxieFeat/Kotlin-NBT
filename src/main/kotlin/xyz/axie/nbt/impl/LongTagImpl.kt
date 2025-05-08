package xyz.axie.nbt.impl

import xyz.axie.nbt.LongTag
import xyz.axie.nbt.TagType
import xyz.axie.nbt.visitor.StreamingTagVisitor
import xyz.axie.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException

internal data class LongTagImpl(
    override val value: Long
) : LongTag {

    override val id: Int = LongTag.ID

    override fun type(): TagType<LongTag> = LongTag.TYPE

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        output.writeLong(value)
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitLong(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        return visitor.visit(value)
    }

    override fun copy(): LongTag {
        return this
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
        return value
    }

    override fun toInt(): Int {
        return value.toInt()
    }

    override fun toShort(): Short {
        return (value and 0xFFFFL).toShort()
    }

    override fun toByte(): Byte {
        return (value and 0xFFL).toByte()
    }

    private object Cache {
        const val LOWER_LIMIT: Int = -128
        const val UPPER_LIMIT: Int = 1024
        val CACHE: Array<LongTag?> = arrayOfNulls(UPPER_LIMIT - LOWER_LIMIT + 1)

        init {
            for (i in CACHE.indices) {
                CACHE[i] = LongTagImpl((i + LOWER_LIMIT).toLong())
            }
        }
    }

    companion object {
        fun of(value: Long): LongTag {
            if (value >= Cache.LOWER_LIMIT && value <= Cache.UPPER_LIMIT) return Cache.CACHE[value.toInt() - Cache.LOWER_LIMIT]!!
            return LongTagImpl(value)
        }

        fun createType(): TagType<LongTag> {
            return object : TagType.StaticSize<LongTag> {

                override val name: String = "LONG"

                override val prettyName: String = "TAG_Long"

                override val isValue: Boolean
                    get() = true

                override val size: Int
                    get() = 8

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): LongTag {
                    return of(input.readLong())
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    return visitor.visit(input.readLong())
                }
            }
        }
    }
}