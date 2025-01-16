package xyz.arial.nbt.io

import xyz.arial.nbt.CompoundTag
import java.io.*
import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Path

/**
 * A utility used for reading and writing NBT data.
 */
object TagIO {

    /**
     * Reads a compound tag from the given input, using the given compression
     * to decompress the input before reading the data.
     *
     * @param input the input to read from
     * @param compression the compression to decompress the data with
     * @return the resulting compound tag
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun read(input: InputStream, compression: TagCompression): CompoundTag {
        compression.decompress(input).use { stream ->
            return TagUtil.ensureCompound(TagUtil.readUnnamedTag(stream))
        }
    }

    /**
     * Reads a compound tag from the given path, using the given compression
     * to decompress the input before reading the data, opening a new stream
     * with the given open options.
     *
     * @param path the path to read from
     * @param compression the compression to decompress the data with
     * @param options the options to open the stream with
     * @return the resulting compound tag
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun read(
        path: Path, compression: TagCompression,
        vararg options: OpenOption
    ): CompoundTag {
        return read(Files.newInputStream(path, *options), compression)
    }

    /**
     * Reads a compound tag from the given file, using the given compression
     * to decompress the input before reading the data.
     *
     * @param file the file to read from
     * @param compression the compression to decompress the data with
     * @return the resulting compound tag
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun read(file: File, compression: TagCompression): CompoundTag {
        return read(FileInputStream(file), compression)
    }

    /**
     * Reads a named tag from the given input, using the given compression to
     * decompress the input before reading the data.
     *
     * @param input the input to read from
     * @param compression the compression to decompress the data with
     * @return the resulting named tag
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun readNamed(input: InputStream, compression: TagCompression): NamedTag {
        compression.decompress(input).use { stream ->
            return TagUtil.readNamedTag(stream)
        }
    }

    /**
     * Reads a named tag from the given path, using the given compression to
     * decompress the input before reading the data, opening a new stream with
     * the given open options.
     *
     * @param path the path to read from
     * @param compression the compression to decompress the data with
     * @param options the options to open the stream with
     * @return the resulting named tag
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun readNamed(
        path: Path, compression: TagCompression,
        vararg options: OpenOption
    ): NamedTag {
        return readNamed(Files.newInputStream(path, *options), compression)
    }

    /**
     * Reads a named tag from the given file, using the given compression to
     * decompress the input before reading the data.
     *
     * @param file the file to read from
     * @param compression the compression to decompress the data with
     * @return the resulting named tag
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun readNamed(file: File, compression: TagCompression): NamedTag {
        return readNamed(FileInputStream(file), compression)
    }

    /**
     * Writes an unnamed compound tag to the given output, using the given
     * compression to compress the output before writing the data.
     *
     * @param output the output to write to
     * @param value the value to write
     * @param compression the compression to compress the data with
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun write(
        output: OutputStream, value: CompoundTag,
        compression: TagCompression
    ) {
        writeNamed(output, "", value, compression)
    }

    /**
     * Writes an unnamed compound tag to the given path, using the given
     * compression to compress the output before writing the data, opening a
     * new stream with the given open options.
     *
     * @param path the path to write to
     * @param value the value to write
     * @param options the options to open the stream with
     * @param compression the compression to compress the data with
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun write(
        path: Path, value: CompoundTag,
        compression: TagCompression, vararg options: OpenOption
    ) {
        write(Files.newOutputStream(path, *options), value, compression)
    }

    /**
     * Writes an unnamed compound tag to the given file, using the given
     * compression to compress the output before writing the data.
     *
     * @param file the file to write to
     * @param value the value to write
     * @param compression the compression to compress the data with
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun write(
        file: File, value: CompoundTag,
        compression: TagCompression
    ) {
        write(FileOutputStream(file), value, compression)
    }

    /**
     * Writes a named tag to the given output, using the given compression to
     * compress the output before writing the data.
     *
     * @param output the output to write to
     * @param name the name of the value to write
     * @param value the value to write
     * @param compression the compression to compress the data with
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun writeNamed(
        output: OutputStream, name: String, value: CompoundTag,
        compression: TagCompression
    ) {
        compression.compress(output).use { stream ->
            TagUtil.writeNamedTag(stream, name, value)
        }
    }

    /**
     * Writes a named tag to the given path, using the given compression to
     * compress the output before writing the data, opening a new stream with
     * the given open options.
     *
     * @param path the path to write to
     * @param name the name of the value to write
     * @param value the value to write
     * @param options the options to open the stream with
     * @param compression the compression to compress the data with
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun writeNamed(
        path: Path, name: String, value: CompoundTag,
        compression: TagCompression, vararg options: OpenOption
    ) {
        writeNamed(Files.newOutputStream(path, *options), name, value, compression)
    }

    /**
     * Writes a named tag to the given file, using the given compression to
     * compress the output before writing the data.
     *
     * @param file the file to write to
     * @param name the name of the value to write
     * @param value the value to write
     * @param compression the compression to compress the data with
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    @JvmStatic
    fun writeNamed(
        file: File, name: String, value: CompoundTag,
        compression: TagCompression
    ) {
        writeNamed(FileOutputStream(file), name, value, compression)
    }
}