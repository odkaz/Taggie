package jp.creation.destiny.one.taggie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
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
        val frameLayout = view.getChildAt(1) as ViewGroup
        val nestedScrollView = frameLayout.getChildAt(0) as ViewGroup
        val cardView = nestedScrollView.getChildAt(0) as ViewGroup
        val mDatabaseReference = FirebaseDatabase.getInstance().reference
        val qUid = arguments!!.getString("qUid", "")
        val questionRef = mDatabaseReference.child(ContentsPATH).child(qUid)
        var flag = true


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
                            //view.background = BitmapDrawable(activity!!.resources, image)
                            imageView.setImageBitmap(image)
                            Log.d("kotlintest", "image view height is = " + imageView.height.toString())
                            flag = false

                        }

                        "title" -> {
                            val titleText = cardView.getChildAt(0) as TextView
                            titleText.text = it.value as String
                        }

                        "content" -> {
                            val contentText = cardView.getChildAt(1) as TextView
                            contentText.text = it.value as String
                        }
                    }
                }

                if (flag) {
                    cardView.y = 30f
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
        return view
    }
}