package xyz.axie.nbt.impl

import xyz.axie.nbt.FloatTag
import xyz.axie.nbt.FloorMath
import xyz.axie.nbt.TagType
import xyz.axie.nbt.visitor.StreamingTagVisitor
import xyz.axie.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException

internal data class FloatTagImpl(
    override val value: Float
) : FloatTag {

    override val id: Int = FloatTag.ID

    override fun type(): TagType<FloatTag> = FloatTag.TYPE

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        output.writeFloat(value)
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitFloat(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        return visitor.visit(value)
    }

    override fun copy(): FloatTag {
        return this
    }

    override fun asNumber(): Number {
        return value
    }

    override fun toDouble(): Double {
        return value.toDouble()
    }

    override fun toFloat(): Float {
        return value
    }

    override fun toLong(): Long {
        return value.toLong()
    }

    override fun toInt(): Int {
        return FloorMath.floor(value)
    }

    override fun toShort(): Short {
        return (FloorMath.floor(value) and 0xFFFF).toShort()
    }

    override fun toByte(): Byte {
        return (FloorMath.floor(value) and 0xFF).toByte()
    }

    companion object {
        fun createType(): TagType<FloatTag> {
            return object : TagType.StaticSize<FloatTag> {

                override val name: String = "FLOAT"

                override val prettyName: String = "TAG_Float"

                override val isValue: Boolean
                    get() = true

                override val size: Int = 4

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): FloatTag {
                    return FloatTag.of(input.readFloat())
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    return visitor.visit(input.readFloat())
                }
            }
        }
    }
}