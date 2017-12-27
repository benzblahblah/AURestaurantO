package benznatthakul.au_restaurant_o

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.View
import android.util.Log
import android.content.Intent
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_timeline.*
import java.util.*


class MainActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLoginEmail = findViewById<Button>(R.id.btnLoginEmail)
        val regTxt = findViewById<View>(R.id.regTxt) as TextView


        btnLoginEmail.setOnClickListener(View.OnClickListener {
            view -> login()
        })

        regTxt.setOnClickListener(View.OnClickListener {
            view -> register()
        })

        var btnLoginFacebook = findViewById<Button>(R.id.btnLoginFacebook)

        btnLoginFacebook.setOnClickListener(View.OnClickListener {
            // Login
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(loginResult: LoginResult) {
                            Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                            startActivity(Intent(applicationContext, AuthenticatedActivity::class.java))
                        }

                        override fun onCancel() {
                            Log.d("MainActivity", "Facebook onCancel.")

                        }

                        override fun onError(error: FacebookException) {
                            Log.d("MainActivity", "Facebook onError.")

                        }
                    })
        })
    }

    private fun register() {
        startActivity(Intent(this, Register :: class.java))
    }

    private fun login() {
        val emailTxt = findViewById<View>(R.id.emailTxt) as EditText
        val passwordTxt = findViewById<View>(R.id.passwordTxt) as EditText

        var email = emailTxt.text.toString()
        var password = passwordTxt.text.toString()

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, AuthenticatedActivity :: class.java))
                    Toast.makeText(this, "Successfully logged in:)", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            Toast.makeText(this,"Please fill up the credentials:(",Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}
