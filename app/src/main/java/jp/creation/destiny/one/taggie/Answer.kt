package jp.creation.destiny.one.taggie

import android.view.View
import java.io.Serializable

class Answer(val name: String, val uid: String, val answerUid: String, val childViewList: ArrayList<View>): Serializable{
    //val childViewList = arrayListOf<View>()


    fun addChildView(child: View) {
        childViewList.add(child)

    }
}