package xyz.axie.nbt.io

import xyz.axie.nbt.CompoundTag
import xyz.axie.nbt.EndTag
import xyz.axie.nbt.Tag
import xyz.axie.nbt.util.Types
import java.io.*

internal object TagUtil {
    private val END_NAMED = NamedTag("", EndTag.INSTANCE)

    @Throws(IOException::class)
    @JvmStatic
    fun readUnnamedTag(inputStream: InputStream): Tag {
        val input = ensureDataInput(inputStream)
        val type = input.read()
        if (type == EndTag.ID) return EndTag.INSTANCE
        input.skipBytes(input.readUnsignedShort())
        return Types.of(type).load(input, 0)
    }

    @Throws(IOException::class)
    @JvmStatic
    fun readNamedTag(inputStream: InputStream): NamedTag {
        val input = ensureDataInput(inputStream)
        val type = input.read()
        if (type == EndTag.ID) return END_NAMED
        val name = input.readUTF()
        val tag = Types.of(type).load(input, 0)
        return NamedTag(name, tag)
    }

    @Throws(IOException::class)
    @JvmStatic
    fun writeNamedTag(outputStream: OutputStream, name: String, value: Tag) {
        val output = if (outputStream is DataOutputStream) outputStream else DataOutputStream(outputStream)
        output.writeByte(value.id)
        if (value.id == EndTag.ID) return
        output.writeUTF(name)
        value.write(output)
    }

    @Throws(IOException::class)
    @JvmStatic
    fun ensureCompound(tag: Tag): CompoundTag {
        if (tag is CompoundTag) return tag
        throw IOException("Root tag must be an unnamed compound!")
    }

    private fun ensureDataInput(input: InputStream): DataInputStream {
        return if (input is DataInputStream) input else DataInputStream(input)
    }
}