package benznatthakul.au_restaurant_o

import android.app.Activity
import android.app.Instrumentation
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_adding_restaurant.*
import android.content.Intent
import android.net.Uri
import benznatthakul.au_restaurant_o.R.id.imageView
import android.provider.MediaStore
import android.graphics.Bitmap
import java.io.IOException
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.OnProgressListener
import android.widget.Toast
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.util.UUID.randomUUID
import com.google.firebase.storage.StorageReference
import android.app.ProgressDialog
import com.google.firebase.storage.FirebaseStorage


class AddingRestaurant : AppCompatActivity() {

    var currentFirebaseUser: FirebaseUser? = null
    var storage: FirebaseStorage? = null
    var storageRef: StorageReference? = null

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_restaurant)

        //get user info
        currentFirebaseUser = FirebaseAuth.getInstance().currentUser
        //init firebase storage
        storage = FirebaseStorage.getInstance()
        storageRef = storage!!.reference

        saveBtn.setOnClickListener {
            uploadImage()
        }

        selectBtn.setOnClickListener {
            selectImage()
        }
    }

    /*
        var image: String,
        var owner: String,
        var rating: Int,
        var shop_address: String,
        var shop_description: String,
        var shop_name: String,
        var shop_type: String,
        var tel_number: String
     */
    fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                showImg.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun uploadImage() {

        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val tsLong = System.currentTimeMillis() / 1000
            val ts = tsLong.toString()

            val ref = storageRef!!.child("shop/" + ts)
            ref.putFile(filePath!!)
                    .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Uploaded", Toast.LENGTH_SHORT).show()
                        //add shop
                        addShop(taskSnapshot.downloadUrl.toString())
                    })
                    .addOnFailureListener(OnFailureListener { e ->
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                    })
                    .addOnProgressListener(OnProgressListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                                .totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                    })
        }
    }

    fun addShop(imgURL: String){

        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()

        var shop = shop(
                ts,
                imgURL,
                currentFirebaseUser!!.uid,
                0,
                rest_addr.text.toString(),
                rest_description.text.toString(),
                rest_name.text.toString(),
                "restaurant",
                rest_tel.text.toString()
                )

        var dbRef = FirebaseDatabase.getInstance().getReference()

        //find shop own by user only
        var query = dbRef.child("shop").child(ts).setValue(shop)
        Log.d("WVN","Added")

        finish()
    }

}
