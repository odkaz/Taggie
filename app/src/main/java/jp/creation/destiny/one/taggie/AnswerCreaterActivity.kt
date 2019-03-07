package jp.creation.destiny.one.taggie

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import kotlinx.android.synthetic.main.activity_answer_creater.*
import kotlinx.android.synthetic.main.content_answer_creater.*

class AnswerCreaterActivity : AppCompatActivity(), View.OnDragListener, View.OnLongClickListener {
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView
    private lateinit var button: Button

    companion object {
        //val TAG = MainActivity.class.getSimpleName()
        val IMAGE_VIEW_TAG = "LAUNCHER LOGO"
        val TEXT_VIEW_TAG = "DRAG TEXT"
        val BUTTON_VIEW_TAG = "DRAG BUTTON"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_creater)
        setSupportActionBar(toolbar)

        setTag()
        implementEvents()


    }

    private fun setTag() {
        textView = findViewById(R.id.textView)
        imageView = findViewById(R.id.imageView)
        button = findViewById(R.id.button)

        textView.tag = TEXT_VIEW_TAG
        imageView.tag = IMAGE_VIEW_TAG
        button.tag = BUTTON_VIEW_TAG

    }

    private fun implementEvents() {
        textView.setOnLongClickListener(this)
        imageView.setOnLongClickListener(this)
        button.setOnLongClickListener(this)

        val relativeLayout = findViewById<RelativeLayout>(R.id.relative_layout)
        relativeLayout.setOnDragListener(this)
        val itemLayout = findViewById<RelativeLayout>(R.id.item_layout)
        itemLayout.setOnDragListener(this)

    }

    override fun onLongClick(v: View?): Boolean {
        val item = ClipData.Item(v!!.tag as CharSequence)
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val data = ClipData(v.tag.toString(), mimeTypes, item)

        val myShadow = MyDragShadowBuilder(v)
        v.startDrag(
            data,
            myShadow,
            v,
            0

        )

        v.visibility = View.INVISIBLE
        return true

    }

    override fun onDrag(v: View, event: DragEvent): Boolean {
        val action = event.action
        when(action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                return event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                v.background.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN)
                v.invalidate()
                return true
            }


            DragEvent.ACTION_DRAG_LOCATION -> {
                return true
            }

            DragEvent.ACTION_DRAG_EXITED -> {
                v.background.clearColorFilter()
                v.invalidate()

                return true
            }

            DragEvent.ACTION_DROP -> {
                val item = event.clipData.getItemAt(0)
                val dragData= item.text.toString()
                Toast.makeText(this, "Dragged data is" + dragData, Toast.LENGTH_LONG).show()
                v.background.clearColorFilter()
                v.invalidate()

                val view = event.localState as View
                val owner = view.parent as ViewGroup
                owner.removeView(view)


                //Log.d("kotlintest", "event y is" + event.y.toInt().toString())
                //Log.d("kotlintest", "owner height is" + owner.height.toString())
                Log.d("kotlintest", "owner id is"+ owner.id.toString())
                Log.d("kotlintest", "relativeLayout id is " + R.id.relative_layout.toString())
                Log.d("kotlintest", "v.id is" + v.id.toString())

                if (v.id == R.id.relative_layout) {
                    val container = v as RelativeLayout
                    val params = RelativeLayout.LayoutParams(view.width, view.height)
                    params.leftMargin = event.x.toInt() - view.width / 2
                    params.topMargin = event.y.toInt() - view.height / 2
                    Log.d("kotlintest", "x =" + params.leftMargin.toString() + ",y=" + params.topMargin.toString())

                    container.addView(view, params)
                    view.visibility = View.VISIBLE

                    when(view.tag) {
                        TEXT_VIEW_TAG -> {
                            val copyTextView = LayoutInflater.from(this).inflate(R.layout.test_text_view, null) as TextView
                            owner.addView(copyTextView)
                            copyTextView.visibility = View.VISIBLE
                        }
                        IMAGE_VIEW_TAG -> {
                            val copyImageView = LayoutInflater.from(this).inflate(R.layout.copy_image_view, null)
                            owner.addView(copyImageView)
                            copyImageView.visibility = View.VISIBLE
                        }
                        BUTTON_VIEW_TAG -> {
                            val copyButton = LayoutInflater.from(this).inflate(R.layout.copy_button, null)
                            owner.addView(copyButton)
                            copyButton.visibility = View.VISIBLE
                        }
                    }




                } else if (v.id == R.id.item_layout){

                    val container = v as RelativeLayout
                    val params = RelativeLayout.LayoutParams(view.width, view.height)
                    Log.d("kotlintest", "v.tag is " + view.tag.toString())
                    when(view.tag) {
                        TEXT_VIEW_TAG -> {
                            params.addRule(RelativeLayout.ALIGN_PARENT_START)
                        }
                        IMAGE_VIEW_TAG -> {
                            params.addRule(RelativeLayout.CENTER_HORIZONTAL)
                        }
                        BUTTON_VIEW_TAG -> {
                            params.addRule(RelativeLayout.ALIGN_PARENT_END)
                        }
                    }


                    container.addView(view, params)
                    view.visibility = View.VISIBLE
                }
                return true
            }

            DragEvent.ACTION_DRAG_ENDED-> {
                v.background.clearColorFilter()
                v.invalidate()
                if (event.result) {
                    Toast.makeText(this, "The drop was handled", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this, "The drop didn't work", Toast.LENGTH_LONG).show()

                }
                return true
            }
            else -> {
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener")
                return false
            }
        }
    }
}