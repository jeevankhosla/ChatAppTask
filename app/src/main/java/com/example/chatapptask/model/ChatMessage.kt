package com.example.chatapptask.model

class ChatMessage {
    var message: String? = null
    var senderId: String? = null

    constructor() {}

    constructor(message: String?, senderId: String?) {
        this.message = message
        this.senderId = senderId
    }
}
