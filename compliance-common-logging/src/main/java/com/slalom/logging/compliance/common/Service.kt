package com.slalom.logging.compliance.common

interface MaskService {

    fun maskMessage(message: String): String
}

class JsonMaskService : MaskService {
    override fun maskMessage(message: String): String { // TODO to implement
        return ""
    }
}


class LombokMaskService : MaskService {
    override fun maskMessage(message: String): String { // TODO to implement
        return ""
    }
}
