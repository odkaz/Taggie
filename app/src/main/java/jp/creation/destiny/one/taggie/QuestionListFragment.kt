package jp.creation.destiny.one.taggie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import drawable.ArrowView

class QuestionListFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_question_detail, container, false) as ViewGroup
        val mDatabaseReference = FirebaseDatabase.getInstance().reference
        val qUid = arguments!!.getString("qUid", "")
        val questionRef = mDatabaseReference.child(ContentsPATH).child(qUid)


        questionRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                children.forEach {
                    when (it.key) {
                        "image" -> {
                            val imageString = it.value as String
                            val bytes = if (imageString.isNotEmpty()) {
                                Base64.decode(imageString, Base64.DEFAULT)
                            } else {
                                byteArrayOf()
                            }

                            val image = BitmapFactory.decodeByteArray(bytes, 0, bytes.size).copy(Bitmap.Config.ARGB_8888, true)
                            val imageView = view.getChildAt(0) as ImageView
                            imageView.setImageBitmap(image)
                        }

                        "title" -> {
                            val contentText = view.getChildAt(1) as TextView
                            contentText.text = it.value as String
                        }

                        "name" -> {
                            val nameText = view.getChildAt(2) as TextView
                            nameText.text = it.value as String
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