package xyz.axie.nbt

internal object FloorMath {

    @JvmStatic
    fun floor(value: Float): Int {
        val intValue = value.toInt()
        return if (value < intValue) intValue - 1 else intValue
    }

    @JvmStatic
    fun floor(value: Double): Int {
        val intValue = value.toInt()
        return if (value < intValue) intValue - 1 else intValue
    }

}