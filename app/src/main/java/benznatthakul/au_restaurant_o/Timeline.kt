package benznatthakul.au_restaurant_o

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register2.*
import kotlinx.android.synthetic.main.activity_timeline.*

class Timeline : AppCompatActivity() {
    lateinit var mDatabase : DatabaseReference
    var mAuth = FirebaseAuth.getInstance()
    var user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        var btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener(View.OnClickListener {
            // Logout from Facebook
            if (AccessToken.getCurrentAccessToken() != null) {
                GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, GraphRequest.Callback {
                    AccessToken.setCurrentAccessToken(null)
                    LoginManager.getInstance().logOut()

                    finish()
                }).executeAsync()
            }
        })

        BtnHomeP.setOnClickListener(View.OnClickListener {
            restaurantList()
        })

        MenuBtn.setOnClickListener(View.OnClickListener {
            menupage()
        })


        btnLogout.setOnClickListener(View.OnClickListener {
            logout()
        })

        val nameTxt = findViewById<View>(R.id.dispTxt) as TextView

        var uid = user!!.uid

        mDatabase = FirebaseDatabase.getInstance().getReference("Names")

        mDatabase.child(uid).child("Name").addValueEventListener( object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                nameTxt.text =  "Welcome " + snapshot.value.toString()
            }
        })

    }

    private fun menupage() {
        startActivity(Intent(this, FoodMenuList::class.java))
    }

    private fun logout() {
//        mAuth.signOut()
        Toast.makeText(this, "Signed Out", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MainActivity::class.java))
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    private fun restaurantList() {
        startActivity(Intent(this, RestaurantList :: class.java))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.btnLogout) {
            mAuth.signOut()
            Toast.makeText(this, "Signed Out", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }


}