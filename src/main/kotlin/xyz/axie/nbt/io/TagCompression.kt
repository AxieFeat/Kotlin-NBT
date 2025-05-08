package xyz.axie.nbt.io

import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.DeflaterOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import java.util.zip.InflaterInputStream

/**
 * A compression type for NBT data.
 *
 * @param decompressor The decompressor function.
 * @param compressor The compressor function.
 */
data class TagCompression(
    val decompressor: CompressorFunction<InputStream>,
    val compressor: CompressorFunction<OutputStream>
) {
    /**
     * Decompresses the given input stream using the decompressor function.
     *
     * @param input The input.
     *
     * @return The decompressed input.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Throws(IOException::class)
    fun decompress(input: InputStream): InputStream {
        return decompressor.apply(input)
    }

    /**
     * Compresses the given output stream using the compressor function.
     *
     * @param output The output.
     *
     * @return The compressed output.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Throws(IOException::class)
    fun compress(output: OutputStream): OutputStream {
        return compressor.apply(output)
    }

    /**
     * A function that takes a closeable output and returns the compressed or
     * decompressed variant.
     *
     * @param T The type of the value.
     */
    fun interface CompressorFunction<T : Closeable?> {
        /**
         * Performs this function on the given value.
         *
         * @param value The value.
         *
         * @return The result.
         *
         * @throws IOException If an I/O error occurs.
         */
        @Throws(IOException::class)
        fun apply(value: T): T
    }

    companion object {
        /**
         * The compression type for uncompressed NBT data.
         */
        @JvmField
        val NONE: TagCompression = TagCompression(
            { input: InputStream -> input },
            { output: OutputStream -> output })

        /**
         * The compression type for GZIP compressed data.
         */
        @JvmField
        val GZIP: TagCompression = TagCompression(
            { `in` -> GZIPInputStream(`in`) },
            { out -> GZIPOutputStream(out) })

        /**
         * The compression type for ZLIB compressed data.
         */
        @JvmField
        val ZLIB: TagCompression = TagCompression(
            { `in` -> InflaterInputStream(`in`) },
            { out -> DeflaterOutputStream(out) })
    }
}