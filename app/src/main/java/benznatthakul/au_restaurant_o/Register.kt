package benznatthakul.au_restaurant_o

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {


    val mAuth = FirebaseAuth.getInstance()
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val regBtn = findViewById<View>(R.id.regBtn) as Button

        mDatabase = FirebaseDatabase.getInstance().getReference("Names")

        regBtn.setOnClickListener(View.OnClickListener {
            view -> register ()
        })
    }

    private fun register() {
        val emailTxt = findViewById<View>(R.id.emailTxt) as EditText
        val passwordTxt = findViewById<View>(R.id.passwordTxt) as EditText
        val nameTxt = findViewById<View>(R.id.nameTxt) as EditText

        var email = emailTxt.text.toString()
        var password = passwordTxt.text.toString()
        var name = nameTxt.text.toString()

        if (!name.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val uid = user!!.uid
                    mDatabase.child(uid).child("Name").setValue(name)
                    Toast.makeText(this, "Successfully signed in: ", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error:(", Toast.LENGTH_LONG).show()
                }
            })

        } else {
            Toast.makeText(this, "Please enter the credentials :(", Toast.LENGTH_LONG).show()
        }
    }
}
