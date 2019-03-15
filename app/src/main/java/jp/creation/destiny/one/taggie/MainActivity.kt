package jp.creation.destiny.one.taggie

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val intent = Intent(applicationContext, QuestionAnswerTabActivity::class.java)
        intent.putExtra("qUid", "-L_jwQTkGuKbJROIhpOs")
        startActivity(intent)

        fab.setOnClickListener { view ->
            val firebaseAuth = FirebaseAuth.getInstance()
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                val intent = Intent(applicationContext, QuestionCreateActivity::class.java)
                startActivity(intent)

            } else {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)

            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
