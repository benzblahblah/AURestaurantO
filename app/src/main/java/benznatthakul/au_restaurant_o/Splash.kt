package benznatthakul.au_restaurant_o

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var textV = findViewById<View>(R.id.tv) as TextView
        var imageV = findViewById<View>(R.id.iv) as ImageView
        val myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition)
        textV.startAnimation(myanim)
        imageV.startAnimation(myanim)
        val i = Intent(this,MainActivity::class.java)
        val timer = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(3000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(i)
                    finish()
                }

            }
        }
        timer.start()
    }
}
