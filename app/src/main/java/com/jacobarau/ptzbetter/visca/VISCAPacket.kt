package com.jacobarau.ptzbetter.visca

data class VISCAPacket(
    val senderAddress: Int,
    val receiverAddress: Int,
    val data: List<Int>
) {
    /**
     * Converts this VISCAPacket to its representation to send to the camera. Each element in the
     * returned List will be in the range 0..0xff and represents a byte to be sent.
     *
     * I would've made this return Array<Byte> but the language is fighting me every step of the way.
     * It really doesn't feel like anyone has tried bit-smashing in Kotlin before. :/
     */
    fun encode(): List<Int> {
        // MSB always set
        var headerByte = 0x80
        if (senderAddress !in 0..7 || receiverAddress !in 0..7) {
            throw IllegalStateException("Sender and receiver addresses must be between 0 and 7 inclusive")
        }
        headerByte += (senderAddress shl 4)
        headerByte += receiverAddress
        val result = arrayOf(headerByte).toMutableList()
        result.addAll(data)
        result.add(0xFF)
        return result
    }
}
