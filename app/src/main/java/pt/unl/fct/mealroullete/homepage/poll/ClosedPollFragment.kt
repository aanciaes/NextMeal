package pt.unl.fct.mealroullete.homepage.poll

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.recipe.RecipeCard
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.persistance.Poll

class ClosedPollFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_closed_poll, container, false)
        val ingredientTableLayout = view.findViewById<TableLayout>(R.id.ingredient_table)

        val polls = MockDatabase.polls

        for (p in polls) {
            if (p.owner == MockDatabase.loggedInUser!!.username || p.users.containsKey(MockDatabase.loggedInUser!!.email)) {
                if (!p.active) {
                    val childParent = inflater.inflate(R.layout.table_item_pollclosed, container, false) as LinearLayout
                    val child = childParent.findViewById<LinearLayout>(R.id.table_item_pollclosed_child)
                    child.findViewById<TextView>(R.id.pollAuthor).text = p.owner


                    if (p.winner == null){
                        chooseWinner (p)
                    }

                    child.findViewById<TextView>(R.id.winnerName).text = p.winner!!.name
                    child.findViewById<ImageView>(R.id.pollImage).setImageResource((p.winner!!.image as Int))

                    child.setOnClickListener {
                        val intent = Intent(this.context, RecipeCard::class.java)
                        val b = Bundle()
                        b.putString("name", p.winner!!.name) //Your id
                        intent.putExtras(b)
                        startActivity(intent)
                    }
                    ingredientTableLayout.addView(childParent)
                }
            }
        }
        return view
    }

    private fun chooseWinner (p: Poll) {
        val map = mutableMapOf<String, Int>()

        map[p.recipes[0]!!.name] = 0
        map[p.recipes[1]!!.name] = 0
        map[p.recipes[2]!!.name] = 0
        for (u in p.users){
            map[u.key] = map[u.key]!! + 1
        }
        val winnerName = map.maxBy { it.value }!!.key
        p.winner = MockDatabase.findRecipe(winnerName)
    }
}
