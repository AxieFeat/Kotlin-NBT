package xyz.arial.nbt.impl

import xyz.arial.nbt.StringTag
import xyz.arial.nbt.TagType
import xyz.arial.nbt.visitor.StreamingTagVisitor
import xyz.arial.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException

internal data class StringTagImpl(
    override val value: String
) : StringTag {

    override val id: Int = StringTag.ID

    override val type: TagType<StringTag> = StringTag.TYPE

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        output.writeUTF(value)
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitString(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        return visitor.visit(value)
    }

    override fun copy(): StringTag {
        return this
    }

    override fun asString(): String {
        return value
    }

    companion object {
        @Throws(IOException::class)
        fun skipString(input: DataInput) {
            input.skipBytes(input.readUnsignedShort())
        }

        fun createType(): TagType<StringTag> {
            return object : TagType.VariableSize<StringTag> {

                override val name: String = "STRING"

                override val prettyName: String = "TAG_String"

                override val isValue: Boolean
                    get() = true

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): StringTag {
                    return StringTag.of(input.readUTF())
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    return visitor.visit(input.readUTF())
                }

                @Throws(IOException::class)
                override fun skip(input: DataInput) {
                    skipString(input)
                }
            }
        }
    }
}