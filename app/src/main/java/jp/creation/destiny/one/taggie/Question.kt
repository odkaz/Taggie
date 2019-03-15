package jp.creation.destiny.one.taggie

import java.io.Serializable

class Question(val title: String, val content: String, val name: String, val uid: String, bytes: ByteArray, val answers: ArrayList<Answer>): Serializable {
    val imageBytes: ByteArray

    init {
        imageBytes = bytes.clone()
    }


}