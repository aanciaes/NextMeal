package pt.unl.fct.mealroullete.homepage.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_profile.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.calculator.CalculatorActivity
import pt.unl.fct.mealroullete.homepage.history.HistoryActivity
import pt.unl.fct.mealroullete.homepage.poll.PollActivity
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity
import pt.unl.fct.mealroullete.logout.LogoutActivity
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.persistance.User
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class ProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val IMAGES_DIRECTORY = "/profile_pictures"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(profile_toolbar)

        val toggle = ActionBarDrawerToggle(this, profile_drawer, profile_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        profile_drawer.addDrawerListener(toggle)
        toggle.syncState()

        profile_navbar.setCheckedItem(R.id.common_drawer_item_profile)
        profile_navbar.setNavigationItemSelectedListener(this)

        setListeners()

        setCommonHeaderInformationForLoggedInUser(profile_navbar, MockDatabase.loggedInUser)
        setInfo()
    }

    private fun setInfo() {
        findViewById<TextView>(R.id.user_name).text = MockDatabase.loggedInUser?.username
        findViewById<TextView>(R.id.user_email).text = MockDatabase.loggedInUser?.email
        setImageFromUrl(MockDatabase.loggedInUser?.picture.toString(), findViewById(R.id.profile_image))

        val nameDateOfBirth = MockDatabase.loggedInUser?.fullName
        if (nameDateOfBirth != null) {
            findViewById<TextView>(R.id.user_name_date_birth).text = "${MockDatabase.loggedInUser?.fullName} ${MockDatabase.loggedInUser?.dateOfBirth}"
        }

        addInitAllergies(MockDatabase.loggedInUser?.allergies)
    }

    override fun onBackPressed() {
        if (profile_drawer.isDrawerOpen(GravityCompat.START)) {
            profile_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var intent: Intent? = null

        when (item.itemId) {
            R.id.common_drawer_item_calculator -> {
                intent = Intent(this, CalculatorActivity::class.java)
            }
            R.id.common_drawer_item_history -> {
                intent = Intent(this, HistoryActivity::class.java)
            }
            R.id.common_drawer_item_poll -> {
                intent = Intent(this, PollActivity::class.java)
            }
            R.id.common_drawer_item_profile -> {
                profile_drawer.closeDrawer(GravityCompat.START)
                return false
            }
            R.id.common_drawer_item_recipe -> {
                intent = Intent(this, RecipeActivity::class.java)
            }
            R.id.common_drawer_item_logout -> {
                intent = Intent(this, LogoutActivity::class.java)
            }
        }

        profile_drawer.closeDrawer(GravityCompat.START)

        startActivity(intent)
        this.finish()

        return true
    }

    private fun setCommonHeaderInformationForLoggedInUser(navigationView: NavigationView, user: User?) {
        val header = navigationView.getHeaderView(0)

        header.findViewById<TextView>(R.id.common_header_user_full_name).text = user?.username
        header.findViewById<TextView>(R.id.common_header_user_email_address).text = user?.email

        val imageView = header.findViewById<ImageView>(R.id.common_header_user_profile_photo)

        if (MockDatabase.loggedInUser?.picture != null) {
            setImageFromUrl(MockDatabase.loggedInUser?.picture.toString(), imageView)
        }
    }

    private fun setListeners() {
        val editPicture = findViewById<ImageButton>(R.id.profile_edit_picture)
        editPicture.setOnClickListener {
            uploadPhoto(0)
        }

        val editEmail = findViewById<ImageButton>(R.id.profile_edit_email)
        editEmail.setOnClickListener { editEmail() }

        val editFullNameDateOfBirth = findViewById<ImageButton>(R.id.profile_edit_full_name)
        editFullNameDateOfBirth.setOnClickListener { editFullNameAndDateOfBirth() }

        addAllergies()
    }

    private fun editEmail() {
        var newEmail = ""
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Email")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(findViewById<TextView>(R.id.user_email).text.toString())
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            newEmail = input.text.toString()
            MockDatabase.loggedInUser?.email = newEmail
            findViewById<TextView>(R.id.user_email).text = MockDatabase.loggedInUser?.email
            profile_navbar.getHeaderView(0)
                    .findViewById<TextView>(R.id.common_header_user_email_address).text = MockDatabase.loggedInUser?.email
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    @SuppressLint("NewApi")
    private fun editFullNameAndDateOfBirth() {
        var newFullName = ""
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Full Name")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("NEXT") { _, _ ->
            newFullName = input.text.toString()
            MockDatabase.loggedInUser?.fullName = newFullName

            val datePickerDialog = DatePickerDialog(this)
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                MockDatabase.loggedInUser?.dateOfBirth = year.toString() + "/" + month.toString() + "/" + dayOfMonth.toString()
                findViewById<TextView>(R.id.user_name_date_birth).text = "${MockDatabase.loggedInUser?.fullName} ${MockDatabase.loggedInUser?.dateOfBirth}"
            }
            datePickerDialog.show()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun uploadPhoto(req_code: Int) {
        openGallery(req_code)
    }

    private fun openGallery(req_code: Int) {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,
                "Select file to upload "), req_code)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data!!.data

            if (requestCode == 0) {
                val imagePath = getPath(selectedImageUri)
                MockDatabase.loggedInUser?.picture = imagePath

                val imageView = findViewById<ImageView>(R.id.profile_image)
                setImageFromUrl(imagePath, imageView)
                setCommonHeaderInformationForLoggedInUser(profile_navbar, MockDatabase.loggedInUser)
            }
        }
    }

    private fun getPath(uri: Uri?): String {
        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        return saveImage(bitmap)
    }

    private fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
                (Environment.getExternalStorageDirectory()).toString() + IMAGES_DIRECTORY)

        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        try {
            val f = File(wallpaperDirectory.absolutePath + "/" + Calendar.getInstance().timeInMillis.toString() + ".jpg")
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())

            MediaScannerConnection.scanFile(this, arrayOf(f.path), arrayOf("image/jpeg"), null)
            fo.close()

            return f.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    private fun setImageFromUrl(path: String, imageView: ImageView) {
        val imgFile = File(path);
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath);

            imageView.setImageBitmap(myBitmap);
        }
    }

    private fun addAllergies() {
        val addAllergies = findViewById<ImageButton>(R.id.add_allergies)
        addAllergies.setOnClickListener {

            var allergyName = ""
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Allergy")

            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("Add") { _, _ ->
                allergyName = input.text.toString()
                if (allergyName != "") {
                    val container = findViewById<LinearLayout>(R.id.allergiesContainer)
                    val allergy = TextView(this)

                    val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(35, 25, 0, 0)

                    allergy.setTextColor(resources.getColor(R.color.colorAccent, null))
                    allergy.layoutParams = params
                    allergy.text = allergyName

                    val id = View.generateViewId()
                    allergy.id = id
                    val index = container.indexOfChild(addAllergies)

                    MockDatabase.loggedInUser?.allergies?.add(allergyName)

                    container.addView(allergy, index)
                }
            }

            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }
    }

    private fun addInitAllergies(allergies: List<String>?) {
        val addAllergies = findViewById<ImageButton>(R.id.add_allergies)
        if (allergies != null) {
            for (allergyName in allergies) {

                val container = findViewById<LinearLayout>(R.id.allergiesContainer)
                val allergy = TextView(this)

                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(35, 25, 0, 0)

                allergy.setTextColor(resources.getColor(R.color.colorAccent, null))
                allergy.layoutParams = params
                allergy.text = allergyName

                val id = View.generateViewId()
                allergy.id = id
                val index = container.indexOfChild(addAllergies)

                container.addView(allergy, index)
            }
        }
    }
}
