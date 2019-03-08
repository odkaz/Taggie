package jp.creation.destiny.one.taggie

import android.content.ClipDescription
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast

class MyOnDragListener(private val context: Context): View.OnDragListener {

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
                Toast.makeText(context, "Dragged data is" + dragData, Toast.LENGTH_LONG).show()
                v.background.clearColorFilter()
                v.invalidate()

                val view = event.localState as View
                val owner = view.parent as ViewGroup
                owner.removeView(view)
                if (v.id == R.id.relative_layout) {
                    val container = v as RelativeLayout
                    val params = RelativeLayout.LayoutParams(view.width, view.height)
                    params.leftMargin = event.x.toInt() - view.width / 2
                    params.topMargin = event.y.toInt() - view.height / 2

                    container.addView(view, params)
                    view.visibility = View.VISIBLE

                    //inflate new balloon if it was dragged out from item_layout
                    if (owner.id == R.id.item_layout) {
                        Log.d("kotlintest", "inflate new balloon")
                        val balloon = BalloonView("", "", 0, 100, context)
                        balloon.inflate(owner)
                    }


                    //add code lines to sync with firebase



                } else if (v.id == R.id.item_layout){

                    val container = v as RelativeLayout
                    val params = RelativeLayout.LayoutParams(view.width, view.height)
                    Log.d("kotlintest", "v.tag is " + view.tag.toString())

                    /*
                    when(view.tag) {
                        AnswerCreaterActivity.TEXT_VIEW_TAG -> {
                            params.addRule(RelativeLayout.ALIGN_PARENT_START)
                        }
                        AnswerCreaterActivity.IMAGE_VIEW_TAG -> {
                            params.addRule(RelativeLayout.CENTER_HORIZONTAL)
                        }
                        AnswerCreaterActivity.BUTTON_VIEW_TAG -> {
                            params.addRule(RelativeLayout.ALIGN_PARENT_END)
                        }
                    }
                    */


                    container.addView(view, params)
                    view.visibility = View.VISIBLE
                }
                return true
            }

            DragEvent.ACTION_DRAG_ENDED-> {
                v.background.clearColorFilter()
                v.invalidate()
                if (event.result) {
                    Toast.makeText(context, "The drop was handled", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(context, "The drop didn't work", Toast.LENGTH_LONG).show()

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