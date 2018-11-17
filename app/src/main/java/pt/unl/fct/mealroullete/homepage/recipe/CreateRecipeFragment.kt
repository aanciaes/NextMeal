package pt.unl.fct.mealroullete.homepage.recipe

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import pt.unl.fct.mealroullete.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class CreateRecipeFragment : Fragment() {

    private val IMAGES_DIRECTORY = "/profile_pictures"

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_recipe, container, false)

        val addRecipe = view.findViewById<Button>(R.id.addRecipe)
        addRecipe.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)

            val text = TextView(this.context)
            text.text = "ARE YOU SURE YOU WANT TO ADD THIS RECIPE?"
            text.setTextColor(R.color.colorAccent)
            text.setPadding(40, 60, 40, 60)
            builder.setView(text)
            builder.setPositiveButton("Yes") { dialog, which ->
                text.text = text.text.toString()
                val refresh = Intent(this.context, RecipeActivity::class.java)
                startActivity(refresh)
            }

            builder.setNegativeButton("No") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        val addIngredient = view.findViewById<ImageButton>(R.id.add_ingredient)
        addIngredient.setOnClickListener {
            var ingredientName = ""
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("INGREDIENT")

            val input = EditText(this.context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("Add") { dialog, which ->
                ingredientName = input.text.toString()
                if (ingredientName != "") {
                    val container = view.findViewById<LinearLayout>(R.id.ingredientContainer)
                    val ingredient = TextView(this.context)
                    val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(0, 25, 0, 0)
                    ingredient.layoutParams = params
                    ingredient.text = ingredientName
                    val id = View.generateViewId()
                    ingredient.id = id
                    val index = container.indexOfChild(addIngredient)
                    container.addView(ingredient, index)
                }

            }
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }

        val addInstruction = view.findViewById<ImageButton>(R.id.add_instruction)
        addInstruction.setOnClickListener {

            var instructionName = ""
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("INSTRUCTION")

            val input = EditText(this.context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("Add") { dialog, which ->
                instructionName = input.text.toString()
                if (instructionName != "") {
                    val container = view.findViewById<LinearLayout>(R.id.instructionContainer)
                    val instruction = TextView(this.context)
                    val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(0, 25, 0, 0)
                    instruction.layoutParams = params
                    instruction.text = instructionName
                    val id = View.generateViewId()
                    instruction.id = id
                    val index = container.indexOfChild(addInstruction)
                    container.addView(instruction, index)
                }

            }

            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }
        val addNutrient = view.findViewById<ImageButton>(R.id.add_nutrient)
        addNutrient.setOnClickListener {

            var nutrientName = ""
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("NUTRIENT")

            val input = EditText(this.context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("Add") { dialog, which ->
                nutrientName = input.text.toString()
                if (nutrientName != "") {
                    val container = view.findViewById<LinearLayout>(R.id.nutrientContainer)
                    val nutrient = TextView(this.context)
                    val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(0, 25, 0, 0)
                    nutrient.layoutParams = params
                    nutrient.text = nutrientName
                    val id = View.generateViewId()
                    nutrient.id = id
                    val index = container.indexOfChild(addNutrient)
                    container.addView(nutrient, index)
                }

            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

            builder.show()
        }

        val imageView = view.findViewById<ImageView>(R.id.recipe_image)
        imageView.setOnClickListener {
            uploadPhoto(0)
        }

        return view
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data!!.data

            if (requestCode == 0) {
                val imagePath = getPath(selectedImageUri)
                val view = this.view?.findViewById<ImageView>(R.id.recipe_image)
                view!!.scaleType = ImageView.ScaleType.CENTER_CROP
                setImageFromUrl(imagePath, view)
            }
        }
    }

    private fun getPath(uri: Uri?): String {
        val bitmap = MediaStore.Images.Media.getBitmap(this.context?.contentResolver, uri)
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

            MediaScannerConnection.scanFile(this.context, arrayOf(f.path), arrayOf("image/jpeg"), null)
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
}
