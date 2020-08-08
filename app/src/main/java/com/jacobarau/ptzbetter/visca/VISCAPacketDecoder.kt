package com.jacobarau.ptzbetter.visca

import android.util.Log

class VISCAPacketDecoder {
    data class State(var header: Int? = null, var data: MutableList<Int> = mutableListOf())

    var state = State()

    companion object {
        data class DecodeResult(val packets: List<VISCAPacket>, val decoderState: State)

        fun decode(data: List<Int>, incomingDecoderState: State): DecodeResult {
            val packets: MutableList<VISCAPacket> = mutableListOf()
            var state = incomingDecoderState
            for (b in data) {
                if (b == 0xFF) {
                    // End of packet received. If our state contains a valid-looking message, return it.
                    if (state.header == null || state.data.count() == 0) {
                        // Illegal state. Print diagnostic message and drop it.
                        Log.w("VISCAPacketDecoder", "Malformed packet received--missing header or zero data bytes before terminator")
                    } else {
                        val sender = (state.header!! shr 4) and 7
                        val receiver = state.header!! and 7
                        packets.add(VISCAPacket(sender, receiver, state.data))
                        Log.i("VISCAPacketDecoder", "Rx " + packets.last())
                    }

                    state = State()
                    continue
                }
                if (state.header == null) {
                    state.header = b
                } else {
                    state.data.add(b)
                }
            }

            return DecodeResult(packets, state)
        }
    }
}