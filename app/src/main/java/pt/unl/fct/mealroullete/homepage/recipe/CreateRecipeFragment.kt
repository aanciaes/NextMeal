package pt.unl.fct.mealroullete.homepage.recipe

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import kotlinx.android.synthetic.main.fragment_create_recipe.view.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.persistance.Ingredient
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.persistance.Poll
import pt.unl.fct.mealroullete.persistance.Recipe
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class CreateRecipeFragment : Fragment() {

    private val OPEN_GALLERY_REQ_CODE = 0
    private val PERMISSIONS_REQUEST_CODE = 1

    val items = mutableListOf<Ingredient>()
    var recipe = Recipe(2, R.drawable.empty_image_recipe, "", mutableListOf(), mutableListOf(), mutableListOf(0,0,0,0), mutableListOf(), 0, false)
    var firstInit = true

    init {
        items.addAll(MockDatabase.mainCourseItems)
        items.addAll(MockDatabase.sideItems)
        items.sortBy{it.name}
    }

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
                recipe.name = view.findViewById<EditText>(R.id.recipeName).text.toString()
                MockDatabase.addRecipe(recipe)
                text.text = text.text.toString()
                val intent = Intent(context, RecipeCard::class.java)
                val b = Bundle()
                b.putString("name", recipe.name) //Your id
                b.putBoolean("createRecipe", true)
                intent.putExtras(b)
                startActivity(intent)
            }

            builder.setNegativeButton("No") { dialog, which -> dialog.cancel() }
            builder.show()
        }


        //get the spinner from the xml.
        val dropdown = view.findViewById(R.id.spinner1) as Spinner
        val list = items.map{it.name} as MutableList
        list.sort()
        val adapter = ArrayAdapter<String>(this.context, R.layout.simple_spinner_dropdown_item, list)
        dropdown.adapter = adapter

        dropdown.onItemSelectedListener = SpinnerListener()

        val addInstruction = view.findViewById<ImageButton>(R.id.add_instruction)
        addInstruction.setOnClickListener {

            var instructionName = ""
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("INSTRUCTION")

            val viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_input, null, false);

            // Set up the input
            val input = viewInflated.findViewById(R.id.input) as EditText
            builder.setView(viewInflated)

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
                    recipe.instructions.add(instructionName)
                }

            }

            builder.setNegativeButton("Cancel", { dialog, which -> dialog.cancel() })

            builder.show()
        }

        val imageView = view.findViewById<ImageView>(R.id.recipe_image)
        imageView.setOnClickListener {
            uploadPhoto(0)
        }

        return view
    }


    private fun uploadPhoto(req_code: Int) {

        if ((ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {

            requestPermissions()
        } else{
            openGallery(req_code)
        }
    }

    private fun openGallery(req_code: Int) {

        if ((ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            startActivityForResult(Intent.createChooser(intent,
                    "Select file to upload "), req_code)
        } else{
            val alertDialogBuilder = AlertDialog.Builder(this.requireActivity())
            alertDialogBuilder.setTitle("Permission Request")
            alertDialogBuilder.setMessage("Meal roulette does not have permissions to read/write from/to your gallery\n" +
                    "Please set the appropriate permissions")

            alertDialogBuilder.setPositiveButton("Ok") { interfaceDialog, _ ->
                interfaceDialog.cancel()
            }

            alertDialogBuilder.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data!!.data

            if (requestCode == OPEN_GALLERY_REQ_CODE) {
                activity!!.contentResolver.takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val view = this.view?.findViewById<ImageView>(R.id.recipe_image)
                view!!.scaleType = ImageView.ScaleType.CENTER_CROP
                setImageFromUri(selectedImageUri.toString(), view)
                recipe.image = selectedImageUri.toString()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                openGallery(OPEN_GALLERY_REQ_CODE)
                return
            }
        }
    }

    private fun setImageFromUri(uri: String, imageView: ImageView) {
        imageView.setImageURI(Uri.parse(uri))
    }

    private fun requestPermissions () {
        if ((ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this.requireActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                explainPermission()
            } else {
                requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_CODE)
            }
        } else {
            openGallery(OPEN_GALLERY_REQ_CODE)
        }
    }

    private fun explainPermission () {
        val alertDialogBuilder = AlertDialog.Builder(this.requireActivity())
        alertDialogBuilder.setTitle("Permission Request")
        alertDialogBuilder.setMessage("Meal roulette needs permission to access your gallery")

        alertDialogBuilder.setPositiveButton("Ok") { interfaceDialog, _ ->
            interfaceDialog.cancel()
            requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST_CODE)
        }

        alertDialogBuilder.show()
    }

    inner class SpinnerListener : OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val ingredientSelected = items.find{it.name == items.get(position).name}
            if(!firstInit){
                val quantity = Random().nextInt((500 + 1) - 100) +  100
                val outerView = activity
                val container = outerView?.findViewById<LinearLayout>(R.id.ingredientContainer)
                val ingredient = TextView(context)
                val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(0, 25, 0, 0)
                ingredient.layoutParams = params
                val txt = quantity.toString() + " gr " + ingredientSelected?.name
                ingredient.text = txt
                val id = View.generateViewId()
                ingredient.id = id
                //val button =  view?.findViewById<ImageButton>(R.id.spinner1)
                //val index = container?.indexOfChild(button)
                container?.addView(ingredient)

                val calories = outerView?.findViewById<TextView>(R.id.caloriesValue)
                val caloriesOldValue = calories?.text.toString().split(" ")[0].toInt()
                val caloriesValue = caloriesOldValue + ingredientSelected!!.calories
                calories?.text = caloriesValue.toString() + " kcal"

                val proteins = outerView?.findViewById<TextView>(R.id.nutrientsProtein)
                val proteinOldValue = proteins?.text.toString().split(" ")[0].toInt()
                val proteinValue = proteinOldValue + ingredientSelected!!.protein
                proteins?.text = proteinValue.toString() + " gr Proteins"

                val fats = outerView?.findViewById<TextView>(R.id.nutrientFats)
                val fatsOldValue = fats?.text.toString().split(" ")[0].toInt()
                val fatsValue = fatsOldValue + ingredientSelected!!.fats
                fats?.text = fatsValue.toString() + " gr Fats"

                val carbs = outerView?.findViewById<TextView>(R.id.nutrientsCarbs)
                val carbsOldValue = carbs?.text.toString().split(" ")[0].toInt()
                val carbsValue = carbsOldValue + ingredientSelected!!.carbs
                carbs?.text = carbsValue.toString() + " gr Carbs"

                recipe.calories += caloriesValue
                recipe.nutrients[1] += proteinValue
                recipe.nutrients[2] += fatsValue
                recipe.nutrients[3] += carbsValue

                recipe.ingredients.add(ingredientSelected)
                recipe.quantities.add(quantity)
            }
            else{
                firstInit = false
            }
        }
    }
}
