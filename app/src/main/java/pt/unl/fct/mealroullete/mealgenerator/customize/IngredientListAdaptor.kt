package pt.unl.fct.mealroullete.mealgenerator.customize

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.TextView
import pt.unl.fct.mealroullete.R

import java.util.ArrayList

class IngredientListAdaptor
/**
 * Default constructor for the PersonListAdapter
 * @param context
 * @param resource
 * @param objects
 */
(private val mContext: Context, private val mResource: Int, objects: ArrayList<IngredientRow>) : ArrayAdapter<IngredientRow>(mContext, mResource, objects) {
    private var lastPosition = -1

    /**
     * Holds variables in a View
     */
    private class ViewHolder {
        internal var name: TextView? = null
        internal var name1: TextView? = null
        internal var name2: TextView? = null
        internal var name3: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
/*        //get the persons information
        val id = getItem(position)!!.id
        val name = getItem(position)!!.name
        val url = getItem(position)!!.image

        val name2 = getItem(position)!!.name2
        val name3 = getItem(position)!!.name3
        val name4 = getItem(position)!!.name4

        //Create the person object with the information
        val ingredient = IngredientRow(id, url, name, name2, name3, name4)

        //create the view result for showing the animation
        val result: View

        //ViewHolder object
        val holder: ViewHolder


        if (convertView == null) {
            val inflater = LayoutInflater.from(mContext)
            convertView = inflater.inflate(mResource, parent, false)
            holder = ViewHolder()
            holder.name = convertView!!.findViewById<View>(R.id.textView1) as TextView
            holder.name1 = convertView!!.findViewById<View>(R.id.textView2) as TextView
            holder.name2 = convertView!!.findViewById<View>(R.id.textView3) as TextView
            holder.name3 = convertView!!.findViewById<View>(R.id.textView4) as TextView


            result = convertView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            result = convertView
        }

        val animation = AnimationUtils.loadAnimation(mContext,
                if (position > lastPosition) R.anim.load_down_anim else R.anim.load_up_anim)
        result.startAnimation(animation)
        lastPosition = position

        holder.name!!.text = ingredient.name
        holder.name1!!.text = ingredient.name2
        holder.name2!!.text = ingredient.name3
        holder.name3!!.text = ingredient.name4*/


        return convertView!!
    }

    companion object {

        private val TAG = "IngredientsListAdapter"
    }
}
