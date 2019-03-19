package jp.creation.destiny.one.taggie

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import drawable.ArrowView

class AnswerListFragment(): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_answer, container, false) as ViewGroup
        val mDatabaseReference = FirebaseDatabase.getInstance().reference
        val qUid = arguments!!.getString("qUid", "")
        val ansUid = arguments!!.getString("ansUid", "")
        val answerRef = mDatabaseReference.child(ContentsPATH).child(qUid).child("answers").child(ansUid)

        answerRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                children.forEach {
                    val data = it.value as HashMap<String, String>
                    when (data["type"]) {
                        "balloon" -> {
                            val balloonView = BalloonView(data["title"]!!, data["content"]!!, data["posX"]!!.toInt(), data["posY"]!!.toInt(), false,  view.context)
                            balloonView.inflate(view)
                        }
                        "arrow" -> {
                            val arrowView = ArrowView(data["posX"]!!.toInt(), data["posY"]!!.toInt(), data["degree"]!!.toFloat(), false,  view.context)
                            arrowView.inflate(view)
                        }
                        "giraffe" -> {
                            val giraffeView = GiraffeOneView(data["posX"]!!.toInt(), data["posY"]!!.toInt(), data["size"]!!.toInt(), false, view.context)
                            giraffeView.inflate(view)
                        }
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
        return view
    }
}