#!/usr/bin/env python3

import socket

def read_or_die(stream, length):
    total = stream.recv(length)
    if len(total) < length:
        raise Exception("Incoming connection terminated")
    return total

class VISCAMessage:
    sender_address = 0
    receiver_address = 1
    message_data = b''

    def read_from_stream(self, stream):
        header1 = read_or_die(stream, 1)
        self.sender_address = (header1[0] >> 4) & 0x7
        self.receiver_address = header1[0] & 0x7
        while True:
            data = read_or_die(stream, 1)
            if data == b'\xff':
                break
            self.message_data = self.message_data + data
    
    def __repr__(self):
        return "VISCAMessage(sender_address=" + str(self.sender_address) + ", receiver_address=" + str(self.receiver_address) + ", data=" + self.message_data.hex() + ")"

BIND_INTERFACE = "0.0.0.0"
BIND_PORT = 5678

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((BIND_INTERFACE, BIND_PORT))
s.listen()
while True:
    conn, addr = s.accept()
    print("connection accepted from", addr)
    
    while True:
        message = VISCAMessage()
        message.read_from_stream(conn)
        
        print("received ", message)
        if message.message_data == b'\x09\x04\x47':
            print("zoom pos inq")
            conn.send(b'\x90\x50\x01\x02\x04\x07\xff')

    print("connection broken")
