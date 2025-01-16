package xyz.arial.nbt.io

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
 * @param decompressor the decompressor function
 * @param compressor the compressor function
 */
@JvmRecord
data class TagCompression(
    val decompressor: CompressorFunction<InputStream>,
    val compressor: CompressorFunction<OutputStream>
) {
    /**
     * Decompresses the given input stream using the decompressor function.
     *
     * @param input the input
     * @return the decompressed input
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun decompress(input: InputStream): InputStream {
        return decompressor.apply(input)
    }

    /**
     * Compresses the given output stream using the compressor function.
     *
     * @param output the output
     * @return the compressed output
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun compress(output: OutputStream): OutputStream {
        return compressor.apply(output)
    }

    /**
     * A function that takes a closeable output and returns the compressed or
     * decompressed variant.
     *
     * @param <T> the type of the value
    </T> */
    fun interface CompressorFunction<T : Closeable?> {
        /**
         * Performs this function on the given value.
         *
         * @param value the value
         * @return the result
         * @throws IOException if an I/O error occurs
         */
        @Throws(IOException::class)
        fun apply(value: T): T
    }

    companion object {
        /**
         * The compression type for uncompressed NBT data.
         */
        val NONE: TagCompression = TagCompression(
            { input: InputStream -> input },
            { output: OutputStream -> output })

        /**
         * The compression type for GZIP compressed data.
         */
        val GZIP: TagCompression = TagCompression(
            { `in` -> GZIPInputStream(`in`) },
            { out -> GZIPOutputStream(out) })

        /**
         * The compression type for ZLIB compressed data.
         */
        val ZLIB: TagCompression = TagCompression(
            { `in` -> InflaterInputStream(`in`) },
            { out -> DeflaterOutputStream(out) })
    }
}